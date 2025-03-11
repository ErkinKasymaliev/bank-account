CREATE TABLE IF NOT EXISTS bank_user (
    id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    registration_date timestamp
);
CREATE INDEX ON bank_user (email);

CREATE TABLE IF NOT EXISTS account (
    id serial PRIMARY KEY,
    account_number varchar(100) NOT NULL,
    balance numeric,
    bank_user_id serial references bank_user(id)
);
