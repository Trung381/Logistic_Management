package com.project.logistic_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "advance_payment_detail")
public class AdvancePaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "advance_payment")
    private Float advancePayment;

    @Column(name = "status")
    private Integer status;

    @Column(name = "period")
    private String period;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
