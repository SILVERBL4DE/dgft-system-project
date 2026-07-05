package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "dgft_orm_message_detail")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftOrmMessageDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @ManyToOne
    @JoinColumn(name = "DGFT_ORM_MSG_MASTER_ID")
    private DgftOrmMessageMaster dgftOrmMessageMaster;

    @Column(name = "ORM_NUMBER", length = 50)
    private String ormNumber;

    @Column(name = "ORM_ISSUE_DATE")
    private LocalDate ormIssueDate;

    @Column(length = 50)
    private String status;

    @Column(name = "DGFT_ACK_STATUS", length = 25)
    private String dgftAckStatus;

    @Column(name = "DGFT_ERROR_CODES", length = 100)
    private String dgftErrorCodes;

    @Column(name = "DGFT_ERROR_DETAILS", length = 300)
    private String dgftErrorDetails;
}
