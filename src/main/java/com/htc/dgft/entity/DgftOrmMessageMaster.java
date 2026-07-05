package com.htc.dgft.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "dgft_orm_message_master")
@Data
@EqualsAndHashCode(callSuper = true)
public class DgftOrmMessageMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 200)
    private String id;

    @Column(name = "UNIQUE_TX_ID", length = 100)
    private String uniqueTxId;

    @Lob
    @Column(name = "REQUEST_JSON_OBJ", columnDefinition = "LONGTEXT")
    private String requestJsonObj;

    @Lob
    @Column(name = "RESPONSE_JSON_OBJ", columnDefinition = "LONGTEXT")
    private String responseJsonObj;

    @Column(name = "DGFT_ACK_STATUS", length = 25)
    private String dgftAckStatus;

    @Column(length = 50)
    private String status;

    @Column(name = "DGFT_MSG_PUSH_ERROR", length = 50)
    private String dgftMsgPushError;

    @Lob
    @Column(name = "DGFT_LAST_TX_STATUS_JSON_OBJ", columnDefinition = "LONGTEXT")
    private String dgftLastTxStatusJsonObj;

    @Column(name = "DGFT_PUSH_INIT_TIME")
    private LocalDateTime dgftPushInitTime;

    @Column(name = "DGFT_LAST_TX_STATUS_INIT_TIME")
    private LocalDateTime dgftLastTxStatusInitTime;
}
