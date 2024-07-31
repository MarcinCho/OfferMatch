package com.marcincho.companies_sm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long companyId;

    private String companyName;

    private String companyType;

    private String description;

    private String phoneNumber;

    private String email;

    private String address;

    private String contactPerson;



}
