package com.budget.planning.controller;

import com.budget.planning.dto.request.UserRegistrationRequest;
import com.budget.planning.service.BudgetPlanningService;
import com.budget.planning.service.UserDetailsServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BudgetPlanningController {
    private final BudgetPlanningService budgetPlanningService;
    private final UserDetailsServiceImp userDetailsService;

    @Operation(summary = "Register new user")
    @ApiResponse(responseCode = "200", description = "User registered", content = @Content)
    @ApiResponse(responseCode = "400", description = "Wrong role or user already registered", content = @Content)

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userDetailsService.register(userRegistrationRequest);
    }

    // http://localhost:8080/swagger-ui/index.html to access swagger
}
