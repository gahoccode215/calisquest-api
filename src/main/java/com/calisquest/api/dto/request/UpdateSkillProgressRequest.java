
package com.calisquest.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSkillProgressRequest {

    @NotNull(message = "Tiến độ không được để trống")
    @Min(value = 0, message = "Tiến độ tối thiểu là 0")
    @Max(value = 100, message = "Tiến độ tối đa là 100")
    private Integer progress;
}
