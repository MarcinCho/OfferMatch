CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    company_name TEXT NOT NULL,
    company_type TEXT NOT NULL,
    company_desc TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL,
    company_address TEXT NOT NULL,
    contact_person TEXT NOT NULL,
    created_at TIME,
    created_by TEXT,
    updated_at TIME,
    updated_by TEXT,
)