package com.htc.dgft.service;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.entity.DgftOrmMasterHis;
import com.htc.dgft.exception.ResourceNotFoundException;
import com.htc.dgft.repository.DgftOrmMasterHisRepository;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrmMasterService {

    private final DgftOrmMasterRepository ormMasterRepository;
    private final DgftOrmMasterHisRepository ormMasterHisRepository;

    public List<DgftOrmMaster> getAllRecords() {
        return ormMasterRepository.findAll();
    }

    public DgftOrmMaster getRecordById(String id) {
        return ormMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ORM Record not found with id: " + id));
    }

    @Transactional
    public void deleteRecord(String id) {
        DgftOrmMaster record = getRecordById(id);
        
        // Save History Snapshot
        createHistorySnapshot(record, "CANCELLED");
        
        // Simulating the logic: mark as cancelled instead of hard deleting
        record.setStatus("CANCELLED");
        record.setFlag("C");
        record.setDgftFlag("C");
        record.setDgftStatus("Awaiting request initiated");
        
        ormMasterRepository.save(record);
    }

    private void createHistorySnapshot(DgftOrmMaster orm, String triggerStatus) {
        DgftOrmMasterHis his = new DgftOrmMasterHis();
        his.setTriggerDate(LocalDateTime.now());
        his.setOrmId(orm.getId());
        his.setTriggerStatus(triggerStatus);
        
        his.setOrmNumber(orm.getOrmNumber());
        his.setOrmAmount(orm.getOrmAmount());
        his.setOrmDate(orm.getOrmDate());
        his.setAdCode(orm.getAdCode());
        his.setOrmCurrency(orm.getOrmCurrency());
        his.setIeCode(orm.getIeCode());
        his.setBeneficiaryName(orm.getBeneficiaryName());
        his.setPurposeCode(orm.getPurposeCode());
        his.setStatus(orm.getStatus());
        his.setBankUniqueTransactionId(orm.getBankUniqueTransactionId());
        
        ormMasterHisRepository.save(his);
    }
}
