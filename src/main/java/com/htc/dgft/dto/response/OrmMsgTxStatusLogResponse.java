package com.htc.dgft.dto.response;

import lombok.Data;

@Data
public class OrmMsgTxStatusLogResponse {
    private String id;
    private String dgftOrmMessageMasterId;
    private String dgftTxStatusJsonObj;
}
