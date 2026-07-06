package com.htc.dgft.controller;

import com.htc.dgft.dto.request.PurposeCodeRequest;
import com.htc.dgft.dto.response.PurposeCodeResponse;
import com.htc.dgft.mapper.EntityMapper;
import com.htc.dgft.service.PurposeCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purpose-codes")
@RequiredArgsConstructor
public class PurposeCodeController {

    private final PurposeCodeService service;
    private final EntityMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<PurposeCodeResponse>> getAll() {
        return ResponseEntity.ok(mapper.toPurposeCodeResponseList(service.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<PurposeCodeResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PurposeCodeResponse> create(@Valid @RequestBody PurposeCodeRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.create(request)));
    }
}
