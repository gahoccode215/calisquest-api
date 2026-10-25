
package com.calisquest.api.common.response;

import java.util.Map;

public record ApiResponseDTO<T>(
        boolean success,
        T data,
        ErrorDetail error) {

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, data, null);
    }

    public static ApiResponseDTO<Void> fail(
            String code,
            String message,
            Map<String, ?> details) {
        return new ApiResponseDTO<>(
                false,
                null,
                new ErrorDetail(code, message, details));
    }
}
