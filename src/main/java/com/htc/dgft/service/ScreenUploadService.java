package com.htc.dgft.service;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import com.htc.dgft.repository.DgftPurposeCodeMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScreenUploadService {

    private final DgftOrmMasterRepository ormMasterRepository;
    private final DgftPurposeCodeMasterRepository purposeCodeMasterRepository;
    private final Validator validator;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String processCsvUpload(MultipartFile file) {
        List<DgftOrmMaster> validRecords = new ArrayList<>();
        List<String> ackLogs = new ArrayList<>();
        ackLogs.add("--- UPLOAD ACKNOWLEDGMENT ---");
        ackLogs.add("Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        ackLogs.add("Original File: " + file.getOriginalFilename());
        ackLogs.add("-----------------------------");

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
                    ackLogs.add("Line " + lineNumber + " - FAILED: Insufficient columns.");
                    lineNumber++;
                    continue;
                }

                try {
                    DgftOrmMaster orm = new DgftOrmMaster();
                    orm.setOrmNumber(columns[0].trim());
                    orm.setOrmIssueDate(parseDate(columns[1].trim()));
                    orm.setIfscCode(columns[2].trim());
                    orm.setAdCode(columns[3].trim());
                    orm.setOrmDate(parseDate(columns[4].trim()));
                    orm.setOrmAmount(new BigDecimal(columns[5].trim()));
                    orm.setOrmCurrency(columns[6].trim());
                    orm.setInrPayableAmount(new BigDecimal(columns[7].trim()));
                    orm.setIeCode(columns[8].trim());
                    orm.setPanNumber(columns[9].trim());
                    orm.setIePan(columns[9].trim());
                    orm.setBeneficiaryName(columns[10].trim());
                    orm.setBeneficiaryCountry(columns[11].trim());
                    
                    String purposeCode = columns[12].trim();
                    if (purposeCodeMasterRepository.findByCode(purposeCode).isEmpty()) {
                        ackLogs.add("Line " + lineNumber + " [" + columns[0].trim() + "] - FAILED: Purpose Code " + purposeCode + " is invalid.");
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

                    Set<ConstraintViolation<DgftOrmMaster>> violations = validator.validate(orm);
                    if (!violations.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (ConstraintViolation<DgftOrmMaster> violation : violations) {
                            sb.append(violation.getMessage()).append("; ");
                        }
                        ackLogs.add("Line " + lineNumber + " [" + orm.getOrmNumber() + "] - FAILED: " + sb.toString());
                        lineNumber++;
                        continue;
                    }

                    validRecords.add(orm);
                    ackLogs.add("Line " + lineNumber + " [" + orm.getOrmNumber() + "] - SUCCESS");
                } catch (Exception e) {
                    ackLogs.add("Line " + lineNumber + " - FAILED: Error parsing data - " + e.getMessage());
                }

                lineNumber++;
            }

            ormMasterRepository.saveAll(validRecords);
            
            // Write ACK file
            String baseFilename = file.getOriginalFilename() != null ? file.getOriginalFilename().replace(".csv", "") : "upload";
            String ackFilename = "ack_" + baseFilename + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            Path ackDirPath = Paths.get("acks");
            if (!Files.exists(ackDirPath)) {
                Files.createDirectories(ackDirPath);
            }
            Path ackFilePath = ackDirPath.resolve(ackFilename);
            Files.write(ackFilePath, ackLogs, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return "Upload completed. Processed " + validRecords.size() + " valid records. ACK file generated at: " + ackFilePath.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file: " + e.getMessage());
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
}
