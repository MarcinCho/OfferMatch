package com.marcincho.companies_sm.controller;


import com.marcincho.companies_sm.dto.CompanyDto;
import com.marcincho.companies_sm.dto.NewCompanyDto;
import com.marcincho.companies_sm.dto.ResponseDto;
import com.marcincho.companies_sm.entity.Company;
import com.marcincho.companies_sm.service.ICompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path="company/", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class CompanyController {


    ICompanyService iCompanyService;

    @PostMapping
    public ResponseEntity<ResponseDto> addCompany(@Validated @RequestBody NewCompanyDto newCompanyDto) {
        iCompanyService.createCompany(newCompanyDto);
        return ResponseEntity.status(201).body(new ResponseDto("201", "Company created"));
    }

    @GetMapping
    public Optional<List<Company>> getAllCompanies() {
        return iCompanyService.fetchAllCompanies();
    }
//
    @GetMapping("id/{id}")
    public Optional<Company> getCompany(@PathVariable Long id) {
        return iCompanyService.fetchCompanyById(id);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<ResponseDto> updateCompany(@Valid @RequestBody CompanyDto companyDto, @PathVariable Long id) {
        boolean = iCompanyService.updateCompany(companyDto);
    }


}



