DROP PROCEDURE IF EXISTS addRentalToInvoice;
DROP PROCEDURE IF EXISTS addConcessionToInvoice;
DROP PROCEDURE IF EXISTS addRepairToInvoice;
DROP PROCEDURE IF EXISTS addTowingToInvoice;
DROP PROCEDURE IF EXISTS addInvoice;
DROP PROCEDURE IF EXISTS addRental;
DROP PROCEDURE IF EXISTS addTowing;
DROP PROCEDURE IF EXISTS addRepair;
DROP PROCEDURE IF EXISTS addConcession;
DROP PROCEDURE IF EXISTS addCustomer;
DROP PROCEDURE IF EXISTS addPerson;
DROP FUNCTION IF EXISTS HasTowingRepairRental;
DROP TABLE IF EXISTS InvoiceProductsData;
drop table if exists Invoices;
drop table if exists Products;
drop table if exists Customer;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
DROP TABLE IF EXISTS State;
DROP TABLE IF EXISTS Country;

CREATE TABLE IF NOT EXISTS Country(
    countryId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS State(
    stateId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    countryId int NOT NULL,
    name varchar(50),
    FOREIGN KEY (countryId) REFERENCES Country(countryId)
);

create table if not exists Address(
	addressId int not null primary key auto_increment,
	street varchar(200) not null,
    city varchar(200) not null,
    zip varchar(20),
    stateId int not null,
    FOREIGN KEY (stateId) REFERENCES State(stateId)
);

create table if not exists Person(
	personId int not null primary key auto_increment,
	personCode varchar(20) not null,
    lastName varchar(200) not null,
    firstName varchar(50) not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId)
);

create table if not exists Email(
	emailId int not null primary key auto_increment,
    emailAddress varchar(200) not null, #I know there's a super complicated Regex for this but It's so long
	personId int not null,
    foreign key (personId) references Person(personId)
);

create table if not exists Customer(
	customerId int not null primary key auto_increment,
	customerCode varchar(20) not null,
    name varchar(200) not null,
    addressId int not null,
    personId int not null,
    foreign key (addressId) references Address(addressId),
    foreign key (personId) references Person(personId),
	type char not null CHECK(type IN ('P','B','C'))
);

create table if not exists Invoices(
invoiceId int not null primary key auto_increment,
invoiceCode varchar(20) not null UNIQUE,
personId int not null,
customerId int not null,
foreign key(personId) references Person(personId),
foreign key(customerId) references Customer(customerId)
);

create table if not exists Products(
    productId int not null primary key auto_increment,
    productCode varchar(100) not null UNIQUE,
    type char not null CHECK(type IN ('R','T','C','F')),
    label varchar(200) not null,
    unitCost double CHECK((type = 'C') XOR (unitCost IS NULL)),
    dailyCost double CHECK((type = 'R') XOR (dailyCost IS NULL)),
    deposit double CHECK((type = 'R') XOR (deposit IS NULL)),
    cleaningFee double CHECK((type = 'R') XOR (cleaningFee IS NULL)),
    partsCost double CHECK((type = 'F') XOR (partsCost IS NULL)),
    hourlyLaborCost double CHECK((type = 'F') XOR (hourlyLaborCost IS NULL)),
    costPerMile double CHECK((type = 'T') XOR (costPerMile IS NULL))
);

CREATE TABLE IF NOT EXISTS InvoiceProductsData(
  invoiceProductsDataId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  productId INT NOT NULL,
  invoiceId INT NOT NULL,
  daysRented double,
  hoursWorked double,
  quantity int,
  associatedRepairId int,
  milesTowed int,
  FOREIGN KEY (productId) REFERENCES Products(productId),
  FOREIGN KEY (associatedRepairId) REFERENCES InvoiceProductsData(invoiceProductsDataId),
  FOREIGN KEY (invoiceId) REFERENCES Invoices(invoiceId)
);

INSERT INTO Country(name)
VALUES('United States'),('Mexico'),('Cuba');

SET @Usa = (SELECT countryId FROM Country WHERE name = 'United States');
SET @Mx = (SELECT countryId FROM Country WHERE name = 'Mexico');
SET @Cuba = (SELECT countryId FROM Country WHERE name = 'Cuba');

INSERT INTO State(countryId, name)
VALUES(@Usa,'IL'),(@Usa,'NY'),(@Mx,'BCS'),(@Usa,'NE'),(@Usa,'CA'),(@Usa,'NM'),(@Cuba,'NL');

insert into Address(street,city,stateId,zip)values('1025 S Street st.','Chicago',(SELECT stateId FROM State WHERE name = 'IL'),'60613');
insert into Address(street,city,stateId,zip)values('1 Wall Street','New York',(SELECT stateId FROM State WHERE name = 'NY'),'10005-0012');
insert into Address(street,city,stateId)values('23450 Cabo San Lucas', 'Los Cabos',(SELECT stateId FROM State WHERE name = 'BCS'));
insert into Address(street,city,stateId,zip)values('6700 Vine St','Lincoln',(SELECT stateId FROM State WHERE name = 'NE'),'68505');
insert into Address(street,city,stateId,zip)values('123 N 1st Street','Compton',(SELECT stateId FROM State WHERE name = 'CA'),'90210');
insert into Address(street,city,stateId,zip)values('100 W Fake Street','Albaquerque',(SELECT stateId FROM State WHERE name = 'NM'),'80706');
insert into Address(street,city,stateId)values('123 Madison Avenue', 'Bodega', (SELECT stateId FROM State WHERE name = 'NL'));

insert into Person(personCode,lastName,firstName,addressId)values('abc2','Medrano','Wiktoria',1);
insert into Person(personCode,lastName,firstName,addressId)values('biz45','Hart', 'Fenton',2);
insert into Person(personCode,lastName,firstName,addressId)values('zyx321','Dolan', 'Ayaana',3);
insert into Person(personCode,lastName,firstName,addressId)values('j3bdl5','Salazar', 'Anthony',4);
insert into Person(personCode,lastName,firstName,addressId)values('lejnr4','Spooner', 'Kenan',5);

insert into Email(emailAddress,personId)values('therealwiki@gmail.com',1);
insert into Email(emailAddress,personId)values('wiktoria.m@yahoo.com',1);
insert into Email(emailAddress,personId)values('fentony@windstream.net',2);
insert into Email(emailAddress,personId)values('slitherysalazar@hotmail.com',4);
insert into Email(emailAddress,personId)values('kspoons@emailservice.domain',5);
insert into Email(emailAddress,personId)values('thespoonman@soundgarden.com',5);

insert into Customer(customerCode,name,addressId,personId,type)values('lsvntk','State of Nebraska',6,(select p.personId from Person p where p.personCode = 'abc2'),'B');
insert into Customer(customerCode,name,addressId,personId,type)values('dvkh','Wacky Wally\'s Used Cars',6,(select p.personId from Person p where p.personCode = 'zyx321'),'P');

insert into Invoices(invoiceCode,personId,customerId)values('INV001',(select p.personId from Person p where personCode = 'abc2'),(select c.customerId from Customer c where customerCode = 'lsvntk'));
insert into Invoices(invoiceCode,personId,customerId)values('INV002',(select p.personId from Person p where personCode = 'lejnr4'),(select c.customerId from Customer c where customerCode = 'dvkh'));
insert into Invoices(invoiceCode,personId,customerId)values('INV003',(select p.personId from Person p where personCode = 'lejnr4'),(select c.customerId from Customer c where customerCode = 'dvkh'));


insert into Products(productCode,type,label,dailyCost,deposit,cleaningFee)values('qwertyuiop','R','2007 Ford Crown Victoria',50.75,200,25.39);
insert into Products(productCode,type,label,partsCost,hourlyLaborCost)values('b4z1ng4','F','Bumper Replacement',100,75);
insert into Products(productCode,type,label,unitCost)values('k04l4','C','12-oz Coffee',4.75);
insert into Products(productCode,type,label,costPerMile)values('sievub','T','Tow < 10 Miles',6.00);

INSERT INTO InvoiceProductsData(productId, invoiceId, daysRented)
VALUES(
       (
           SELECT productId
           FROM Products
           WHERE productCode = 'qwertyuiop'
           ),
       (
           SELECT invoiceId
           FROM Invoices
           WHERE invoiceCode = 'INV001'
           ),
       5
      );

INSERT INTO InvoiceProductsData(productId, invoiceId, hoursWorked)
VALUES(
       (
           SELECT productId
           FROM Products
           WHERE productCode = 'b4z1ng4'
           ),
       (
           SELECT invoiceId
           FROM Invoices
           WHERE invoiceCode = 'INV002'
           ),
       4
      );

SET @associatedRepairId = (
    SELECT invoiceProductsDataId
    FROM InvoiceProductsData
    JOIN Invoices ON InvoiceProductsData.invoiceId = Invoices.invoiceId
    JOIN Products P ON InvoiceProductsData.productId = P.productId
    WHERE invoiceCode = 'INV002'
    AND productCode = 'b4z1ng4'
    );

INSERT INTO InvoiceProductsData(productId,invoiceId,quantity,associatedRepairId)
VALUES(
       (
           SELECT productId
           FROM Products
           WHERE productCode = 'k04l4'
           ),
       (
           SELECT invoiceId
           FROM Invoices
           WHERE invoiceCode = 'INV002'
           ),
       6,
       @associatedRepairId
      );

INSERT INTO InvoiceProductsData(productId,invoiceId,milesTowed)
VALUES(
       (
           SELECT productId
           FROM Products
           WHERE productCode = 'sievub'
           ),
       (
           SELECT invoiceId
           FROM Invoices
           WHERE invoiceCode = 'INV001'
           ),
       1234
      );

CREATE FUNCTION HasTowingRepairRental(invoiceId int)
RETURNS BOOLEAN
BEGIN
    RETURN EXISTS(
            SELECT 1
            FROM InvoiceProductsData IPD
                     JOIN Products P on IPD.productId = P.productId
            WHERE IPD.invoiceId = invoiceId
              AND type = 'T'
        ) AND EXISTS(
            SELECT 1
            FROM InvoiceProductsData IPD
                     JOIN Products P on IPD.productId = P.productId
            WHERE IPD.invoiceId = invoiceId
              AND type = 'R'
        ) AND EXISTS(
            SELECT 1
            FROM InvoiceProductsData IPD
                     JOIN Products P on IPD.productId = P.productId
            WHERE IPD.invoiceId = invoiceId
              AND type = 'F'
        );
end;

INSERT INTO InvoiceProductsData(productId, invoiceId, hoursWorked)
VALUES(
          (
              SELECT productId
              FROM Products
              WHERE productCode = 'b4z1ng4'
          ),
          (
              SELECT invoiceId
              FROM Invoices
              WHERE invoiceCode = 'INV003'
          ),
          4
      );

SET @associatedRepairId = (
    SELECT invoiceProductsDataId
    FROM InvoiceProductsData
             JOIN Invoices ON InvoiceProductsData.invoiceId = Invoices.invoiceId
             JOIN Products P ON InvoiceProductsData.productId = P.productId
    WHERE invoiceCode = 'INV003'
      AND productCode = 'b4z1ng4'
);

INSERT INTO InvoiceProductsData(productId,invoiceId,quantity,associatedRepairId)
VALUES(
          (
              SELECT productId
              FROM Products
              WHERE productCode = 'k04l4'
          ),
          (
              SELECT invoiceId
              FROM Invoices
              WHERE invoiceCode = 'INV003'
          ),
          6,
          @associatedRepairId
      );

INSERT INTO InvoiceProductsData(productId,invoiceId,quantity,associatedRepairId)
VALUES(
          (
              SELECT productId
              FROM Products
              WHERE productCode = 'k04l4'
          ),
          (
              SELECT invoiceId
              FROM Invoices
              WHERE invoiceCode = 'INV003'
          ),
          6,
          @associatedRepairId
      );

#String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country
CREATE PROCEDURE addPerson(personCode VARCHAR(20), firstName VARCHAR(50), lastName VARCHAR(50), streetName VARCHAR(200), cityName VARCHAR(200), stateName VARCHAR(50), zipCode VARCHAR(20), countryName VARCHAR(50))
BEGIN
    SET @countryId = (SELECT Country.countryId FROM Country WHERE Country.name = countryName LIMIT 1);
    IF(@countryId IS NULL) THEN
        INSERT INTO Country(name) VALUES(countryName);
        SET @countryId = LAST_INSERT_ID();
    END IF;

    SET @stateId = (SELECT stateId FROM State WHERE name = stateName AND countryId = @countryId LIMIT 1);
    IF(@stateId IS NULL) THEN
        INSERT INTO State(countryId, name) VALUES(@countryId, stateName);
        SET @stateId = LAST_INSERT_ID();
    END IF;

    #Addresses get changed often enough that making a new address for each person simplifies the design
    #It makes changing a customer/owner's address a simple update statement
    #Compared to using the same addressId if two customers share an address
    INSERT INTO Address(street, city, zip, stateId) VALUES(streetName,cityName,zipCode,@stateId);
    SET @addressId = LAST_INSERT_ID();

    INSERT INTO Person(personCode, lastName, firstName, addressId) VALUES(personCode,lastName,firstName,@addressId);
END;

CREATE PROCEDURE addCustomer(customerCode VARCHAR(20), customerType CHAR, primaryContactPersonCode VARCHAR(20), name VARCHAR(200), streetName VARCHAR(200), cityName VARCHAR(200), stateName VARCHAR(50), zipCode VARCHAR(20), countryName VARCHAR(50))
BEGIN
    SET @countryId = (SELECT Country.countryId FROM Country WHERE Country.name = countryName LIMIT 1);
    IF(@countryId IS NULL) THEN
        INSERT INTO Country(name) VALUES(countryName);
        SET @countryId = LAST_INSERT_ID();
    END IF;

    SET @stateId = (SELECT stateId FROM State WHERE name = stateName AND countryId = @countryId LIMIT 1);
    IF(@stateId IS NULL) THEN
        INSERT INTO State(countryId, name) VALUES(@countryId, stateName);
        SET @stateId = LAST_INSERT_ID();
    END IF;

    #Addresses get changed often enough that making a new address for each customer simplifies the design
    #It makes changing a customer/owner's address a simple update statement
    #Compared to using the same addressId if two customers share an address
    INSERT INTO Address(street, city, zip, stateId) VALUES(streetName,cityName,zipCode,@stateId);
    SET @addressId = LAST_INSERT_ID();

    SET @personId = (SELECT personId FROM Person WHERE personCode = primaryContactPersonCode);

    INSERT INTO Customer(customerCode, name, addressId, personId, type) VALUES(customerCode, name, @addressId, @personId, customerType);
END;

CREATE PROCEDURE addConcession(productCode VARCHAR(100), productLabel VARCHAR(200), unitCost DOUBLE)
BEGIN
    INSERT INTO Products(productCode, type, label, unitCost) VALUES(productCode, 'C', productLabel, unitCost);
END;

CREATE PROCEDURE addRepair(productCode VARCHAR(100), productLabel VARCHAR(200), partsCost DOUBLE, laborRate DOUBLE)
BEGIN
   INSERT INTO Products(productCode, type, label, partsCost, hourlyLaborCost) VALUES(productCode, 'F', productLabel, partsCost, laborRate);
END;

CREATE PROCEDURE addTowing(productCode VARCHAR(100), productLabel VARCHAR(200), costPerMile DOUBLE)
BEGIN
   INSERT INTO Products(productCode, type, label, costPerMile) VALUES(productCode, 'T', productLabel, costPerMile);
END;

CREATE PROCEDURE addRental(productCode VARCHAR(100), productLabel VARCHAR(200), dailyCost DOUBLE, deposit DOUBLE, cleaningFee DOUBLE)
BEGIN
   INSERT INTO Products(productCode, type, label, dailyCost, deposit, cleaningFee) VALUES(productCode, 'R', productLabel, dailyCost, deposit, cleaningFee);
END;

CREATE PROCEDURE addInvoice(invoiceCode VARCHAR(20), ownerCode VARCHAR(20), customerCode VARCHAR(20))
BEGIN
    SET @personId = (SELECT personId FROM Person WHERE personCode = ownerCode LIMIT 1);
    SET @customerId = (SELECT customerId FROM Customer c WHERE c.customerCode = customerCode LIMIT 1);

    INSERT INTO Invoices(invoiceCode, personId, customerId) VALUES(invoiceCode, @personId, @customerId);
END;

CREATE PROCEDURE addTowingToInvoice(invoiceCode VARCHAR(20), productCode VARCHAR(100), milesTowed DOUBLE)
BEGIN
    SET @invoiceId = (SELECT invoiceId FROM Invoices I WHERE I.invoiceCode = invoiceCode LIMIT 1);
    SET @productId = (SELECT productId FROM Products P WHERE P.productCode = productCode LIMIT 1);
    INSERT INTO InvoiceProductsData(productId, invoiceId, milesTowed) VALUES(@productId, @invoiceId, milesTowed);
END;

CREATE PROCEDURE addRepairToInvoice(invoiceCode VARCHAR(20), productCode VARCHAR(100), hoursWorked DOUBLE)
BEGIN
    SET @invoiceId = (SELECT invoiceId FROM Invoices I WHERE I.invoiceCode = invoiceCode LIMIT 1);
    SET @productId = (SELECT productId FROM Products P WHERE P.productCode = productCode LIMIT 1);
    INSERT INTO InvoiceProductsData(productId, invoiceId, hoursWorked) VALUES(@productId, @invoiceId, hoursWorked);
END;

CREATE PROCEDURE addConcessionToInvoice(invoiceCode VARCHAR(20), productCode VARCHAR(100), quantity INT, repairCode VARCHAR(100))
BEGIN
    SET @invoiceId = (SELECT invoiceId FROM Invoices I WHERE I.invoiceCode = invoiceCode LIMIT 1);
    SET @productId = (SELECT productId FROM Products P WHERE P.productCode = productCode LIMIT 1);
    SET @repairId = (SELECT invoiceProductsDataId FROM InvoiceProductsData IDP JOIN Products P2 on IDP.productId = P2.productId WHERE P2.productCode = repairCode LIMIT 1);

    INSERT INTO InvoiceProductsData(productId, invoiceId, quantity, associatedRepairId) VALUES(@productId, @invoiceId, quantity, @repairId);
END;

CREATE PROCEDURE addRentalToInvoice(invoiceCode VARCHAR(20), productCode VARCHAR(100), daysRented DOUBLE)
BEGIN
    SET @invoiceId = (SELECT invoiceId FROM Invoices I WHERE I.invoiceCode = invoiceCode LIMIT 1);
    SET @productId = (SELECT productId FROM Products P WHERE P.productCode = productCode LIMIT 1);

    INSERT INTO InvoiceProductsData(productId, invoiceId, daysRented) VALUES(@productId, @invoiceId, daysRented);
END;



SELECT IPD.productId AS productId,
       IPD.daysRented AS daysRented,
       IPD.hoursWorked AS hoursWorked,
       IPD.quantity AS quantity,
       IPD.milesTowed AS milesTowed,
       P.productId AS associatedRepairId
FROM InvoiceProductsData IPD
    LEFT JOIN InvoiceProductsData I on IPD.associatedRepairId = I.invoiceProductsDataId
    LEFT JOIN Products P ON I.productId = P.productId
AND IPD.invoiceId = 54
AND I.invoiceId = 54