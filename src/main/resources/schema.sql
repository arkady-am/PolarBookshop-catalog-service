DROP TABLE IF EXISTS books;
CREATE TABLE book {
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    isbn    varchar(255) UNIQUE NOT NULL,
    title   varchar(255) NOT NULL,
    author  varchar(255) NOT NULL,
    price   float(8) NOT NULL,
    version integer NOT NULL,
}