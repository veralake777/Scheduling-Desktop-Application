# Scheduling Desktop Application

# To Run
- Clone the repo to your local machine
- Open it in your IDE
- Update the database information with your own
- Run the application


# Scenario
You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified.

# Personal Objectives
Gain the ability to communicate effectively about a full stack Java Program from start to finish. 

Learn Java best practices.

Implement Java design patterns (Singletons, Javabean, etc.).

Utilize the SDLC (Software Development Lifecycle).

Present a Graphical User Interface that has a modern look and feel.

Create an pleasant and intuitive user experience.

# Project Objectives

The organization outlined specific business requirements that must be included as part of the application. 
From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

## Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
![Login Page](/GUI/LoginPage.PNG)

## Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.

## Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.

## Provide the ability to view the calendar by month and by week.

## Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.

## Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
   
   •   scheduling an appointment outside business hours
   
   •   scheduling overlapping appointments
   
   •   entering nonexistent or invalid customer data
   
   •   entering an incorrect username and password
   

## Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.

## Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.

## Provide the ability to generate each  of the following reports:
   
   •   number of appointment types by month
   
   •   the schedule for each consultant
   
   •   one additional report of your choice
   
## Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.

#DATABASE MODIFICATIONS
## Modifications used for simplifying CRUD operations
- auto_increment id columns for customer, address, user, appointment tables
- add default value to createDate, createdBy, lastUpdate, lastUpdateBy for customer, address, user, appointment tables

## Stored Procedures
Stored procedures in this application are use to increase readability within the Java code portion of the project
NEW CUSTOMER STORED PROCEDURE
```aidl
-- procedure to handle inserting a new customer
    -- handles the address table update if the address does not already exist
    -- city does not need an if/else because the user is selecting from a combo box. if new cities need to be added
    -- in the future then the databse worldCity table must be updated or the city table must be updated/modified to 
    -- allow users to insert new cities 
CREATE PROCEDURE new_customer
    (
       IN c_name  varchar(45),
       IN c_address_1 varchar(50),
       IN c_address_2 varchar(50),
       IN c_city varchar(50),
       IN c_postal_code varchar(10),
       IN c_phone varchar(20),
       IN c_active tinyint(1)
     )
     BEGIN
		 
         -- address table
		 INSERT INTO address(addressId, address, address2, cityId, postalCode, phone)
         VALUES (null, c_address_1, c_address_2, 
         (SELECT cityId from city where city = c_city), c_postal_code, c_phone);
         
         -- customer table
         IF EXISTS (SELECT addressId from address where address = c_address_1) THEN
			INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)
			VALUES (null, c_name, (SELECT addressId from address where address = c_address_1), c_active, default, default, default, default);
		ELSE
			INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)
			VALUES (null, c_name, (SELECT max(addressId)+1), c_active, default, default, default, default);
		END IF;
     END // 

-- to use the procedure `CALL new_customer(...)`
```
