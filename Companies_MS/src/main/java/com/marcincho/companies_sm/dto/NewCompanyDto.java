package com.marcincho.companies_sm.dto;

import jakarta.validation.constraints.NotEmpty;

public record NewCompanyDto(@NotEmpty(message = "Company name cannot be empty") String companyName,
                            @NotEmpty(message = "Email cannot be empty") String email,
                            @NotEmpty(message = "Please add contact person")
                            String contactPerson) {
}
