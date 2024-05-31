package com.budget.planning.dto.request;

import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
@Getter
public class AccountRegistrationRequest {
    @Min(value = 1, message = "Write down initial balance of the account!")
    private Integer balance;
}
