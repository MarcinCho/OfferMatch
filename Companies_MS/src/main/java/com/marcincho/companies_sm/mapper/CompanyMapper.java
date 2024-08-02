package com.marcincho.companies_sm.mapper;

import com.marcincho.companies_sm.dto.CompanyDto;
import com.marcincho.companies_sm.dto.NewCompanyDto;
import com.marcincho.companies_sm.entity.Company;

public class CompanyMapper {

    public static CompanyDto mapToCompanyDto(Company company, CompanyDto companyDto) {
        companyDto.setCompanyName(company.getCompanyName());
        companyDto.setCompanyType(company.getCompanyType());
        companyDto.setDescription(company.getDescription());
        companyDto.setPhoneNumber(company.getPhoneNumber());
        companyDto.setEmail(company.getEmail());
        companyDto.setAddress(company.getAddress());
        companyDto.setContactPerson(company.getContactPerson());
        return companyDto;
    }

    public static Company mapToCompany(CompanyDto companyDto, Company company) {
        company.setCompanyName(companyDto.getCompanyName());
        company.setCompanyType(companyDto.getCompanyType());
        company.setDescription(companyDto.getDescription());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setEmail(companyDto.getEmail());
        company.setAddress(companyDto.getAddress());
        company.setContactPerson(companyDto.getContactPerson());
        return company;
    }

    public static Company mapToNewCompany(NewCompanyDto newCompanyDto, Company company) {
        company.setCompanyName(newCompanyDto.companyName());
        company.setEmail(newCompanyDto.email());
        company.setContactPerson(newCompanyDto.contactPerson());
        return company;
    }


}
