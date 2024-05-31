package com.budget.planning.dto.request;

import jakarta.validation.constraints.Min;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountRegistrationRequest {
    @Min(value = 1, message = "Write down initial balance of the account!")
    private Integer balance;
}
