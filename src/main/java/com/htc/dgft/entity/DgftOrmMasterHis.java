package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dgft_orm_master_his")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftOrmMasterHis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @Column(name = "TRIGGER_DATE")
    private LocalDateTime triggerDate;

    @Column(name = "ORM_ID", length = 200)
    private String ormId;

    @Column(name = "TRIGGER_STATUS", length = 20)
    private String triggerStatus;

    // Remaining fields mirror the DgftOrmMaster table fields
    @Column(name = "ORM_NUMBER", length = 50)
    private String ormNumber;

    @Column(name = "ORM_AMOUNT", precision = 16, scale = 4)
    private BigDecimal ormAmount;

    @Column(name = "ORM_DATE")
    private LocalDate ormDate;

    @Column(name = "AD_CODE", length = 7)
    private String adCode;

    @Column(name = "ORM_CURRENCY", length = 3)
    private String ormCurrency;

    @Column(name = "IE_CODE", length = 10)
    private String ieCode;

    @Column(name = "BENEFICIARY_NAME", length = 100)
    private String beneficiaryName;

    @Column(name = "PURPOSE_CODE", length = 20)
    private String purposeCode;

    @Column(length = 50)
    private String status;

    @Column(name = "BANK_UNIQUE_TRANSACTION_ID", length = 100)
    private String bankUniqueTransactionId;
}
