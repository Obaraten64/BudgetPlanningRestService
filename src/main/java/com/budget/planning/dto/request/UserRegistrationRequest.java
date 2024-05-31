package com.budget.planning.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationRequest {
    @Schema(example = "vova")
    @NotBlank(message = "Write down your name!")
    private String name;
    @Schema(example = "vova@gmail.com")
    @NotBlank(message = "Write down your email!")
    private String email;
    @Schema(example = "1234")
    @NotBlank(message = "Write down your password!")
    private String password;
    @Schema(example = "parent")
    @NotBlank(message = "Write down your role!")
    private String role;
    @Min(value = 1, message = "Write down your bank account id!")
    private Long account_id;
}
