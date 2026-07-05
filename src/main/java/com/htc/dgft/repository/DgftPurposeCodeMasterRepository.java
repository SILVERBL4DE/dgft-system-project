package com.htc.dgft.repository;

import com.htc.dgft.entity.DgftPurposeCodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DgftPurposeCodeMasterRepository extends JpaRepository<DgftPurposeCodeMaster, String> {
    Optional<DgftPurposeCodeMaster> findByCode(String code);
}
