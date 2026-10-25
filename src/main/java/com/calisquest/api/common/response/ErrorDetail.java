
package com.calisquest.api.common.response;

import java.util.Map;

public record ErrorDetail(
        String code,
        String message,
        Map<String, ?> details) {
}
