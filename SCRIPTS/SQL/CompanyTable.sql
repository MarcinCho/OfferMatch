DROP TABLE IF EXISTS companies;

CREATE TABLE companies(company_id BIGSERIAL PRIMARY KEY,
                       company_name TEXT,
                       company_type TEXT,
                       description TEXT,
                       phone_number TEXT,
                       email TEXT,
                       address TEXT,
                       contact_person TEXT,
                    created_at time,
                        created_by TEXT,
updated_at time,
updated_by TEXT);