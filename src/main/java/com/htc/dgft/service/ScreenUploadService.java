package com.htc.dgft.service;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import com.htc.dgft.repository.DgftPurposeCodeMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenUploadService {

    private final DgftOrmMasterRepository ormMasterRepository;
    private final DgftPurposeCodeMasterRepository purposeCodeMasterRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String processCsvUpload(MultipartFile file) {
        List<DgftOrmMaster> validRecords = new ArrayList<>();
        List<String> errorLogs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    lineNumber++;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    lineNumber++;
                    continue;
                }

                String[] columns = line.split("\\|");
                if (columns.length < 13) {
                    errorLogs.add("Line " + lineNumber + ": Insufficient columns.");
                    lineNumber++;
                    continue;
                }

                try {
                    DgftOrmMaster orm = new DgftOrmMaster();
                    orm.setOrmNumber(columns[0].trim());
                    orm.setOrmIssueDate(parseDate(columns[1].trim()));
                    orm.setIfscCode(columns[2].trim());
                    orm.setAdCode(columns[3].trim());
                    
                    if (orm.getAdCode().length() != 7) {
                        errorLogs.add("Line " + lineNumber + ": AD Code must be 7 characters.");
                        lineNumber++;
                        continue;
                    }

                    orm.setOrmDate(parseDate(columns[4].trim()));
                    orm.setOrmAmount(new BigDecimal(columns[5].trim()));
                    orm.setOrmCurrency(columns[6].trim());
                    orm.setInrPayableAmount(new BigDecimal(columns[7].trim()));
                    
                    orm.setIeCode(columns[8].trim());
                    if (orm.getIeCode().length() != 10) {
                        errorLogs.add("Line " + lineNumber + ": IE Code must be 10 characters.");
                        lineNumber++;
                        continue;
                    }
                    
                    orm.setPanNumber(columns[9].trim());
                    orm.setIePan(columns[9].trim());
                    orm.setBeneficiaryName(columns[10].trim());
                    orm.setBeneficiaryCountry(columns[11].trim());
                    
                    String purposeCode = columns[12].trim();
                    if (purposeCodeMasterRepository.findByCode(purposeCode).isEmpty()) {
                        errorLogs.add("Line " + lineNumber + ": Purpose Code " + purposeCode + " is invalid.");
                        lineNumber++;
                        continue;
                    }
                    orm.setPurposeCode(purposeCode);
                    
                    if (columns.length >= 14) {
                        orm.setReferenceIrm(columns[13].trim());
                    }

                    // Default values for a fresh record
                    orm.setStatus("ACTIVE");
                    orm.setFlag("N"); // N implies workflow required
                    orm.setDgftFlag("F"); // F for Fresh
                    orm.setDgftStatus("Awaiting request initiated");

                    validRecords.add(orm);
                } catch (Exception e) {
                    errorLogs.add("Line " + lineNumber + ": Error parsing data - " + e.getMessage());
                }

                lineNumber++;
            }

            ormMasterRepository.saveAll(validRecords);
            
            if (!errorLogs.isEmpty()) {
                return "Successfully processed " + validRecords.size() + " records. Errors encountered:\n" + String.join("\n", errorLogs);
            }

            return "Successfully processed " + validRecords.size() + " records with zero errors.";

        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file: " + e.getMessage());
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
}
