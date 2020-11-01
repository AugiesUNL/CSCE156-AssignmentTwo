#1
select * from Person;

#2
select * from Email e
where e.personId = (select p.personId from Person p where p.personCode = 'abc2');

#3
insert into Email (emailAddress,personId)
values('something@not.com',(select p.personId from Person p where p.personCode = 'abc2'));

#4
update Email
set emailAddress = 'otherthing@knot.net' where emailId = 1;

#5
delete from Email
where personId = 2;

delete from Person
where personId = 2;

#6
insert into Person(personCode,lastName,firstName,addressId)
values('grt43','Gan', 'Fan',2);

#7
select P.*
from InvoiceProductData
JOIN Product P on InvoiceProductData.productId = P.productId
where invoiceId = 1;

#8
SELECT P.*
FROM Invoices i
    JOIN InvoiceProductData IPD on i.invoiceId = IPD.invoiceId
    JOIN Product P on i.invoiceId = IPD.invoiceId
WHERE personId = 1;

#9
SELECT I.*
FROM Invoices I
WHERE personId = 1;

#10
SET @rentalId = (SELECT productId FROM Product WHERE type = 'R' LIMIT 1);
SET @invoiceId = (SELECT invoiceId FROM Invoices I WHERE NOT EXISTS(SELECT 1 FROM InvoiceProductData WHERE invoiceId = I.invoiceId) LIMIT 1);

INSERT INTO InvoiceProductData(productId, invoiceId, daysRented)
VALUES(
       @productId,
       @invoiceId,
       4
      );

#11
SELECT SUM(unitCost)
FROM Product
WHERE type = 'C';

#12
SELECT C.*
FROM Customer C
JOIN Invoices I on C.customerId = I.customerId
GROUP BY C.customerId
HAVING COUNT(*) > 2;

#13
SELECT COUNT(*) AS Count
FROM Invoices I
WHERE 'R' IN(
    SELECT type
    FROM Product P
    JOIN InvoiceProductData IPD on P.productId = IPD.productId
    WHERE I.invoiceId = IPD.invoiceId
    );

#14 Specified no fees or taxes but unsure about discounts so I included it.
SELECT SUM(
    IF(
        HasTowingRepairRental(I.invoiceId),
        0,
        milesTowed * costPerMile
        )
    )
FROM InvoiceProductData IPD
JOIN Invoices I on IPD.invoiceId = I.invoiceId
JOIN Product P on IPD.productId = P.productId
WHERE type = 'T';

#15
DROP TEMPORARY TABLE IF EXISTS duplicateProductTypes;
DROP TEMPORARY TABLE IF EXISTS towingCounts;
DROP TEMPORARY TABLE IF EXISTS rentalCounts;
DROP TEMPORARY TABLE IF EXISTS repairCounts;
DROP TEMPORARY TABLE IF EXISTS concessionCounts;

CREATE TEMPORARY TABLE duplicateProductTypes(invoiceId int);
CREATE TEMPORARY TABLE towingCounts(invoiceId int, count int);
CREATE TEMPORARY TABLE repairCounts(invoiceId int, count int);
CREATE TEMPORARY TABLE concessionCounts(invoiceId int, count int);
CREATE TEMPORARY TABLE rentalCounts(invoiceId int, count int);

INSERT INTO rentalCounts(invoiceId, count)
SELECT I.invoiceId, Count(invoiceProductDataId) AS count
FROM Invoices I
JOIN InvoiceProductData IPD on I.invoiceId = IPD.invoiceId
JOIN Product P on IPD.productId = P.productId
WHERE type = 'R'
GROUP BY invoiceId;

INSERT INTO repairCounts(invoiceId, count)
SELECT I.invoiceId, Count(invoiceProductDataId) AS count
FROM Invoices I
         JOIN InvoiceProductData IPD on I.invoiceId = IPD.invoiceId
         JOIN Product P on IPD.productId = P.productId
WHERE type = 'F'
GROUP BY invoiceId;

INSERT INTO towingCounts(invoiceId, count)
SELECT I.invoiceId, Count(invoiceProductDataId) AS count
FROM Invoices I
         JOIN InvoiceProductData IPD on I.invoiceId = IPD.invoiceId
         JOIN Product P on IPD.productId = P.productId
WHERE type = 'T'
GROUP BY invoiceId;

INSERT INTO concessionCounts(invoiceId, count)
SELECT I.invoiceId, Count(invoiceProductDataId) AS count
FROM Invoices I
         JOIN InvoiceProductData IPD on I.invoiceId = IPD.invoiceId
         JOIN Product P on IPD.productId = P.productId
WHERE type = 'C'
GROUP BY invoiceId;

INSERT INTO duplicateProductTypes(invoiceId)
SELECT invoiceId FROM rentalCounts WHERE count > 1;
INSERT INTO duplicateProductTypes(invoiceId)
SELECT invoiceId FROM concessionCounts WHERE count > 1;
INSERT INTO duplicateProductTypes(invoiceId)
SELECT invoiceId FROM repairCounts WHERE count > 1;
INSERT INTO duplicateProductTypes(invoiceId)
SELECT invoiceId FROM towingCounts WHERE count > 1;

SELECT * FROM Invoices where invoiceId IN( SELECT * FROM duplicateProductTypes);

UPDATE InvoiceProductData SET associatedRepairId = NULL;
DELETE FROM InvoiceProductData;
DELETE FROM Invoices;
DELETE FROM Product;