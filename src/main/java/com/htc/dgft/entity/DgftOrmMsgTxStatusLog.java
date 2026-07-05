package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "dgft_orm_msg_tx_status_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftOrmMsgTxStatusLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @ManyToOne
    @JoinColumn(name = "DGFT_ORM_MSG_MASTER_ID")
    private DgftOrmMessageMaster dgftOrmMessageMaster;

    @Lob
    @Column(name = "DGFT_TX_STATUS_JSON_OBJ", columnDefinition = "LONGTEXT")
    private String dgftTxStatusJsonObj;
}
