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
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/OrderOfWorkDAO")
public class OrderOfWorkDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OrderOfWorkDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
	 protected void connect_func() throws SQLException {

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
		        String sql = "SELECT * FROM OrderOfWork WHERE OrderID = ?";
		        preparedStatement = connect.prepareStatement(sql);
		        preparedStatement.setString(1, email);
		        ResultSet rs = preparedStatement.executeQuery();
		        return rs.next();
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		        return false;
		    } finally {
		        disconnect();
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
                    .getConnection("jdbc:mysql://127.0.0.1:3306/pro?"
                            + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    
    public List<OrderOfWork> listAllOrders() throws SQLException {
        List<OrderOfWork> listOrders = new ArrayList<>();
        String sql = "SELECT * FROM OrderOfWork";
        connect_func();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int requestID = resultSet.getInt("RequestID");
                int clientID = resultSet.getInt("ClientID");
                String startDate = resultSet.getString("StartDate");
                String endDate = resultSet.getString("EndDate");
                int numberOfTreesCut = resultSet.getInt("NumberOfTreesCut");
                String dateOfCut = resultSet.getString("DateOfCut");
                String contractorID = resultSet.getString("ContractorID");

                String statusString = resultSet.getString("Status");
                OrderOfWork.Status status = null;
                if (statusString != null) {
                    try {
                        String enumFormat = statusString.replace(" ", "");
                        status = OrderOfWork.Status.valueOf(enumFormat);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Converted Status: " + status);

                OrderOfWork order = new OrderOfWork(orderID, requestID, clientID, startDate, endDate, status, numberOfTreesCut, dateOfCut, contractorID);
                listOrders.add(order);
            }
        } finally {
            disconnect();
        }

        return listOrders;
    }


    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    
    public void insert(OrderOfWork order) throws SQLException {
        connect_func();
        String sql = "INSERT INTO OrderOfWork (RequestID, ClientID, StartDate, EndDate, Status, NumberOfTreesCut, DateOfCut, ContractorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, order.getRequestID());
        preparedStatement.setInt(2, order.getClientID());
        preparedStatement.setString(3, order.getStartDate());
        preparedStatement.setString(4, order.getEndDate());
        preparedStatement.setString(5, order.getStatus().name());
        preparedStatement.setInt(6, order.getNumberOfTreesCut());
        preparedStatement.setString(7, order.getDateOfCut());
        preparedStatement.setString(8, order.getContractorID());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
    

    public boolean delete(int OrderID) throws SQLException {
        String sql = "DELETE FROM OrderOfWork WHERE OrderID = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, OrderID);

        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        disconnect();
        return rowDeleted;
    }
    
     
    public boolean updateOrderOfWork(OrderOfWork order) throws SQLException {
    	String sql = "UPDATE OrderOfWork SET ClientID=?, StartDate=?, EndDate=?, Status=?, NumberOfTreesCut=?, DateOfCut=?, ContractorID=? WHERE OrderID=?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, order.getClientID());
        preparedStatement.setString(2, order.getStartDate());
        preparedStatement.setString(3, order.getEndDate());
        preparedStatement.setString(4, order.getStatus().name()); // Assuming Status is an enum
        preparedStatement.setInt(5, order.getNumberOfTreesCut());
        preparedStatement.setString(6, order.getDateOfCut());
        preparedStatement.setString(7, order.getContractorID());
        preparedStatement.setInt(8, order.getOrderID());

        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        disconnect();
        return rowUpdated;
    }

    
    public OrderOfWork getOrderOfWork(int orderID) throws SQLException {
        OrderOfWork order = null;
        String sql = "SELECT * FROM OrderOfWork WHERE OrderID = ?";

        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, orderID);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
        	int requestID = resultSet.getInt("RequestID");
            int clientID = resultSet.getInt("ClientID");
            String startDate = resultSet.getString("StartDate");
            String endDate = resultSet.getString("EndDate");
            String statusString = resultSet.getString("Status");
            OrderOfWork.Status status = OrderOfWork.Status.valueOf(statusString);
            int numberOfTreesCut = resultSet.getInt("NumberOfTreesCut");
            String dateOfCut = resultSet.getString("DateOfCut");
            String contractorID = resultSet.getString("ContractorID");

            order = new OrderOfWork(orderID, requestID, clientID, startDate, endDate, status, numberOfTreesCut, dateOfCut, contractorID);
        }

        resultSet.close();
        preparedStatement.close();
        disconnect(); 

        return order;
    }

   
    public boolean checkOrderID(int OrderID) throws SQLException {
        boolean checks = false;
        String sql = "SELECT * FROM OrderOfWork WHERE OrderID = ?";
        connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, OrderID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            checks = true;
        }

        resultSet.close();
        preparedStatement.close();
        return checks;
    }

  
    public boolean isValid(int OrderID, int ClientID) throws SQLException {
        String sql = "SELECT * FROM OrderOfWork WHERE OrderID = ? AND ClientID = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, OrderID);
        preparedStatement.setInt(2, ClientID);

        ResultSet resultSet = preparedStatement.executeQuery();

        boolean isValid = resultSet.next(); 

        resultSet.close();
        preparedStatement.close();
        return isValid;
    }

    
    public void init() throws SQLException, FileNotFoundException, IOException {
        connect_func();
        statement = (Statement) connect.createStatement();

        String[] INITIAL = {
            "drop database if exists pro;",
            "create database Pro;",
            "use Pro;",
            "drop table if exists OrderOfWork;",
            "CREATE TABLE if not exists OrderOfWork ( " +
                "OrderID INT AUTO_INCREMENT PRIMARY KEY, " +
                "RequestID INT NOT NULL, " +
                "ClientID INT NOT NULL, " +
                "StartDate DATE," +
                "EndDate DATE," +
                "Status ENUM('Started', 'In Progress', 'Pending', 'Completed')," +
                "NumberOfTreesCut INT," +
                "DateOfCut DATE," +
                "ContractorID VARCHAR(255)," +
                "FOREIGN KEY (RequestID) REFERENCES QuoteRequest (RequestID) ON DELETE CASCADE, " +
                "FOREIGN KEY (ClientID) REFERENCES Client (ClientID) ON DELETE CASCADE, " +
                "PRIMARY KEY (OrderID)" +
            ");",
        };

        String[] TUPLES = {
            "(1, 1, '2010-02-02', '2010-02-12', 'Initiated', 1, '2010-02-12', '1'),\n"
            + "(2, 3, '2012-04-04', '2012-04-14', 'Initiated', 3, '2012-04-14', '1'),\n"
            + "(3, 5, '2014-06-06', '2014-06-16', 'Initiated', 5, '2014-06-16', '1'),\n"
            + "(4, 7, '2016-08-08', '2016-08-18', 'Initiated', 7, '2016-08-18', '1'),\n"
            + "(5, 9, '2018-10-10', '2018-10-20', 'Initiated', 9, '2018-10-20', '1'),\n"
            + "(6, 12, '2021-01-13', '2020-02-23', 'Initiated', 12, '2020-02-23', '1'),\n"
            + "(7, 14, '2023-03-03', '2023-03-13', 'Initiated', 14, '2023-03-13', '1'),\n"
            + "(8, 16, '2025-05-05', '2025-05-15', 'Initiated', 16, '2025-05-15', '1');"
        };

        for (int i = 0; i < INITIAL.length; i++)
            statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)
            statement.execute("INSERT INTO OrderOfWork (RequestID, ClientID, StartDate, EndDate, Status, NumberOfTreesCut, DateOfCut, ContractorID) VALUES " + TUPLES[i]);
        disconnect();
    }
}