package com.next.intune.common.api;

public record ApiResult<T>(String code, String message, T result) {
    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), result);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResult<T> fail(ResponseCode responseCode) {
        return new ApiResult<>(responseCode.getCode(), responseCode.getMessage(), null);
    }
}
