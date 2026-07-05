package com.htc.dgft.mapper;

import com.htc.dgft.dto.response.DgftOrmMasterResponseVO;
import com.htc.dgft.entity.DgftOrmMaster;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrmMapper {

    public DgftOrmMasterResponseVO toResponseVO(DgftOrmMaster entity) {
        if (entity == null) return null;
        
        DgftOrmMasterResponseVO vo = new DgftOrmMasterResponseVO();
        vo.setId(entity.getId());
        vo.setOrmNumber(entity.getOrmNumber());
        vo.setOrmAmount(entity.getOrmAmount());
        vo.setOrmDate(entity.getOrmDate());
        vo.setOrmCurrency(entity.getOrmCurrency());
        vo.setAdCode(entity.getAdCode());
        vo.setIeCode(entity.getIeCode());
        vo.setBeneficiaryName(entity.getBeneficiaryName());
        vo.setPurposeCode(entity.getPurposeCode());
        vo.setStatus(entity.getStatus());
        vo.setDgftStatus(entity.getDgftStatus());
        vo.setBankUniqueTransactionId(entity.getBankUniqueTransactionId());
        
        vo.setAddedBy(entity.getAddedBy());
        if (entity.getAddedDate() != null) {
            vo.setAddedDate(entity.getAddedDate().toLocalDate());
        }
        
        return vo;
    }

    public List<DgftOrmMasterResponseVO> toResponseVOList(List<DgftOrmMaster> entities) {
        return entities.stream().map(this::toResponseVO).collect(Collectors.toList());
    }
}
