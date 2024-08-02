DROP TABLE IF EXISTS companies;

CREATE TABLE companies(company_id BIGINT PRIMARY KEY,
company_name TEXT, description TEXT, 
phone_number TEXT, email TEXT, 
address TEXT, contact_person TEXT);