package com.htc.dgft.controller;

import com.htc.dgft.scheduler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SchedulerTriggerController {

    private final DgftOrmPushScheduler ormPushScheduler;
    private final DgftApiPushScheduler apiPushScheduler;
    private final DgftEnquiryScheduler enquiryScheduler;
    private final DgftProcessedStatusScheduler processedStatusScheduler;
    private final DgftCsvExportScheduler csvExportScheduler;

    @PostMapping("/push-orms")
    public ResponseEntity<String> triggerPushOrms() {
        ormPushScheduler.executePush();
        return ResponseEntity.ok("ORM Push to staging completed successfully.");
    }

    @PostMapping("/api-push")
    public ResponseEntity<String> triggerApiPush() {
        apiPushScheduler.executeApiPush();
        return ResponseEntity.ok("API simulation push to DGFT completed successfully.");
    }

    @PostMapping("/enquiry")
    public ResponseEntity<String> triggerEnquiry() {
        enquiryScheduler.executeEnquiry();
        return ResponseEntity.ok("Enquiry status sync completed successfully.");
    }

    @PostMapping("/process-status")
    public ResponseEntity<String> triggerProcessStatus() {
        processedStatusScheduler.executeProcessedStatusCheck();
        return ResponseEntity.ok("Process Status check (success/fail mock) completed successfully.");
    }

    @PostMapping("/export-csv")
    public ResponseEntity<String> triggerCsvExport() {
        csvExportScheduler.executeCsvExport();
        return ResponseEntity.ok("CSV Export of final records completed successfully.");
    }
    
    @PostMapping("/run-all")
    public ResponseEntity<String> triggerAllSequential() {
        ormPushScheduler.executePush();
        apiPushScheduler.executeApiPush();
        enquiryScheduler.executeEnquiry();
        processedStatusScheduler.executeProcessedStatusCheck();
        csvExportScheduler.executeCsvExport();
        return ResponseEntity.ok("Full end-to-end DGFT flow triggered successfully.");
    }
}
