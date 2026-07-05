package com.htc.dgft.controller;

import com.htc.dgft.service.ScreenUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class ScreenUploadController {

    private final ScreenUploadService screenUploadService;

    @PostMapping(value = "/orm", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER')")
    public ResponseEntity<String> uploadOrmCsv(@RequestParam("file") MultipartFile file) {
        String result = screenUploadService.processCsvUpload(file);
        return ResponseEntity.ok(result);
    }
}
