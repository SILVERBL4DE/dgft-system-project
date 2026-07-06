package com.htc.dgft.service;

import com.htc.dgft.entity.DgftOrmMessageDetail;
import com.htc.dgft.entity.DgftOrmMessageMaster;
import com.htc.dgft.exception.ResourceNotFoundException;
import com.htc.dgft.repository.DgftOrmMessageDetailRepository;
import com.htc.dgft.repository.DgftOrmMessageMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageMasterService {

    private final DgftOrmMessageMasterRepository masterRepository;
    private final DgftOrmMessageDetailRepository detailRepository;

    public List<DgftOrmMessageMaster> getAllBatches() {
        return masterRepository.findAll();
    }

    public DgftOrmMessageMaster getBatchById(String id) {
        return masterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));
    }

    public List<DgftOrmMessageDetail> getDetailsForBatch(String batchId) {
        return detailRepository.findByDgftOrmMessageMasterId(batchId);
    }
}
