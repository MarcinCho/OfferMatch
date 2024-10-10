package com.marcincho.companies_sm.service;

import com.marcincho.companies_sm.dto.CompanyDto;
import com.marcincho.companies_sm.dto.NewCompanyDto;
import com.marcincho.companies_sm.entity.Company;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {

    void createCompany(NewCompanyDto newCompanyDto);

    Optional<Company> fetchCompanyById(Long id);

    boolean updateCompany(CompanyDto companyDto, Long id);

    boolean deleteCompany(Long id);

    Optional<List<Company>> fetchCompaniesByCompanyType(String companyType);

    List<Company> fetchAllCompanies();

    Optional<List<Company>> getCompaniesWithLimit(int limit);

}
