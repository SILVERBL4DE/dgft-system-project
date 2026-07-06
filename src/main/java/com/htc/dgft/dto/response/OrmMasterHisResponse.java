package com.htc.dgft.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrmMasterHisResponse {
    private String id;
    private String ormId;
    private String triggerStatus;
    private LocalDateTime triggerDate;
    private String ormNumber;
    private BigDecimal ormAmount;
    private LocalDate ormDate;
    private String status;
}
