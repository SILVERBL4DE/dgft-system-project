package com.htc.dgft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "ORM Number is mandatory")
    @Size(max = 50, message = "ORM Number cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ORM Number must be alphanumeric")
    @Column(name = "ORM_NUMBER", length = 50)
    private String ormNumber;

    @NotNull(message = "ORM Amount is mandatory")
    @DecimalMin(value = "1.00", message = "ORM Amount must be greater than 1")
    @Column(name = "ORM_AMOUNT", precision = 16, scale = 4)
    private BigDecimal ormAmount;

    @NotNull(message = "ORM Date is mandatory")
    @PastOrPresent(message = "ORM Date cannot be in the future")
    @Column(name = "ORM_DATE")
    private LocalDate ormDate;

    @Column(name = "AD_CODE_ID", length = 200)
    private String adCodeId;

    @NotBlank(message = "AD Code is mandatory")
    @Size(min = 7, max = 7, message = "AD Code must be exactly 7 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "AD Code must be alphanumeric")
    @Column(name = "AD_CODE", length = 7)
    private String adCode;

    @NotBlank(message = "Currency is mandatory")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    @Column(name = "ORM_CURRENCY", length = 3)
    private String ormCurrency;

    @Size(max = 10, message = "IE Code cannot exceed 10 characters")
    @Column(name = "IE_CODE", length = 10)
    private String ieCode;

    @Column(name = "IE_NAME", length = 100)
    private String ieName;

    @Column(name = "IE_PAN", length = 10)
    private String iePan;

    @NotBlank(message = "Beneficiary Name is mandatory")
    @Size(max = 200, message = "Beneficiary Name cannot exceed 200 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_/\\\\.,;:*!#$@+^?\\s]+$", message = "Beneficiary Name contains invalid characters")
    @Column(name = "BENEFICIARY_NAME", length = 100)
    private String beneficiaryName;

    @NotBlank(message = "Beneficiary Country is mandatory")
    @Size(max = 20, message = "Beneficiary Country cannot exceed 20 characters")
    @Column(name = "BENEFICIARY_COUNTRY", length = 3)
    private String beneficiaryCountry;

    @NotBlank(message = "Purpose Code is mandatory")
    @Size(max = 10, message = "Purpose Code cannot exceed 10 characters")
    @Column(name = "PURPOSE_CODE", length = 20)
    private String purposeCode;

    @NotBlank(message = "IFSC Code is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9]{11}$", message = "IFSC Code must be 11 alphanumeric characters")
    @Column(name = "IFSC_CODE", length = 11)
    private String ifscCode;

    @Column(name = "INR_PAYABLE_AMOUNT", precision = 16, scale = 2)
    private BigDecimal inrPayableAmount;

    @NotBlank(message = "PAN Number is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9]{10}$", message = "PAN Number must be 10 alphanumeric characters")
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

    @PastOrPresent(message = "ORM Issue Date cannot be in the future")
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
