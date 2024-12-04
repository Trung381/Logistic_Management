package com.project.logistic_management.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InboundDTO {
    private Integer intakeUserId;
    private List<InboundDetailsDTO> details;
}
