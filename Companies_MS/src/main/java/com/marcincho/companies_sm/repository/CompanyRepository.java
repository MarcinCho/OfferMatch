package com.marcincho.companies_sm.repository;

import com.marcincho.companies_sm.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    Optional<Company> findByCompanyName(String name);

    Optional<List<Company>> findByCompanyType(String type);

    Optional<List<Company>> findAllLimitedTo(int limit);
}
