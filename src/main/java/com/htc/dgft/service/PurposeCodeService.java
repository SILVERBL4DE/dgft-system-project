package com.htc.dgft.service;

import com.htc.dgft.dto.request.PurposeCodeRequest;
import com.htc.dgft.entity.DgftPurposeCodeMaster;
import com.htc.dgft.exception.ResourceNotFoundException;
import com.htc.dgft.repository.DgftPurposeCodeMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurposeCodeService {

    private final DgftPurposeCodeMasterRepository repository;

    public List<DgftPurposeCodeMaster> getAll() {
        return repository.findAll();
    }

    public DgftPurposeCodeMaster create(PurposeCodeRequest request) {
        DgftPurposeCodeMaster p = new DgftPurposeCodeMaster();
        p.setCode(request.getCode());
        p.setDescription(request.getDescription());
        p.setStatus(request.getStatus());
        return repository.save(p);
    }

    public DgftPurposeCodeMaster getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purpose Code not found"));
    }
}
