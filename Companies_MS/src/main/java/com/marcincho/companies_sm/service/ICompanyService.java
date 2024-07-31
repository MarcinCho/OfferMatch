package com.marcincho.companies_sm.service;

import com.marcincho.companies_sm.dto.CompanyDto;
import com.marcincho.companies_sm.entity.Company;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {

    void createCompany(CompanyDto companyDto);

    Optional<Company> fetchCompanyById(Long id);

    boolean updateCompany(CompanyDto companyDto);

    boolean deleteCompany(Long id);

    Optional<List<Company>> fetchCompaniesByCompanyType(String companyType);
}
