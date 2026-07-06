package com.htc.dgft.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrmMessageDetailResponse {
    private String id;
    private String dgftOrmMessageMasterId;
    private String dgftOrmMasterId;
    private String status;
    private String addedBy;
    private LocalDateTime addedDate;
}
