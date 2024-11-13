package com.project.logistic_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "schedule_config_id")
    private Integer scheduleConfigId;

    @Column(name = "truck_id")
    private Integer truckId;

    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "path_attach_document")
    private String pathAttachDocument;

    @Column(name = "departure_time")
    private Date departureTime;

    @Column(name = "arrival_time")
    private Date arrivalTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "expenses_status")
    private Integer expensesStatus;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
