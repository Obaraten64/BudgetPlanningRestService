package com.budget.planning.controller;

import com.budget.planning.configuration.security.UserAdapter;
import com.budget.planning.dto.request.AccountRegistrationRequest;
import com.budget.planning.dto.request.AccountUpdateRequest;
import com.budget.planning.dto.request.LimitUpdateRequest;
import com.budget.planning.dto.request.UserRegistrationRequest;
import com.budget.planning.dto.response.AccountUpdateDTO;
import com.budget.planning.dto.response.UserWithLimitDTO;
import com.budget.planning.service.BudgetPlanningService;
import com.budget.planning.service.UserDetailsServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BudgetPlanningController {
    private final BudgetPlanningService budgetPlanningService;
    private final UserDetailsServiceImp userDetailsService;

    @Operation(summary = "Register new user")
    @ApiResponse(responseCode = "200", description = "User registered", content = @Content)
    @ApiResponse(responseCode = "400", description = "Wrong role or user already registered", content = @Content)

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userDetailsService.register(userRegistrationRequest);
    }

    @Operation(summary = "Register new bank account, authorization required",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Created bank account",
            content = @Content(
                schema = @Schema(implementation = AccountUpdateDTO.class),
                examples = @ExampleObject(value = "{\"account_id\":2,\"balance\":10000}")))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)

    @PostMapping("/account/register")
    public AccountUpdateDTO register(@Valid @RequestBody AccountRegistrationRequest accountRequest,
                                     @AuthenticationPrincipal UserAdapter user) {
        return budgetPlanningService.registerAccount(accountRequest, user.getUser());
    }

    @Operation(summary = "Replenish a bank account, authorization required",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Updated bank account",
            content = @Content(
                    schema = @Schema(implementation = AccountUpdateDTO.class),
                    examples = @ExampleObject(value = "{\"account_id\":2,\"balance\":10000}")))
    @ApiResponse(responseCode = "400", description = "You do not have a bank account", content = @Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)

    @PostMapping("/account/replenish")
    public AccountUpdateDTO replenishAccount(@Valid @RequestBody AccountUpdateRequest accountRequest,
                                             @AuthenticationPrincipal UserAdapter user) {
        return budgetPlanningService.replenishAccount(accountRequest, user.getUser());
    }

    @Operation(summary = "Withdraw money from bank account, authorization required",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Updated bank account",
            content = @Content(
                    schema = @Schema(implementation = AccountUpdateDTO.class),
                    examples = @ExampleObject(value = "{\"account_id\":2,\"balance\":10000}")))
    @ApiResponse(responseCode = "400", description = "You do not have a bank account, you can't withdraw that much money or" +
            "balance will become zero after operation", content = @Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)

    @PostMapping("/account/withdraw")
    public AccountUpdateDTO withdrawAccount(@Valid @RequestBody AccountUpdateRequest accountRequest,
                                            @AuthenticationPrincipal UserAdapter user) {
        return budgetPlanningService.withdrawAccount(accountRequest, user.getUser());
    }

    @Operation(summary = "Set new usage limit for a child, Parent role required",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Updated user",
            content = @Content(
                    schema = @Schema(implementation = UserWithLimitDTO.class),
                    examples = @ExampleObject(value = "{\"name\":\"vova\",\"email\":\"vova@gmail.com\",\"usage_limit\":10}")))
    @ApiResponse(responseCode = "400", description = "You do not have a bank account, you can't withdraw that much money or" +
            "balance will become zero after operation", content = @Content)
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    @ApiResponse(responseCode = "403", description = "Wrong role", content = @Content)

    @PostMapping("/limit/update")
    public UserWithLimitDTO updateLimit(@Valid @RequestBody LimitUpdateRequest limitRequest,
                                        @AuthenticationPrincipal UserAdapter user) {
        return budgetPlanningService.updateLimit(limitRequest, user.getUser());
    }

    // http://localhost:8080/swagger-ui/index.html to access swagger
}
