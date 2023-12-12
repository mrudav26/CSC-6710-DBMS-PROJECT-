import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Connect
 */
@WebServlet("/clientDAO")
public class clientDAO {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public clientDAO() {
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
        //uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Pro?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=Pass1550");
            System.out.println(connect);
        }
    }

    public boolean database_login(String email, String password) throws SQLException {
        try {
            connect_func("john", "Pass1550");
            String sql = "select * from Client where Email = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("failed login");
            return false;
        }
    }


    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
                            + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }

    public List<client> listAllClients() throws SQLException {
        List<client> listClient = new ArrayList<client>();
        String sql = "SELECT * FROM Client";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int clientID = resultSet.getInt("ClientID"); // Retrieve ClientID           
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String password = resultSet.getString("Password");
            String address = resultSet.getString("Address");
            String creditCardInfo = resultSet.getString("CreditCardInfo");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String email = resultSet.getString("Email");

            client clients = new client(clientID,  firstName, lastName,password, address, creditCardInfo, phoneNumber, email);
            listClient.add(clients);
        }
        resultSet.close();
        disconnect();
        return listClient;
    }


    public List<client> getClientsWithCompletedTreeCutting() throws SQLException {
        List<client> clientsWithCompletedTreeCutting = new ArrayList<>();
        connect_func();  // Connect to the database

        try {
            String sql = "SELECT DISTINCT C.ClientID, C.FirstName, C.LastName " +
                         "FROM Client C " +
                         "JOIN OrderOfWork O ON C.ClientID = O.ClientID " +
                         "JOIN Bills B ON O.OrderID = B.OrderID " +
                         "WHERE O.Status = 'Completed'";
            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");

                client clients = new client(clientID, firstName, lastName);
                clientsWithCompletedTreeCutting.add(clients);
            }
        } finally {
            disconnect();  // Close the database connection
        }

        return clientsWithCompletedTreeCutting;
    }
    
    public List<client> getBigClients() throws SQLException {
        List<client> bigClients = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
            String sql = "SELECT c.ClientID, c.FirstName, c.LastName,\n"
            		+ "       SUM(o.NumberOfTreesCut) AS TotalTreesCut \n"
            		+ "FROM Client c \n"
            		+ "JOIN OrderOfWork o ON c.ClientID = o.ClientID \n"
            		+ "WHERE o.ContractorID = '1' \n"
            		+ "GROUP BY c.ClientID, c.FirstName, c.LastName \n"
            		+ "HAVING TotalTreesCut = (\n"
            		+ "    SELECT MAX(TreeCount) \n"
            		+ "    FROM (\n"
            		+ "        SELECT SUM(ow.NumberOfTreesCut) AS TreeCount \n"
            		+ "        FROM OrderOfWork ow  \n"
            		+ "        WHERE ow.ContractorID = '1'\n"
            		+ "        GROUP BY ow.ClientID\n"
            		+ "    ) AS MaxTreeCount\n"
            		+ ");";

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                int totalTreesCut = resultSet.getInt("TotalTreesCut");

                client client = new client(clientID, firstName, lastName, totalTreesCut);
                bigClients.add(client);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return bigClients;
    }
    
    public List<client> getEasyClients() throws SQLException {
        List<client> easyClients = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
            String sql = "SELECT DISTINCT C.ClientID, C.FirstName, C.LastName " +
                         "FROM Client C " +
                         "JOIN QuoteRequest QR ON C.ClientID = QR.ClientID " +
                         "JOIN QuoteResponse QRS ON QR.RequestID = QRS.RequestID " +
                         "JOIN ClientRespondToQuoteResponse CR ON QRS.ResponseID = CR.ResponseID " +
                         "WHERE CR.ContractorID = 1 AND CR.Status = 'Accepted'";

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");

                client client = new client(clientID, firstName, lastName);
                easyClients.add(client);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return easyClients;
    }

    public List<client> getClientsWithNoOrdersOfWork() throws SQLException {
        List<client> clientsWithNoOrders = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
        	String sql = "SELECT DISTINCT C.* " +
                    "FROM Client C " +
                    "WHERE C.ClientID NOT IN (" +
                    "    SELECT DISTINCT C.ClientID " +
                    "    FROM Client C " +
                    "    JOIN QuoteRequest QR ON C.ClientID = QR.ClientID " +
                    "    JOIN OrderOfWork OW ON QR.RequestID = OW.RequestID " +
                    ")";


            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                // Add more fields as needed

                client client = new client(clientID, firstName, lastName);
                clientsWithNoOrders.add(client);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return clientsWithNoOrders;
    }


    public List<client> getBadClientsWithOverdueBills() throws SQLException {
        List<client> badClients = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
            String sql = "SELECT DISTINCT C.ClientID, C.FirstName, C.LastName, B.GeneratedDate, B.PaymentStatus, B.BillStatus\n"
            		+ "            		FROM OrderOfWork O\n"
            		+ "            		JOIN Bills B ON O.OrderID = B.OrderID\n"
            		+ "            		JOIN Client C ON O.ClientID = C.ClientID\n"
            		+ "            		LEFT JOIN ResponseToBill RTB ON B.BillID = RTB.BillID\n"
            		+ "            		WHERE (B.BillID IS NULL OR (B.PaymentStatus = 'Overdue' AND RTB.PaymentStatus <> 'Paid'))\n"
            		+ "            		AND (DATEDIFF(NOW(), B.GeneratedDate) > 7 OR RTB.ResponseDate IS NULL);";
            
            		

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String generatedDate = resultSet.getString("GeneratedDate");
                String billStatusString = resultSet.getString("BillStatus");
		        client.BillStatus billStatus = client.BillStatus.valueOf(billStatusString);

		        String paymentStatusString = resultSet.getString("PaymentStatus");
		        client.PaymentStatus paymentStatus = client.PaymentStatus.valueOf(paymentStatusString);

                client badClient = new client(clientID, firstName, lastName, generatedDate, billStatus, paymentStatus);
                badClients.add(badClient);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return badClients;
    }
    
    public List<client> getGoodClients() throws SQLException {
        List<client> goodClients = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
        	 String sql = "SELECT c.ClientID, c.FirstName, c.LastName, c.Email, b.GeneratedDate,b.BillStatus, rb.PaymentStatus\n"
                     + "FROM Client c\n"
                     + "JOIN ResponseToBill rb ON c.ClientID = rb.ClientID\n"
                     + "JOIN Bills b ON rb.BillID = b.BillID\n"
                     + "WHERE b.BillStatus = 'Issued'\n"
                     + "  AND rb.PaymentStatus = 'Paid'\n"
                     + "  AND rb.ResponseDate <= DATE_ADD(b.GeneratedDate, INTERVAL 24 HOUR);\n";

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String generatedDate = resultSet.getString("GeneratedDate");
                String billStatusString = resultSet.getString("BillStatus");
		        client.BillStatus billStatus = client.BillStatus.valueOf(billStatusString);
                String paymentStatusString = resultSet.getString("PaymentStatus");
		        client.PaymentStatus paymentStatus = client.PaymentStatus.valueOf(paymentStatusString);

                client goodClient = new client(clientID, firstName, lastName, generatedDate,billStatus, paymentStatus);
                goodClients.add(goodClient);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return goodClients;
    }

    

    public List<client> getClientsStatistics() throws SQLException {
        List<client> clientStatisticsList = new ArrayList<>();
        connect_func(); // Connect to the database

        try {
        	String sql = "SELECT " +
                    "c.ClientID, " +
                    "c.FirstName, " +
                    "c.LastName, " +
                    "COUNT(orw.NumberOfTreesCut) AS TotalTreesCut, " +
                    "SUM(b.Amount) AS TotalDueAmount, " +
                    "SUM(CASE WHEN rb.PaymentStatus = 'Paid' THEN b.Amount ELSE 0 END) AS TotalPaidAmount, " +
                    "MAX(orw.DateOfCut) AS DateOfWorkDone " +
                "FROM Client c " +
                "JOIN QuoteRequest qr ON c.ClientID = qr.ClientID " +
                "JOIN " +
                    "OrderOfWork orw ON qr.RequestID = orw.RequestID " +
                "JOIN " +
                    "Bills b ON orw.OrderID = b.OrderID " +
                "JOIN " +
                    "ResponseToBill rb ON b.BillID = rb.BillID " +
                "WHERE " +
                    "orw.Status = 'Finished' " +
                "GROUP BY " +
                    "c.ClientID, c.FirstName, c.LastName";

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                int totalTreesCut = resultSet.getInt("TotalTreesCut"); 
                double totalDueAmount = resultSet.getDouble("TotalDueAmount"); 
                double totalPaidAmount = resultSet.getDouble("TotalPaidAmount"); 
                String dateOfWorkDone = resultSet.getString("DateOfWorkDone"); 
                
                System.out.println("TotalTreesCut: " + totalTreesCut);
                System.out.println("TotalDueAmount: " + totalDueAmount);
                System.out.println("TotalPaidAmount: " + totalPaidAmount);
                System.out.println("DateOfWorkDone: " + dateOfWorkDone);

                client clientStatistics = new client(clientID, firstName, lastName, totalTreesCut, totalDueAmount, totalPaidAmount, dateOfWorkDone);
                clientStatisticsList.add(clientStatistics);
            }
        } finally {
            disconnect(); // Close the database connection
        }

        return clientStatisticsList;
    }

    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
            connect.close();
        }
    }

    public void insert(client clients) throws SQLException {
        connect_func("root", "pass1234");
        String sql = "INSERT INTO Client ( FirstName, LastName, Password, Address, CreditCardInfo, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);        
        preparedStatement.setString(1, clients.getFirstName());
        preparedStatement.setString(2, clients.getLastName());
        preparedStatement.setString(3, clients.getPassword());
        preparedStatement.setString(4, clients.getAddress());
        preparedStatement.setString(5, clients.getCreditCardInfo());
        preparedStatement.setString(6, clients.getPhoneNumber());
        preparedStatement.setString(7, clients.getEmail());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }




    public boolean delete(String Email) throws SQLException {
        String sql = "DELETE FROM Client WHERE Email = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, Email);

        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;
    }

    public boolean update(client clients) throws SQLException {
        String sql = "update Client set ClientID=?, FirstName=?, LastName =?,Password = ?,Address=?, CreditCardInfo=?, PhoneNumber=? where Email = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, clients.getClientID());       
        preparedStatement.setString(2, clients.getFirstName());
        preparedStatement.setString(3, clients.getLastName());
        preparedStatement.setString(4, clients.getPassword());
        preparedStatement.setString(5, clients.getAddress());
        preparedStatement.setString(6, clients.getCreditCardInfo());
        preparedStatement.setString(7, clients.getPhoneNumber());
        preparedStatement.setString(8, clients.getEmail());

        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;
    }

    public client getClient(int ClientID) throws SQLException {
        client client = null;
        String sql = "SELECT * FROM Client WHERE ClientID = ?";

        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, ClientID);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
        	 
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String password = resultSet.getString("Password");
            String address = resultSet.getString("Address");
            String creditCardInfo = resultSet.getString("CreditCardInfo");
            String phoneNumber = resultSet.getString("PhoneNumber");           
            String email = resultSet.getString("Email");

            client = new client(firstName, lastName, password, address, creditCardInfo, phoneNumber,email);
        }

        resultSet.close();
        statement.close();
        
        disconnect(); 

        return client;
    }

    public boolean checkEmail(String Email) throws SQLException {
        boolean checks = false;
        String sql = "SELECT * FROM Client WHERE Email = ?";
        connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, Email);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println(checks);

        if (resultSet.next()) {
            checks = true;
        }

        System.out.println(checks);
        return checks;
    }

    public boolean checkPassword(String Password) throws SQLException {
        boolean checks = false;
        String sql = "SELECT * FROM Client WHERE password = ?";
        connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, Password);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println(checks);

        if (resultSet.next()) {
            checks = true;
        }

        System.out.println(checks);
        return checks;
    }


    public boolean isValid(String Email, String Password) throws SQLException {
        String sql = "SELECT * FROM Client";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.last();

        int setSize = resultSet.getRow();
        resultSet.beforeFirst();

        for (int i = 0; i < setSize; i++) {
            resultSet.next();
            if (resultSet.getString("Email").equals(Email) && resultSet.getString("Password").equals(Password)) {
                return true;
            }
        }
        return false;
    }


    public void init() throws SQLException, FileNotFoundException, IOException {
        connect_func();
        statement = (Statement) connect.createStatement();

        String[] INITIAL = {"drop database if exists Pro; ",
                "create database Pro; ",
                "use Pro; ",
                "drop table if exists Client; ",
                ("CREATE TABLE if not exists Client( " +
                        "ClientID INT AUTO_INCREMENT PRIMARY KEY,\n"
                        + "    FirstName VARCHAR(50),\n"
                        + "    LastName VARCHAR(50),\n"
                        + "    Password VARCHAR(50),\n"
                        + "    Address VARCHAR(200),\n"
                        + "    CreditCardInfo VARCHAR(10),\n"
                        + "    PhoneNumber VARCHAR(10),\n"
                        + "    Email VARCHAR(50) UNIQUE,\n"
                        + "    UNIQUE (ClientID) " + "); ")
        };
        String[] TUPLES = {("insert into Client(  FirstName, LastName, Password, Address, CreditCardInfo, PhoneNumber, Email)\n"
        		+ "values \n"
        		+ "('Susie ', 'Guzman','susie123', '1234 whatever street detroit MI 48202','1010101010', '11111', 'susie@gmail.com'),\n"
        		+ "('Lawson', 'Lee', 'lawson123', '5678 ivan street tata CO 12561','2020202020', '22222','lawson@gmail.com'),\n"
        		+ "('Brady', 'Plum', 'brady23', '9101 marko street brat DU 54321','3030303030', '33333','brady@gmail.com'),\n"
        		+ "('Moore', 'Mone', 'moore123','1121 frey street sestra MI 48202','4040404040', '44444', 'moore@gmail.com'),\n"
        		+ "('Phillips', 'Zipp','phillips123','3141 m8s street baka IL 48000','5050505050', '55555', 'phillips@gmail.com'),\n"
        		+ "('Pierce', 'Lucki','pierce123','5161 yolos street ides CM 24680','6060606060', '66666', 'pierce@gmail.com'),\n"
        		+ "('Francis','Hawkin', 'francis123','7181 egypt street lolas DT 13579','7070707070', '77777', 'francis@gmail.com'),\n"
        		+ "('Smith', 'Joe','smith123','9202 sign street samo ne tu MH 09876','8080808080', '88888', 'smith@gmail.com'),\n"
        		+ "('Stone', 'Pills','stone123','1222 snoop street kojik HW 87654','9090909090', '99999', 'stone@gmail.com'),\n"
        		+ "('Alice', 'Smith', 'alice123', '9101 Oak Avenue, New York, NY 10001', '1122112211', '12121', 'alice@gmail.com'),\n"
        		+ "('John', 'Doe', 'john123', '5678 Main Street, Chicago, IL 60601', '2233223322', '23232', 'john@gmail.com'),\n"
        		+ "('Robert', 'Johnson', 'robert123', '2468 Elm Drive, Los Angeles, CA 90001', '3344334433', '34343', 'robert@gmail.com'),\n"
        		+ "('Emily', 'Clark', 'emily123', '1357 Pine Road, San Francisco, CA 94101', '4455445544', '45454', 'emily@gmail.com'),\n"
        		+ "('Daniel', 'White', 'daniel123', '7890 Cedar Lane, Houston, TX 77001', '5566556655', '56565', 'daniel@gmail.com'),\n"
        		+ "('Megan', 'Miller', 'megan123', '1122 Birch Street, Miami, FL 33101', '6677667766', '67676', 'megan@gmail.com'),\n"
        		+ "('Charlie', 'Davis', 'charlie123', '9922 Maple Avenue, Seattle, WA 98101', '7788778877', '78787', 'charlie@gmail.com');\n"
        		+ "")
        };
        for (int i = 0; i < INITIAL.length; i++)
            statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)
            statement.execute(TUPLES[i]);
        disconnect();
    }
}