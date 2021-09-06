DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  price DOUBLE NOT NULL
);

INSERT INTO products (name, description, price) VALUES
  ('cadeira', 'cadeira de balanço', 56.99),
  ('mochila', 'mochila para notebook', 199.00),
  ('tv 32', 'Smart TV', 2950.99);