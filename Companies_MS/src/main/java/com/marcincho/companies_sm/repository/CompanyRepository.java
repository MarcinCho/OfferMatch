package com.marcincho.companies_sm.repository;

import com.marcincho.companies_sm.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String name);

    Optional<List<Company>> findByCompanyType(String type);

}
