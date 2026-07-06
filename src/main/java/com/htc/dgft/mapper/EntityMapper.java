package com.htc.dgft.mapper;

import com.htc.dgft.dto.request.PurposeCodeRequest;
import com.htc.dgft.dto.response.*;
import com.htc.dgft.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public PurposeCodeResponse toResponse(DgftPurposeCodeMaster entity) {
        if (entity == null) return null;
        PurposeCodeResponse dto = new PurposeCodeResponse();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }
    
    public DgftPurposeCodeMaster toEntity(PurposeCodeRequest request) {
        if (request == null) return null;
        DgftPurposeCodeMaster entity = new DgftPurposeCodeMaster();
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        return entity;
    }

    public OrmMessageMasterResponse toResponse(DgftOrmMessageMaster entity) {
        if (entity == null) return null;
        OrmMessageMasterResponse dto = new OrmMessageMasterResponse();
        dto.setId(entity.getId());
        dto.setBankUniqueTransactionId(entity.getUniqueTxId());
        dto.setStatus(entity.getStatus());
        dto.setDgftAckStatus(entity.getDgftAckStatus());
        dto.setAddedBy(entity.getAddedBy());
        if (entity.getAddedDate() != null) dto.setAddedDate(entity.getAddedDate());
        return dto;
    }

    public OrmMessageDetailResponse toResponse(DgftOrmMessageDetail entity) {
        if (entity == null) return null;
        OrmMessageDetailResponse dto = new OrmMessageDetailResponse();
        dto.setId(entity.getId());
        if (entity.getDgftOrmMessageMaster() != null) dto.setDgftOrmMessageMasterId(entity.getDgftOrmMessageMaster().getId());
        // dgftOrmMaster relationship is not explicitly mapped in Detail entity, we rely on ormNumber instead if needed
        dto.setStatus(entity.getStatus());
        dto.setAddedBy(entity.getAddedBy());
        if (entity.getAddedDate() != null) dto.setAddedDate(entity.getAddedDate());
        return dto;
    }

    public OrmMasterHisResponse toResponse(DgftOrmMasterHis entity) {
        if (entity == null) return null;
        OrmMasterHisResponse dto = new OrmMasterHisResponse();
        dto.setId(entity.getId());
        dto.setOrmId(entity.getOrmId());
        dto.setTriggerStatus(entity.getTriggerStatus());
        dto.setTriggerDate(entity.getTriggerDate());
        dto.setOrmNumber(entity.getOrmNumber());
        dto.setOrmAmount(entity.getOrmAmount());
        dto.setOrmDate(entity.getOrmDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public OrmMsgTxStatusLogResponse toResponse(DgftOrmMsgTxStatusLog entity) {
        if (entity == null) return null;
        OrmMsgTxStatusLogResponse dto = new OrmMsgTxStatusLogResponse();
        dto.setId(entity.getId());
        if (entity.getDgftOrmMessageMaster() != null) dto.setDgftOrmMessageMasterId(entity.getDgftOrmMessageMaster().getId());
        dto.setDgftTxStatusJsonObj(entity.getDgftTxStatusJsonObj());
        return dto;
    }

    public UserResponse toResponse(User entity) {
        if (entity == null) return null;
        UserResponse dto = new UserResponse();
        dto.setId(String.valueOf(entity.getId()));
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toList()));
        }
        return dto;
    }

    // Utility methods for collections
    public List<PurposeCodeResponse> toPurposeCodeResponseList(List<DgftPurposeCodeMaster> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrmMessageMasterResponse> toMessageMasterResponseList(List<DgftOrmMessageMaster> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrmMessageDetailResponse> toMessageDetailResponseList(List<DgftOrmMessageDetail> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrmMasterHisResponse> toMasterHisResponseList(List<DgftOrmMasterHis> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
    
    public List<OrmMsgTxStatusLogResponse> toMsgTxStatusLogResponseList(List<DgftOrmMsgTxStatusLog> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<UserResponse> toUserResponseList(List<User> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
