package com.project.logistic_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "allowance")
    private Float allowance;

    @Column(name = "basic_salary")
    private Float basicSalary;

    @Column(name = "advance")
    private Float advance;

    @Column(name = "period")
    private String period; //YYYY-MM

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
