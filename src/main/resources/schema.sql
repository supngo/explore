DROP TABLE IF EXISTS "USER";

CREATE TABLE "USER" (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL
);