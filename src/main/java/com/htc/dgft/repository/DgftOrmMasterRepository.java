package com.htc.dgft.repository;

import com.htc.dgft.entity.DgftOrmMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DgftOrmMasterRepository extends JpaRepository<DgftOrmMaster, String> {
    List<DgftOrmMaster> findByDgftStatus(String dgftStatus);
    List<DgftOrmMaster> findByStatusAndDgftStatus(String status, String dgftStatus);
}
