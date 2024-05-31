package com.budget.planning.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountUpdateRequest {
    @Min(value = 1, message = "Write down how much money you want to spend!")
    private Integer amount;
    @Schema(example = "Buy candy")
    @NotBlank(message = "Write down purpose of the operation!")
    private String purpose;
}
