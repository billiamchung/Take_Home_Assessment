CREATE TABLE product (
    name varchar(255),
    category varchar(255),
    date_added DATETIME,
    added_by varchar(255)
);

CREATE TABLE product_price(
    name varchar(255),
    price DECIMAL(10, 2),
    current_discount TINYINT DEFAULT 0,
    time_updated TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by varchar(255)
);

CREATE TABLE product_price_change_log(
    update_performed varchar(255),
    modified_by varchar(255),
    update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    name varchar(255),
    price DECIMAL(10, 2),
    current_discount TINYINT DEFAULT 0,
    time_updated TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by varchar(255)
);

SELECT p.name, p.category, pp.price, pp.updated_by, pp.time_updated
FROM product p
INNER JOIN product_price pp
ON p.name = pp.name;