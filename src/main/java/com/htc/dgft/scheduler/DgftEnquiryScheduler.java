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
        List<DgftOrmMessageDetail> pendingDetails = messageDetailRepository.findByStatus("PENDING");
        
        for (DgftOrmMessageDetail detail : pendingDetails) {
            masterRepository.findAll().stream()
                    .filter(orm -> orm.getOrmNumber().equals(detail.getOrmNumber()))
                    .findFirst()
                    .ifPresent(master -> {
                        master.setFlag("P");
                        master.setStatus("ACTIVE");
                        master.setDgftFlag("F");
                        master.setDgftStatus("VALIDATED");
                        master.setMasterDetailStatus("MSG_PUSH_SUCCESS");
                        masterRepository.save(master);
                    });
        }
    }
}
