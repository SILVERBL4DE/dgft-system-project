package com.htc.dgft.repository;

import com.htc.dgft.entity.DgftOrmMessageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DgftOrmMessageDetailRepository extends JpaRepository<DgftOrmMessageDetail, String> {
    List<DgftOrmMessageDetail> findByStatus(String status);
    List<DgftOrmMessageDetail> findByDgftOrmMessageMasterId(String masterId);
}
