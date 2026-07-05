package com.htc.dgft.scheduler;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DgftCsvExportScheduler {

    private final DgftOrmMasterRepository masterRepository;
    
    // We export records that have reached their final states.
    @Scheduled(cron = "0 0 11 * * ?")
    @Transactional(readOnly = true)
    public void executeCsvExport() {
        List<DgftOrmMaster> processedRecords = masterRepository.findByDgftStatus("PROCESSED");
        List<DgftOrmMaster> failedRecords = masterRepository.findByDgftStatus("FAILED");
        
        if (processedRecords.isEmpty() && failedRecords.isEmpty()) {
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "dgft_export_report_" + timestamp + ".csv";

        try {
            File exportDir = new File("exports");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File exportFile = new File(exportDir, fileName);
            try (PrintWriter pw = new PrintWriter(new FileWriter(exportFile))) {
                pw.println("ORM_NUMBER,IE_CODE,AD_CODE,AMOUNT,CURRENCY,FINAL_STATUS,ERROR_CODES");
                
                for (DgftOrmMaster orm : processedRecords) {
                    pw.printf("%s,%s,%s,%s,%s,%s,%s%n",
                            orm.getOrmNumber(), orm.getIeCode(), orm.getAdCode(), 
                            orm.getOrmAmount(), orm.getOrmCurrency(), orm.getDgftStatus(), "");
                }
                
                for (DgftOrmMaster orm : failedRecords) {
                    pw.printf("%s,%s,%s,%s,%s,%s,%s%n",
                            orm.getOrmNumber(), orm.getIeCode(), orm.getAdCode(), 
                            orm.getOrmAmount(), orm.getOrmCurrency(), orm.getDgftStatus(), orm.getErrorCodes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
