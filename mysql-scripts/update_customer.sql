DROP procedure update_customer;
delimiter //
CREATE PROCEDURE update_customer(
		IN c_customer_id int(11),
		IN c_name  varchar(45),
       IN c_address_1 varchar(50),
       IN c_address_2 varchar(50),
       IN c_city varchar(50),
       IN c_postal_code varchar(10),
       IN c_phone varchar(20),
       IN c_active tinyint(1)
)
BEGIN
	IF EXISTS(select addressId from address where address = c_address_1 AND address2 = c_address_2 AND cityId = (select cityId from city where city = c_city) AND postalCode = c_postal_code AND phone = c_phone) THEN
		UPDATE address 
		SET
			address = c_address_1, 
			address2 = c_address_2, 
			cityId = (select cityId from city where city = c_city), 
			phone = c_phone, 
			createDate = default,
			createdBy = default,
			lastUpdate = default,
			lastUpdateBy = default
		WHERE addressId = (select addressId from address where address = c_address_1 AND address2 = c_address_2 AND cityId = (select cityId from city where city = c_city) AND postalCode = c_postal_code AND phone = c_phone);
	ELSE
		INSERT INTO address(addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)
		VALUES(
        null, 
        c_address_1, 
        c_address_2, 
        (select cityId from city where city = c_city),
        c_postal_code, 
        c_phone,
        default,
        default,
        default,
        default
        );
	UPDATE customer
	SET  
		customerName = c_name,
		addressId = (select addressId from address where address = c_address_1 AND address2 = c_address_2 AND cityId = (select cityId from city where city = c_city) AND postalCode = c_postal_code AND phone = c_phone),
		active = c_active,
		createDate = default,
		createdBy = default,
		lastUpdate = default,
		lastUpdateBy = default
	WHERE customerId = c_customer_id;
    END IF;
END //
DELIMITER ;
select * from customer;
select * from address where addressId = 2;
CALL update_customer(1, "John Done", "123 Elm", "", "Quebec", "11112", "555-1213", 1);