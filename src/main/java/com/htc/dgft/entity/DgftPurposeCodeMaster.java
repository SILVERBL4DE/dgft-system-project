package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "dgft_purpose_code_master")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftPurposeCodeMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @Column(length = 20)
    private String code;

    @Column(length = 200)
    private String description;

    @Column(length = 20)
    private String status;

    @Column(name = "PURPOSE_GROUP", length = 2)
    private String purposeGroup;

    @Column(name = "GROUP_NAME", length = 50)
    private String groupName;

    @Column(name = "APPLICATION_TYPE", length = 4)
    private String applicationType;

    @Column(length = 200)
    private String remarks;
}
