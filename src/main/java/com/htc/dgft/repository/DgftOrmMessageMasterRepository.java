package com.htc.dgft.repository;

import com.htc.dgft.entity.DgftOrmMessageMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DgftOrmMessageMasterRepository extends JpaRepository<DgftOrmMessageMaster, String> {
    List<DgftOrmMessageMaster> findByStatus(String status);
}
