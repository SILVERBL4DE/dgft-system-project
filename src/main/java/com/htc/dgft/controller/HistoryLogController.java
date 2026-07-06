package com.htc.dgft.controller;

import com.htc.dgft.dto.response.OrmMasterHisResponse;
import com.htc.dgft.dto.response.OrmMsgTxStatusLogResponse;
import com.htc.dgft.mapper.EntityMapper;
import com.htc.dgft.service.HistoryLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class HistoryLogController {

    private final HistoryLogService service;
    private final EntityMapper mapper;

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<OrmMasterHisResponse>> getAllHistory() {
        return ResponseEntity.ok(mapper.toMasterHisResponseList(service.getAllHistory()));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<OrmMsgTxStatusLogResponse>> getAllTxLogs() {
        return ResponseEntity.ok(mapper.toMsgTxStatusLogResponseList(service.getAllTxLogs()));
    }
}
