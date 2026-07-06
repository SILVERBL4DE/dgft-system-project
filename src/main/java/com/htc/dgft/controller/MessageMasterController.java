package com.htc.dgft.controller;

import com.htc.dgft.dto.response.OrmMessageDetailResponse;
import com.htc.dgft.dto.response.OrmMessageMasterResponse;
import com.htc.dgft.mapper.EntityMapper;
import com.htc.dgft.service.MessageMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class MessageMasterController {

    private final MessageMasterService service;
    private final EntityMapper mapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<OrmMessageMasterResponse>> getAllBatches() {
        return ResponseEntity.ok(mapper.toMessageMasterResponseList(service.getAllBatches()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<OrmMessageMasterResponse> getBatchById(@PathVariable String id) {
        return ResponseEntity.ok(mapper.toResponse(service.getBatchById(id)));
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<OrmMessageDetailResponse>> getDetailsForBatch(@PathVariable String id) {
        return ResponseEntity.ok(mapper.toMessageDetailResponseList(service.getDetailsForBatch(id)));
    }
}
