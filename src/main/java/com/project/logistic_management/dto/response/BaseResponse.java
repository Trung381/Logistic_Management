package com.project.logistic_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResponse<T> {
    private String status;
    private T data;
    private String message;

    public static <T> BaseResponse<T> ok(T data) {
        return null;
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return null;
    }
}
