package com.htc.dgft.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrmMessageMasterResponse {
    private String id;
    private String bankUniqueTransactionId;
    private String status;
    private String dgftAckStatus;
    private String addedBy;
    private LocalDateTime addedDate;
}
