package com.project.logistic_management.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryDetailReportDTO {
    private Integer userId;             //ma nhan vien
    private String fullname;        //ho va ten
    private Integer roleId;        //vai tro
    private Float basicSalary;      //luong co ban
    private Float allowance;        //tro cap
    private Float totalCommission;  //tien hoa hong
    private Float totalExpenses;    //chi phi phat sinh
    private Float performanceSalary;//luong hieu suat
    private Float advance;          //luong tam ung
    private Float received;         //duoc nhan
}
