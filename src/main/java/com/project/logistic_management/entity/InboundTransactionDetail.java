package com.project.logistic_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inbound_transaction_detail")
public class InboundTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inbound_transaction_id")
    private Integer inboundTransactionId;

    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "quantity")
    private Integer quantity;
}
