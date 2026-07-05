package com.htc.dgft.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DgftOrmMasterResponseVO {
    private String id;
    private String ormNumber;
    private BigDecimal ormAmount;
    private LocalDate ormDate;
    private String ormCurrency;
    private String adCode;
    private String ieCode;
    private String beneficiaryName;
    private String purposeCode;
    
    // We intentionally exclude PAN numbers and sensitive flags from the VO
    
    private String status;
    private String dgftStatus;
    private String bankUniqueTransactionId;
    private String addedBy;
    private LocalDate addedDate;
}
