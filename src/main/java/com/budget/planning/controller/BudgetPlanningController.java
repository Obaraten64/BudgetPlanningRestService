package com.budget.planning.controller;

import com.budget.planning.configuration.security.UserAdapter;
import com.budget.planning.dto.request.AccountRegistrationRequest;
import com.budget.planning.dto.request.UserRegistrationRequest;
import com.budget.planning.dto.response.AccountRegistrationDTO;
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
                schema = @Schema(implementation = AccountRegistrationDTO.class),
                examples = @ExampleObject(value = "{\"account_id\":2,\"balance\":10000}")))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)

    @PostMapping("/account/register")
    public AccountRegistrationDTO register(@Valid @RequestBody AccountRegistrationRequest accountRequest,
                                           @AuthenticationPrincipal UserAdapter user) {
        return budgetPlanningService.registerAccount(accountRequest, user.getUser());
    }

    // http://localhost:8080/swagger-ui/index.html to access swagger
}
