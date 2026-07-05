package com.htc.dgft.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.entity.DgftOrmMessageDetail;
import com.htc.dgft.entity.DgftOrmMessageMaster;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import com.htc.dgft.repository.DgftOrmMessageDetailRepository;
import com.htc.dgft.repository.DgftOrmMessageMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DgftOrmPushScheduler {

    private final DgftOrmMasterRepository masterRepository;
    private final DgftOrmMessageMasterRepository messageMasterRepository;
    private final DgftOrmMessageDetailRepository messageDetailRepository;

    @Scheduled(cron = "0 0 10 * * ?") // Can be triggered manually or runs at 10 AM
    @Transactional
    public void executePush() {
        List<DgftOrmMaster> pendingRecords = masterRepository.findByDgftStatus("Awaiting request initiated");
        if (pendingRecords.isEmpty()) {
            return;
        }

        int batchSize = 20;
        for (int i = 0; i < pendingRecords.size(); i += batchSize) {
            List<DgftOrmMaster> batch = pendingRecords.subList(i, Math.min(i + batchSize, pendingRecords.size()));
            processBatch(batch);
        }
    }

    private void processBatch(List<DgftOrmMaster> batch) {
        String uniqueTxId = "TXN-" + UUID.randomUUID().toString().substring(0, 15).toUpperCase();
        
        DgftOrmMessageMaster messageMaster = new DgftOrmMessageMaster();
        messageMaster.setUniqueTxId(uniqueTxId);
        messageMaster.setStatus("MSG_PUSH_NEW");
        messageMaster.setDgftAckStatus("REQUEST_INITIATED");
        messageMaster.setDgftPushInitTime(LocalDateTime.now());
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonPayload = mapper.writeValueAsString(batch);
            messageMaster.setRequestJsonObj(jsonPayload);
        } catch (Exception e) {
            messageMaster.setRequestJsonObj("{}");
        }

        messageMaster = messageMasterRepository.save(messageMaster);

        List<DgftOrmMessageDetail> details = new ArrayList<>();
        for (DgftOrmMaster orm : batch) {
            DgftOrmMessageDetail detail = new DgftOrmMessageDetail();
            detail.setDgftOrmMessageMaster(messageMaster);
            detail.setOrmNumber(orm.getOrmNumber());
            detail.setOrmIssueDate(orm.getOrmIssueDate());
            detail.setStatus("NEW");
            details.add(detail);

            // Update main table
            orm.setFlag("P");
            orm.setDgftStatus("Request initiated");
            orm.setBankUniqueTransactionId(uniqueTxId);
        }
        
        messageDetailRepository.saveAll(details);
        masterRepository.saveAll(batch);
    }
}
