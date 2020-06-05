-- ALTER TABLE user MODIFY COLUMN userId INT auto_increment;
-- insert into address(addressId, address, address2, cityId, postalCode, phone) values(default, "123 Main Street", "Apt 1", 3, "11111", "555-5555");

-- select * from city;
-- select * from address;

-- alter table user modify column createDate datetime default '0000-00-00 00:00:00';
-- alter table user modify column createdBy varchar(40) default 'test';
-- alter table user modify column lastUpdate datetime default '0000-00-00 00:00:00';
-- alter table user modify column lastUpdateBy varchar(40) default 'test';
-- commit;

-- select * from customer;
-- drop procedure new_customer;
-- DELIMITER //

-- CREATE PROCEDURE new_customer
--     (
--        IN c_name  varchar(45),
--        IN c_address_1 varchar(50),
--        IN c_address_2 varchar(50),
--        IN c_city varchar(50),
--        IN c_postal_code varchar(10),
--        IN c_phone varchar(20),
--        IN c_active tinyint(1)
--      )
-- BEGIN
--          -- address, city table
-- 		 INSERT INTO address(addressId, address, address2, cityId, postalCode, phone)
--          VALUES (null, c_address_1, c_address_2, 
--          (SELECT cityId from city where city = c_city), c_postal_code, c_phone);
--          
--          -- customer table
--          IF EXISTS (SELECT addressId from address where address = c_address_1) THEN
-- 			INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)
-- 			VALUES (null, c_name, (SELECT addressId from address where address = c_address_1), c_active, default, default, default, default);
-- 		ELSE
-- 			INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)
-- 			VALUES (null, c_name, (SELECT max(addressId)+1), c_active, default, default, default, default);
-- 		END IF;
--      END // 

