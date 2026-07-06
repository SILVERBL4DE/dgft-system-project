package com.htc.dgft.scheduler;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.entity.DgftOrmMessageDetail;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import com.htc.dgft.repository.DgftOrmMessageDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DgftEnquiryScheduler {

    private final DgftOrmMessageDetailRepository messageDetailRepository;
    private final DgftOrmMasterRepository masterRepository;

    @Scheduled(cron = "0 30 10 * * ?")
    @Transactional
    public void executeEnquiry() {
        // Phase 1: Syncing PENDING Details (Steps 4 & 10)
        List<DgftOrmMessageDetail> pendingDetails = messageDetailRepository.findByStatus("PENDING");
        
        for (DgftOrmMessageDetail detail : pendingDetails) {
            masterRepository.findAll().stream()
                    .filter(orm -> orm.getOrmNumber().equals(detail.getOrmNumber()))
                    .findFirst()
                    .ifPresent(master -> {
                        master.setFlag("P");
                        // Preserving existing STATUS (ACTIVE/CANCELLED) and DGFT_FLAG (F/C)
                        master.setDgftStatus("VALIDATED");
                        master.setMasterDetailStatus("MSG_PUSH_SUCCESS");
                        masterRepository.save(master);
                    });
        }

        // Phase 2: Syncing Final Outcomes - PROCESSED (Steps 6 & 12)
        List<DgftOrmMessageDetail> processedDetails = messageDetailRepository.findByStatus("PROCESSED");
        for (DgftOrmMessageDetail detail : processedDetails) {
            masterRepository.findAll().stream()
                    .filter(orm -> orm.getOrmNumber().equals(detail.getOrmNumber()))
                    .findFirst()
                    .ifPresent(master -> {
                        master.setFlag("A");
                        master.setDgftStatus("PROCESSED");
                        master.setMasterDetailStatus("MSG_PUSH_FULLY_PROCESSED");
                        masterRepository.save(master);
                    });
        }

        // Phase 2: Syncing Final Outcomes - FAILED (Steps 6 & 12)
        List<DgftOrmMessageDetail> failedDetails = messageDetailRepository.findByStatus("FAILED");
        for (DgftOrmMessageDetail detail : failedDetails) {
            masterRepository.findAll().stream()
                    .filter(orm -> orm.getOrmNumber().equals(detail.getOrmNumber()))
                    .findFirst()
                    .ifPresent(master -> {
                        master.setFlag("F");
                        master.setDgftStatus("FAILED");
                        master.setMasterDetailStatus("MSG_PUSH_PROCESS_FAILED");
                        masterRepository.save(master);
                    });
        }
    }
}
