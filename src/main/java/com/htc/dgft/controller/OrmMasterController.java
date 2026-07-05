package com.htc.dgft.controller;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.dto.response.DgftOrmMasterResponseVO;
import com.htc.dgft.mapper.OrmMapper;
import com.htc.dgft.service.OrmMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orm")
@RequiredArgsConstructor
public class OrmMasterController {

    private final OrmMasterService ormMasterService;
    private final OrmMapper ormMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<List<DgftOrmMasterResponseVO>> getAllRecords() {
        return ResponseEntity.ok(ormMapper.toResponseVOList(ormMasterService.getAllRecords()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAKER', 'CHECKER', 'VIEWER')")
    public ResponseEntity<DgftOrmMasterResponseVO> getRecordById(@PathVariable String id) {
        return ResponseEntity.ok(ormMapper.toResponseVO(ormMasterService.getRecordById(id)));
    }

    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("@ormSecurity.canModify(#id)")
    public ResponseEntity<Void> cancelRecord(@PathVariable String id) {
        ormMasterService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
