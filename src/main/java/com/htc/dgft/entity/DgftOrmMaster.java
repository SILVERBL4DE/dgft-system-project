package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dgft_orm_master")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftOrmMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @Column(name = "ORM_NUMBER", length = 50)
    private String ormNumber;

    @Column(name = "ORM_AMOUNT", precision = 16, scale = 4)
    private BigDecimal ormAmount;

    @Column(name = "ORM_DATE")
    private LocalDate ormDate;

    @Column(name = "AD_CODE_ID", length = 200)
    private String adCodeId;

    @Column(name = "AD_CODE", length = 7)
    private String adCode;

    @Column(name = "ORM_CURRENCY", length = 3)
    private String ormCurrency;

    @Column(name = "IE_CODE", length = 10)
    private String ieCode;

    @Column(name = "IE_NAME", length = 100)
    private String ieName;

    @Column(name = "IE_PAN", length = 10)
    private String iePan;

    @Column(name = "BENEFICIARY_NAME", length = 100)
    private String beneficiaryName;

    @Column(name = "BENEFICIARY_COUNTRY", length = 3)
    private String beneficiaryCountry;

    @Column(name = "PURPOSE_CODE", length = 20)
    private String purposeCode;

    @Column(name = "IFSC_CODE", length = 11)
    private String ifscCode;

    @Column(name = "INR_PAYABLE_AMOUNT", precision = 16, scale = 2)
    private BigDecimal inrPayableAmount;

    @Column(name = "PAN_NUMBER", length = 10)
    private String panNumber;

    @Column(length = 50)
    private String status;

    @Column(length = 200)
    private String remarks;

    @Column(name = "INBOUND_FILE_NAME", length = 100)
    private String inboundFileName;

    @Column(length = 1)
    private String flag;

    @Column(name = "DGFT_FLAG", length = 1)
    private String dgftFlag;

    @Column(name = "ERROR_CODES", length = 200)
    private String errorCodes;

    @Column(name = "CHECKER_REMARKS", length = 250)
    private String checkerRemarks;

    @Column(name = "BANK_UNIQUE_TRANSACTION_ID", length = 100) // Increased slightly for UUID usage
    private String bankUniqueTransactionId;

    @Column(name = "REFERENCE_IRM", length = 50)
    private String referenceIrm;

    @Column(name = "ORM_ISSUE_DATE")
    private LocalDate ormIssueDate;

    @Column(name = "REMITTANCE_TYPE", length = 1)
    private String remittanceType;

    @Column(name = "BANK_ACCOUNT_NUMBER", length = 25)
    private String bankAccountNumber;

    @Column(name = "DGFT_STATUS", length = 250)
    private String dgftStatus;

    @Column(name = "PROCESS_STATUS", length = 50) // Adjust length
    private String processStatus;

    @Column(name = "MASTER_DETAIL_STATUS", length = 50)
    private String masterDetailStatus;

    @Column(name = "ORM_CURRENCY_ID", length = 200)
    private String ormCurrencyId;

    @Column(name = "BENEFICIARY_COUNTRY_ID", length = 200)
    private String beneficiaryCountryId;

    @Column(name = "BILL_REFERENCE_NUMBER", length = 50)
    private String billReferenceNumber;
}
