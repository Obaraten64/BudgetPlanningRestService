package com.budget.planning.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {
    @Schema(example = "vova@gmail.com")
    @NotBlank(message = "Write down username!")
    private String email;
    @Min(value = 1, message = "Write down bank account id!")
    private Long account_id;
}
