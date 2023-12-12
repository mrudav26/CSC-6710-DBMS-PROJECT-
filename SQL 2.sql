Use Pro; 

insert into Contractor( Username, Password, Email)
values ( 'david', 'david123', 'david@gmail.com');

select * from Contractor;

insert into Administrator(Username, Password, Email)
values 
( 'manav', 'manav123', 'manav@gmail.com'),
( 'mrudav', 'mrudav123', 'mrudav@gmail.com');             

select * from administrator;

insert into Client(  FirstName, LastName, Password, Address, CreditCardInfo, PhoneNumber, Email)
values 
('Susie ', 'Guzman','susie123', '1234 whatever street detroit MI 48202','1010101010', '11111', 'susie@gmail.com'),
('Lawson', 'Lee', 'lawson123', '5678 ivan street tata CO 12561','2020202020', '22222','lawson@gmail.com'),
('Brady', 'Plum', 'brady23', '9101 marko street brat DU 54321','3030303030', '33333','brady@gmail.com'),
('Moore', 'Mone', 'moore123','1121 frey street sestra MI 48202','4040404040', '44444', 'moore@gmail.com'),
('Phillips', 'Zipp','phillips123','3141 m8s street baka IL 48000','5050505050', '55555', 'phillips@gmail.com'),
('Pierce', 'Lucki','pierce123','5161 yolos street ides CM 24680','6060606060', '66666', 'pierce@gmail.com'),
('Francis','Hawkin', 'francis123','7181 egypt street lolas DT 13579','7070707070', '77777', 'francis@gmail.com'),
('Smith', 'Joe','smith123','9202 sign street samo ne tu MH 09876','8080808080', '88888', 'smith@gmail.com'),
('Stone', 'Pills','stone123','1222 snoop street kojik HW 87654','9090909090', '99999', 'stone@gmail.com'),
('Alice', 'Smith', 'alice123', '9101 Oak Avenue, New York, NY 10001', '1122112211', '12121', 'alice@gmail.com'),
('John', 'Doe', 'john123', '5678 Main Street, Chicago, IL 60601', '2233223322', '23232', 'john@gmail.com'),
('Robert', 'Johnson', 'robert123', '2468 Elm Drive, Los Angeles, CA 90001', '3344334433', '34343', 'robert@gmail.com'),
('Emily', 'Clark', 'emily123', '1357 Pine Road, San Francisco, CA 94101', '4455445544', '45454', 'emily@gmail.com'),
('Daniel', 'White', 'daniel123', '7890 Cedar Lane, Houston, TX 77001', '5566556655', '56565', 'daniel@gmail.com'),
('Megan', 'Miller', 'megan123', '1122 Birch Street, Miami, FL 33101', '6677667766', '67676', 'megan@gmail.com'),
('Charlie', 'Davis', 'charlie123', '9922 Maple Avenue, Seattle, WA 98101', '7788778877', '78787', 'charlie@gmail.com');

select* from Client; 

insert into QuoteRequest (ClientID, RequestDate, Note, NumberOfTrees, Size, Height, Location, ProximityToHouse)
values
(1,'2010-01-01', 'note1', 1, 10, 10.1, 'frontyard', 16.9),
(2,'2011-02-02', 'note2', 2, 6, 11.2, 'backyard', 15.8),
(3,'2012-03-03', 'note3', 3, 5, 13.3, 'frontyard', 14.7),
(4,'2013-04-04', 'note4', 4, 8, 9.4, 'backyard', 7.6),
(5,'2014-05-05', 'note5', 5, 9, 8.5, 'frontyard', 5.5),
(6,'2015-06-06', 'note6', 6, 4, 7.6, 'backyard', 9.4),
(7,'2016-07-07', 'note7', 7, 4, 14.7, 'frontyard', 13.3),
(8,'2017-08-08', 'note8', 8, 9, 15.8, 'backyard', 11.2),
(9,'2018-09-09', 'note9', 9, 12, 16.9, 'frontyard', 10.1),
(10, '2019-10-10', 'note10', 10, 13, 17.8, 'backyard', 9.8),
(11, '2020-11-11', 'note11', 11, 14, 18.8, 'backyard', 10.8),
(12, '2021-12-12', 'note12', 12, 15, 19.8, 'backyard', 11.8),
(13, '2022-01-01', 'note13', 13, 16, 20.8, 'backyard', 12.8),
(14, '2023-02-02', 'note14', 14, 17, 21.8, 'backyard', 13.8),
(15, '2024-03-03', 'note15', 15, 18, 22.8, 'backyard', 14.8),
(16, '2025-04-14', 'note16', 16, 19, 23.8, 'backyard', 15.8);

select * from QuoteRequest;

insert into QuoteResponse (RequestID, ClientID, ResponseDate, Price, WorkPeriodFrom, WorkPeriodTo, Note) 
values
(1, 1, '2010-01-01', 100, '2010-02-02', '2010-02-12', 'Note1'),
(2, 2, '2011-02-02', 200, '2011-03-03', '2011-03-13', 'Note2'),
(3, 3, '2012-03-03', 300, '2012-04-04', '2012-04-14', 'Note3'),
(4, 4, '2013-04-04', 400, '2013-05-05', '2013-05-15', 'Note4'),
(5, 5, '2014-05-05', 500, '2014-06-06', '2014-06-16', 'Note5'),
(6, 6, '2015-06-06', 600, '2015-07-07', '2015-07-17', 'Note6'),
(7, 7, '2016-07-07', 700, '2016-08-08', '2016-08-18', 'Note7'),
(8, 8, '2017-08-08', 800, '2017-09-09', '2017-09-19', 'Note8'),
(9, 9, '2018-09-09', 900, '2018-10-10', '2018-10-20', 'Note9'),
(10, 10, '2019-10-10', 1000, '2019-11-11', '2019-11-21', 'Note10'), 
(11, 11, '2020-11-11', 1100, '2020-12-12', '2020-12-22', 'Note11'),
(12, 12, '2021-12-12', 1200, '2021-01-13', '2020-02-23', 'Note12'),
(13, 13, '2022-01-01', 1300, '2022-02-02', '2022-02-12', 'Note13'),
(14, 14, '2023-02-02', 1400, '2023-03-03', '2023-03-13', 'Note14'),
(15, 15, '2024-03-03', 1500, '2024-04-04', '2024-04-14', 'Note15'),
(16, 16, '2025-04-04', 1600, '2025-05-05', '2025-05-15', 'Note16');

select * from QuoteResponse; 

insert into ClientRespondToQuoteResponse (ContractorID, ResponseID, ResponseDate, Status, Note)
values 
(1, 1, '2010-01-02', 'Accepted', 'abc'), 
(1, 2, '2011-02-03', 'Rejected', 'xyz'),
(1, 3, '2012-03-04', 'Accepted', 'wer'),
(1, 4, '2013-04-05', 'RequestAgain', 'ert'),
(1, 5, '2014-05-06', 'Accepted', 'yui'),
(1, 6, '2015-06-07', 'Rejected', 'iop'),
(1, 7, '2016-07-08', 'Accepted', 'ghj'),
(1, 8, '2017-08-09', 'RequestAgain', 'klm'),
(1, 9, '2018-09-10', 'Accepted', 'nop'), 
(1, 10, '2019-10-11', 'Rejected', 'ypu'), 
(1, 11, '2020-11-12', 'RequestAgain', 'mfh'),
(1, 12, '2021-12-13', 'Accepted', 'tey'),
(1, 13, '2022-01-14', 'Rejected', 'hsg'),
(1, 14, '2023-02-15', 'Accepted', 'ncv'),
(1, 15, '2024-03-16', 'RequestAgain', 'tdc'), 
(1, 16, '2025-04-17', 'Accepted', 'ufb');

select * from ClientRespondToQuoteResponse;

insert into ContractorRespondToClientResponse (ContractorID, ClientResponseID, Status, ResponseDate, Note, ModifiedPrice, ModifiedWorkPeriodFrom, ModifiedWorkPeriodTo) 
values
(1, 1, 'Accepted', '2010-01-03', 'hi hello', 100, '2010-02-02', '2010-02-12'),
(1, 3, 'Accepted', '2012-03-05', 'hi hi', 300, '2012-04-04', '2012-04-14'),
(1, 4, 'Rejected', '2013-04-06', 'bye bye', 350, '2013-05-05', '2013-05-15'), 
(1, 5, 'Accepted', '2014-05-07', 'hi hello', 500, '2014-06-06', '2014-06-16'), 
(1, 7, 'Accepted', '2016-07-09', 'hi hello', 700, '2016-08-08', '2016-08-18'), 
(1, 8, 'Rejected', '2017-08-10', 'bye bye', 750, '2017-09-09', '2017-09-19'), 
(1, 9, 'Accepted', '2018-09-11', 'hi hello', 900, '2018-10-10', '2018-10-20'),
(1, 12, 'Accepted', '2021-12-14', 'hi hi', 1200, '2021-01-13', '2020-02-23'),
(1, 14, 'Accepted', '2023-02-16', 'hi hello', 1400, '2023-03-03', '2023-03-13'),
(1, 15, 'Rejected', '2021-12-14', 'hi hi', 1500, '2024-04-04', '2024-04-14'),
(1, 16, 'Accepted', '2021-12-14', 'bye bye', 1600, '2025-05-05', '2025-05-15');

select * from ContractorRespondToClientResponse;

insert into OrderOfWork (RequestID, ClientID, StartDate , EndDate, Status, NumberOfTreesCut, DateOfCut, ContractorID)
values 
(1, 1, '2010-02-02', '2010-02-12', 'Initiated', 1, '2010-02-12', '1'),
(3, 3, '2012-04-04', '2012-04-14', 'Finished', 3, '2012-04-14', '1'),
(5, 5, '2014-06-06', '2014-06-16', 'Underway', 5, '2014-06-16', '1'),
(7, 7, '2016-08-08', '2016-08-18', 'Undecided', 7, '2016-08-18', '1'),
(9, 9, '2018-10-10', '2018-10-20', 'Initiated', 9, '2018-10-20', '1'),
(12, 12, '2021-01-13', '2020-02-23', 'Initiated', 12, '2020-02-23', '1'),
(14, 14, '2023-03-03', '2023-03-13', 'Finished', 14, '2023-03-13', '1'),
(16, 16, '2025-05-05', '2025-05-15', 'Initiated', 16, '2025-05-15', '1');

select * from OrderOfwork;

insert into Bills ( ContractorID, OrderID, Amount, GeneratedDate, BillStatus, PaymentStatus) 
VALUES 
(1, 1, 100, '2010-02-12', 'Issued', 'Paid'),
(1, 2, 300, '2012-04-14', 'Issued', 'Paid'),
(1, 3, 500, '2014-06-16', 'Issued', 'Overdue'),
(1, 4, 700, '2016-08-18', 'Issued', 'Paid'),
(1, 5, 900, '2018-10-20', 'Issued', 'Overdue'),
(1, 6, 1200, '2020-02-23', 'Issued', 'Paid'),
(1, 7, 1400, '2023-03-13', 'Issued', 'Unpaid'),
(1, 8, 1600, '2025-05-15', 'Issued', 'Paid');

select * from Bills;

INSERT INTO ResponseToBill ( BillID, ClientID, ResponseDate, Note, PaymentStatus) 
VALUES 
(1, 1,'2010-02-12','hi', 'Paid'),
(2, 3,'2012-04-14','hello', 'Paid'), 
(3, 5,'2015-06-16','bye', 'Unpaid'), 
(4, 7,'2016-08-18','good bye', 'Paid'), 
(5, 9,'2019-10-20','thank you', 'Unpaid'), 
(6, 12,'2020-02-23','see yaa', 'Paid'), 
(7, 14,'2024-03-13','next time', 'Unpaid'), 
(8, 16,'2025-05-15','yoyo', 'Paid');

select * from ResponseToBill;
