package com.marcincho.companies_sm.service;

import com.marcincho.companies_sm.dto.CompanyDto;
import com.marcincho.companies_sm.dto.NewCompanyDto;
import com.marcincho.companies_sm.entity.Company;
import com.marcincho.companies_sm.exception.CompanyExistException;
import com.marcincho.companies_sm.exception.ResourceNotFoundException;
import com.marcincho.companies_sm.mapper.CompanyMapper;
import com.marcincho.companies_sm.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CompanyServiceImpl implements ICompanyService{

    private CompanyRepository companyRepository;


    @Override
    public void createCompany(NewCompanyDto newCompanyDto) {
        Optional<Company> companyExists = companyRepository.findByCompanyName(newCompanyDto.companyName());
        if(companyExists.isPresent()) {
            throw new CompanyExistException("Company already registered with given name");
        }
        Company company = CompanyMapper.mapToNewCompany(newCompanyDto, new Company());
        companyRepository.save(company);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Company> fetchCompanyById(Long id) {
        return companyRepository.findById(id);
    }


    @Override
    public boolean updateCompany(CompanyDto companyDto, Long id) {
        boolean isUpdated = false;
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id: ", id.toString()));
        CompanyMapper.mapToCompany(companyDto, company);
        companyRepository.save(company);
        isUpdated = true;

        return  isUpdated;
    }

    @Override
    public boolean deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company" , "Id", id.toString()));
        companyRepository.deleteById(id);
        return true;
    }

    /**
     * @param companyType
     * @return
     */
    @Override
    public Optional<List<Company>> fetchCompaniesByCompanyType(String companyType) {
        return companyRepository.findByCompanyType(companyType);
    }

    /**
     * @return
     */
    @Override
    public List<Company> fetchAllCompanies() {
        return companyRepository.findAll();
    }


}
