package com.marcincho.companies_sm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "Company",
        description = "Schema to hold company details"
)
public class CompanyDto {
    @NotEmpty(message = "Company name cannot be empty")
    private String companyName;

    @NotEmpty(message = "You need to specify company type")
    private String companyType;

    private String description;

    private String phoneNumber;

    @NotEmpty(message = "Email address cannot be empty")
    private String email;

    private String address;

    @NotEmpty(message = "Please add contact person")
    private String contactPerson;



}
