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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DgftProcessedStatusScheduler {

    private final DgftOrmMessageMasterRepository messageMasterRepository;
    private final DgftOrmMessageDetailRepository messageDetailRepository;
    private final DgftOrmMsgTxStatusLogRepository txStatusLogRepository;
    private final Random random = new Random();

    @Scheduled(cron = "0 45 10 * * ?")
    @Transactional
    public void executeProcessedStatusCheck() {
        List<DgftOrmMessageMaster> pendingMasters = messageMasterRepository.findByStatus("MSG_PUSH_SUCCESS");

        for (DgftOrmMessageMaster master : pendingMasters) {
            // Simulate DGFT review response: mostly success, sometimes fail
            boolean isFullySuccessful = random.nextInt(10) > 2; 

            if (isFullySuccessful) {
                master.setDgftAckStatus("Processed");
                master.setStatus("MSG_PUSH_FULLY_PROCESSED");
                
                DgftOrmMsgTxStatusLog log = new DgftOrmMsgTxStatusLog();
                log.setDgftOrmMessageMaster(master);
                log.setDgftTxStatusJsonObj("{\"status\": \"PROCESSED\", \"message\": \"Batch successfully processed by DGFT without errors.\"}");
                txStatusLogRepository.save(log);
                
                List<DgftOrmMessageDetail> details = messageDetailRepository.findByDgftOrmMessageMasterId(master.getId());
                for (DgftOrmMessageDetail detail : details) {
                    detail.setStatus("PROCESSED");
                    detail.setDgftAckStatus("Processed");
                }
                messageDetailRepository.saveAll(details);
            } else {
                master.setDgftAckStatus("FAILED");
                master.setStatus("MSG_PUSH_PROCESS_FAILED");
                
                DgftOrmMsgTxStatusLog log = new DgftOrmMsgTxStatusLog();
                log.setDgftOrmMessageMaster(master);
                log.setDgftTxStatusJsonObj("{\"status\": \"FAILED\", \"errorCode\": \"ERR-500-DGFT\", \"message\": \"Simulated rejection from government portal.\"}");
                txStatusLogRepository.save(log);
                
                List<DgftOrmMessageDetail> details = messageDetailRepository.findByDgftOrmMessageMasterId(master.getId());
                for (DgftOrmMessageDetail detail : details) {
                    detail.setStatus("FAILED");
                    detail.setDgftAckStatus("Errored");
                    detail.setDgftErrorCodes("ERR-500-DGFT");
                    detail.setDgftErrorDetails("Simulated rejection from government portal.");
                }
                messageDetailRepository.saveAll(details);
            }
        }
        
        messageMasterRepository.saveAll(pendingMasters);
    }
}
