CREATE DATABASE Pro;
USE Pro;

CREATE TABLE Contractor
(
    ContractorID INT PRIMARY KEY DEFAULT 1,       
    Username VARCHAR(50),
    Password VARCHAR(50),
    Email VARCHAR(50)
);

CREATE TABLE Administrator
(
    AdminID INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(50) ,
    Username VARCHAR(50),
    Password VARCHAR(50)
);

CREATE TABLE Client (
    ClientID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Password VARCHAR(50),
    Address VARCHAR(200),
    CreditCardInfo VARCHAR(10),
    PhoneNumber VARCHAR(10),
    Email VARCHAR(50) UNIQUE,
    UNIQUE (ClientID)
);

CREATE TABLE QuoteRequest
(
    RequestID INT AUTO_INCREMENT PRIMARY KEY,
    ClientID INT,   
    RequestDate DATE,
	NumberOfTrees INT,
    Note VARCHAR(200),
    Size VARCHAR(10), 
    Height DECIMAL(3, 1), 
    Location VARCHAR(100),
    ProximityToHouse FLOAT (20),
    UNIQUE (RequestID),
    FOREIGN KEY (ClientID) REFERENCES Client (ClientID)    
);

CREATE TABLE QuoteResponse
(
    ResponseID INT AUTO_INCREMENT PRIMARY KEY,
    RequestID INT,
    ClientID INT,
    ResponseDate DATE,
    Price DOUBLE,
    WorkPeriodFrom DATE,
    WorkPeriodTo DATE,     
    Note VARCHAR(200),
    UNIQUE (ResponseID),
    FOREIGN KEY (RequestID) REFERENCES QuoteRequest (RequestID),
	FOREIGN KEY (ClientID) REFERENCES QuoteRequest (ClientID)
);

CREATE TABLE ClientRespondToQuoteResponse (
    ClientResponseID INT AUTO_INCREMENT PRIMARY KEY,
    ContractorID INT,
    ResponseID INT,
    ResponseDate DATE,
    Status ENUM('Accepted', 'Rejected', 'Pending', 'RequestAgain'),
    Note VARCHAR(255),
    UNIQUE (ClientResponseID),
    FOREIGN KEY (ContractorID) REFERENCES Contractor(ContractorID),
    FOREIGN KEY (ResponseID) REFERENCES QuoteResponse(ResponseID)
);

CREATE TABLE ContractorRespondToClientResponse (
    ContractorResponseID INT AUTO_INCREMENT PRIMARY KEY,
    ContractorID INT,
    ClientResponseID INT,
    Status ENUM('Accepted', 'Rejected', 'Pending', 'RequestAgain'),
    ResponseDate DATE,
    Note VARCHAR(255),
    ModifiedPrice DOUBLE,
    ModifiedWorkPeriodFrom DATE,
    ModifiedWorkPeriodTo DATE,  
    FOREIGN KEY (ContractorID) REFERENCES Contractor(ContractorID),
    FOREIGN KEY (ClientResponseID) REFERENCES ClientRespondToQuoteResponse(ClientResponseID)
);

CREATE TABLE OrderOfWork (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    RequestID INT,
    ClientID INT,   
    StartDate DATE,
    EndDate DATE,
    Status ENUM('Initiated', 'Finished', 'Underway', 'Undecided'),    
    NumberOfTreesCut INT,
    DateOfCut DATE,
    ContractorID VARCHAR(50),
    FOREIGN KEY (ClientID) REFERENCES Client(ClientID),
    FOREIGN KEY (RequestID) REFERENCES QuoteRequest(RequestID)   
);

CREATE TABLE Bills (
    BillID INT AUTO_INCREMENT PRIMARY KEY,
    ContractorID INT,
    OrderID INT,  
    Amount DECIMAL(10, 2),
    GeneratedDate DATE,
    BillStatus ENUM('Issued', 'Pending'),
    PaymentStatus ENUM('Paid', 'Unpaid', 'Overdue'),
    FOREIGN KEY (OrderID) REFERENCES OrderOfWork(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ContractorID) REFERENCES Contractor(ContractorID) ON DELETE CASCADE
);

CREATE TABLE ResponseToBill (
    ResponseToBillID INT AUTO_INCREMENT PRIMARY KEY,
    BillID INT,
    ClientID INT,
    ResponseDate DATE,
    Note VARCHAR(50),
    PaymentStatus ENUM('Paid', 'Unpaid'),
    FOREIGN KEY (BillID) REFERENCES Bills(BillID) ON DELETE CASCADE,
    FOREIGN KEY (ClientID) REFERENCES Client(ClientID) ON DELETE CASCADE
);