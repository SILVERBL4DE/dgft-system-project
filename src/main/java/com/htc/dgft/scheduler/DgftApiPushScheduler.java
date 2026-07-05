package com.htc.dgft.scheduler;

import com.htc.dgft.entity.DgftOrmMessageDetail;
import com.htc.dgft.entity.DgftOrmMessageMaster;
import com.htc.dgft.entity.DgftOrmMsgTxStatusLog;
import com.htc.dgft.repository.DgftOrmMessageDetailRepository;
import com.htc.dgft.repository.DgftOrmMessageMasterRepository;
import com.htc.dgft.repository.DgftOrmMsgTxStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DgftApiPushScheduler {

    private final DgftOrmMessageMasterRepository messageMasterRepository;
    private final DgftOrmMessageDetailRepository messageDetailRepository;
    private final DgftOrmMsgTxStatusLogRepository txStatusLogRepository;

    @Scheduled(cron = "0 15 10 * * ?")
    @Transactional
    public void executeApiPush() {
        List<DgftOrmMessageMaster> pendingPushes = messageMasterRepository.findByStatus("MSG_PUSH_NEW");
        
        for (DgftOrmMessageMaster master : pendingPushes) {
            // Simulate API Call to DGFT
            // Assuming Success here
            master.setDgftAckStatus("Validated");
            master.setStatus("MSG_PUSH_SUCCESS");
            
            DgftOrmMsgTxStatusLog log = new DgftOrmMsgTxStatusLog();
            log.setDgftOrmMessageMaster(master);
            log.setDgftTxStatusJsonObj("{\"status\": \"SUCCESS\", \"code\": \"200\", \"message\": \"Batch received and validated by DGFT.\"}");
            txStatusLogRepository.save(log);
            
            List<DgftOrmMessageDetail> details = messageDetailRepository.findByDgftOrmMessageMasterId(master.getId());
            for (DgftOrmMessageDetail detail : details) {
                detail.setStatus("PENDING");
                detail.setDgftAckStatus(null);
            }
            
            messageDetailRepository.saveAll(details);
        }
        
        messageMasterRepository.saveAll(pendingPushes);
    }
}
