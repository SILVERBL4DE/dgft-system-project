package com.htc.dgft.service;

import com.htc.dgft.entity.DgftOrmMasterHis;
import com.htc.dgft.entity.DgftOrmMsgTxStatusLog;
import com.htc.dgft.repository.DgftOrmMasterHisRepository;
import com.htc.dgft.repository.DgftOrmMsgTxStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryLogService {

    private final DgftOrmMasterHisRepository hisRepository;
    private final DgftOrmMsgTxStatusLogRepository txLogRepository;

    public List<DgftOrmMasterHis> getAllHistory() {
        return hisRepository.findAll();
    }

    public List<DgftOrmMsgTxStatusLog> getAllTxLogs() {
        return txLogRepository.findAll();
    }
}
