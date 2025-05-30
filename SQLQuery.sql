create database RESTAURANT;
USE RESTAURANT;

drop table tblUser;
INSERT INTO tblUser (UID,username,password,position,name) VALUES ('1','mana','123','Manager','M');
INSERT INTO tblUser (UID,username,password,position,name) VALUES ('2','recep','123','Receptionist','R');
SELECT name, role, ID FROM tblUser WHERE username = 'mana' AND password = '123';
-- Tạo bảng tblUser
CREATE TABLE tblUser (
    UID INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    name VARCHAR(50),
    position VARCHAR(50)
);
SELECT * FROM tblUser;
delete from tblUser where UID = '2';


-- Tạo bảng tblClient
CREATE TABLE tblClient (
    CID INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    tel VARCHAR(50),
    email VARCHAR(50),
    address VARCHAR(255)
);
DROP TABLE tblClient;
INSERT INTO TBLCLIENT VALUES('Nguyen Van A','0122336565','a@gmail.com','Ha Noi');

-- Tạo bảng tblTable
CREATE TABLE tblTable (
    TID INT IDENTITY(1,1) PRIMARY KEY,
    position VARCHAR(50) UNIQUE,
    seatNumber INT
);
INSERT INTO tblTable VALUES ('1-1','4');
INSERT INTO tblTable VALUES ('1-2','6');
INSERT INTO tblTable VALUES ('1-3','4');
INSERT INTO tblTable VALUES ('1-4','8');
INSERT INTO tblTable VALUES ('1-5','4');
INSERT INTO tblTable VALUES ('2-1','6');
INSERT INTO tblTable VALUES ('2-2','10');
INSERT INTO tblTable VALUES ('2-3','10');
INSERT INTO tblTable VALUES ('2-4','10');
INSERT INTO tblTable VALUES ('2-5','10');
DROP TABLE tblTable;
select * from tblTable;

-- Tạo bảng tblOrder
CREATE TABLE tblOrder (
    OID INT IDENTITY(1,1) PRIMARY KEY,
    tblTableTID INT,
    
    isPaid BIT,
    FOREIGN KEY (tblTableTID) REFERENCES tblTable(TID),
   
);
DROP TABLE TBLORDER;
INSERT INTO TBLORDER VALUES(1,1,0);
-- Tạo bảng tblDish
CREATE TABLE tblDish (
    DID INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(50),
    price INT,
    des VARCHAR(255)
);
SELECT * FROM tblDish;

-- Tạo bảng tblOrderedDish
CREATE TABLE tblOrderedDish (
    ODID INT IDENTITY(1,1) ,
    OrderTime SmallDateTime,
    price INT,
	quantity INT,
    note VARCHAR(255),
    tblDishDID INT,
    tblOrderOID INT,
	PRIMARY KEY (TBLORDEROID,ODID),
    FOREIGN KEY (tblDishDID) REFERENCES tblDish(DID),
    FOREIGN KEY (tblOrderOID) REFERENCES tblOrder(OID)
);
INSERT INTO tblOrderedDish VALUES ('2025-05-26 10:00','100000','','1','1');
DROP TABLE tblOrderedDish;
-- Tạo bảng tblReceipt
CREATE TABLE tblReceipt (
    RID INT IDENTITY(1,1) PRIMARY KEY,
    discount INT,
    tblUserUID INT,
    tblOrderOID INT,
    tblClientCID INT,
    FOREIGN KEY (tblUserUID) REFERENCES tblUser(UID),
    FOREIGN KEY (tblOrderOID) REFERENCES tblOrder(OID),
    FOREIGN KEY (tblClientCID) REFERENCES tblClient(CID)
);
DROP TABLE tblReceipt;

-- Truy vấn cho code
SELECT * FROM tblClient;

SELECT 
    t.TID, t.position, t.seatNumber, 
    o.OID, o.isPaid, o.tblClientCID, 
    od.ODID, od.OrderTime, od.price AS Price, od.note, od.tblDishDID, 
	d.name as dishName, d.type, d.des, 
    c.name as clientName, c.tel, c.address, c.email 
FROM tblTable t 
LEFT JOIN tblOrder o ON t.TID = o.tblTableTID 
LEFT JOIN tblOrderedDish od ON o.OID = od.tblOrderOID 
LEFT JOIN tblClient c ON c.CID = o.tblClientCID 
LEFT JOIN tblDish d on d.DID = od.tblDishDID
WHERE t.TID BETWEEN 1 AND 10 
ORDER BY t.TID, o.OID, od.ODID;

SELECT * FROM TBLORDEREDDISH OD
JOIN TBLDISH D ON D.DID = OD.TBLDISHDID
JOIN TBLORDER O ON O.OID = OD.tblOrderOID
JOIN TBLTABLE T ON O.tblTableTid = T.TID
WHERE TID = '1';

INSERT INTO TBLORDER VALUES(1,0);
INSERT INTO tblOrderedDish VALUES ('2025-05-26 10:00','100000','1','','1','1');
INSERT INTO tblOrderedDish VALUES ('2025-05-26 10:00','10000','2','','2','1');
INSERT INTO tblReceipt (tblUserUID, tblClientCID, tblOrderOID, discount)  VALUES ();
SELECT * FROM TBLCLIENT;
SELECT * FROM TBLRECEIPT;
SELECT * FROM TBLORDER;
SELECT * FROM TBLORDEREDDISH;
SELECT * FROM tblTable;
SELECT TOP 1 OID 
FROM tblOrder 
ORDER BY OID DESC;
SELECT TOP 1 OID FROM tblOrder ORDER BY OID DESC;
SELECT * FROM tblOrderedDish od
JOIN tblDish d on d.DID = od.tblDishDID
JOIN tblOrder o on o.OID = od.tblOrderOID
JOIN tblTable t on t.TID = o.tblTableTID
WHERE t.position ='1-1' and o.isPaid ='0';

truncate table tblOrderedDish;
truncate table tblReceipt;
truncate table tblOrder;
delete from tblOrder;

UPDATE tblOrder SET isPaid = 1 WHERE OID = 5;

SELECT 
    d.DID,
    d.name,
    d.type,
    d.price AS unitPrice,
    d.des,
    ISNULL(SUM(od.quantity), 0) AS orderCount,
    ISNULL(SUM(od.quantity * od.price), 0) AS revenue
FROM tblDish d
LEFT JOIN tblOrderedDish od ON d.DID = od.tblDishDID
WHERE od.OrderTime BETWEEN '2025-5-30' AND '2025-5-31'
GROUP BY d.DID, d.name, d.type, d.price, d.des



SELECT 
	r.tblOrderOID,
	c.name,
	c.CID,
	t.position,
    od.ODID,
    od.OrderTime,
    od.quantity,
    od.price,
    od.note 
FROM tblOrderedDish od 
JOIN tblDish d ON od.tblDishDID = d.DID 
JOIN tblOrder o ON od.tblOrderOID = o.OID 
JOIN tblTable t ON o.tblTableTID = t.TID 
JOIN tblReceipt r ON  o.OID = r.tblOrderOID   
JOIN tblClient c on r.tblClientCID = c.CID  
WHERE d.DID ='2' AND od.OrderTime BETWEEN '2025-5-10' AND '2025-5-31'
ORDER BY od.OrderTime ASC;


SELECT od.ODID,od.OrderTime,od.price as oprice, od.quantity, od.note,od.tblDishDID, od.tblOrderOID,d.name,d.type,od.price,d.des,o.OID,o.tblTableTID,o.isPaid,r.RID,r.discount,r.tblUserUID,r.rblClientCID,c.CID,c.name as cname,c.tel,c.email,c.address,t.TID,t.position,t.seatNumber FROM tblOrderedDish od  
JOIN tblDish d on d.DID = od.tblDishDID 
JOIN tblOrder o on o.OID = od.tblOrderOID
JOIN tblReceipt r ON od.tblOrderOID = r.tblOrderOID 
JOIN tblClient c ON r.tblClientCID = c.CID 
JOIN tblTable t on t.TID = o.tblTableTID 
WHERE o.OID = '23'