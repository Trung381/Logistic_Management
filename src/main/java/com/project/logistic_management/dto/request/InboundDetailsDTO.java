package com.project.logistic_management.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundDetailsDTO {
    private Integer goodsId;
    private String origin;
    private Integer quantity;
}
