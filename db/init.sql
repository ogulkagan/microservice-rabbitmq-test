-- Creation of product table
CREATE TABLE IF NOT EXISTS account (
  id SERIAL NOT NULL,
  customer_id varchar(250) NOT NULL,
  PRIMARY KEY (id)
);

-- Creation of product table
CREATE TABLE IF NOT EXISTS account_detail (
  id SERIAL NOT NULL,
  account_id INT NOT NULL,
  currency varchar(250) NOT NULL,
  amount float NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT account_id
    FOREIGN KEY(account_id) 
  REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS moves (
    id SERIAL NOT NULL,
    account_id INT NOT NULL,
    amount float NOT NULL,
    currency varchar(250) NOT NULL,
    direction varchar(250) NOT NULL,
    description varchar(250) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT account_id
      FOREIGN KEY(account_id) 
	  REFERENCES account(id)
);
