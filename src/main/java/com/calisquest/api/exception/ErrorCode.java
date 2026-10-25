
package com.calisquest.api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_REQUEST(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST",
            "Yêu cầu không hợp lệ"),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR",
            "Dữ liệu đầu vào không hợp lệ"),

    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "USER_NOT_FOUND",
            "Không tìm thấy tài khoản"),

    USER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "USER_ALREADY_EXISTS",
            "Tài khoản đã tồn tại"),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_SERVER_ERROR",
            "Đã xảy ra lỗi hệ thống");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(
            HttpStatus status,
            String code,
            String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
