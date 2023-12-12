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
@WebServlet("/QuoteRequestDAO")
public class QuoteRequestDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public QuoteRequestDAO(){}
	
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
		        String sql = "SELECT * FROM QuoteRequest WHERE RequestID = ?";
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
    
    
    public List<QuoteRequest> listAllRequests() throws SQLException {
        List<QuoteRequest> listRequests = new ArrayList<>();
        String sql = "SELECT * FROM QuoteRequest";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int requestID = resultSet.getInt("RequestID");
            int clientID = resultSet.getInt("ClientID");
            String requestDate = resultSet.getString("RequestDate");
            String note = resultSet.getString("Note");
            int numberOfTrees = resultSet.getInt("NumberOfTrees");
            String size = resultSet.getString("Size");
            double height = resultSet.getDouble("Height");
            String location = resultSet.getString("Location");
            double proximityToHouse = resultSet.getDouble("ProximityToHouse");


            QuoteRequest request = new QuoteRequest(requestID, clientID, requestDate, numberOfTrees , note, size, height, location, proximityToHouse);
            listRequests.add(request);
        }

        resultSet.close();
        disconnect();
        return listRequests;
    }
    

    public List<QuoteRequest> getAgreedQuotesForOneTree() throws SQLException {
        List<QuoteRequest> agreedQuotes = new ArrayList<>();
        connect_func(); 

        try {
            String sql = "SELECT QR.*, CRR.Status AS Status\n"
            		+ "FROM QuoteRequest QR\n"
            		+ "JOIN ClientRespondToQuoteResponse CRR ON QR.RequestID = CRR.ResponseID\n"
            		+ "WHERE QR.NumberOfTrees = 1 AND CRR.Status = 'Accepted';";

            preparedStatement = connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int requestID = resultSet.getInt("RequestID");
                int clientID = resultSet.getInt("ClientID");
                String requestDate = resultSet.getString("RequestDate");
                String note = resultSet.getString("Note");
                int numberOfTrees = resultSet.getInt("NumberOfTrees");
                String size = resultSet.getString("Size");
                double height = resultSet.getDouble("Height");
                String location = resultSet.getString("Location");
                float proximityToHouse = resultSet.getFloat("ProximityToHouse");
                
              

                QuoteRequest quote = new QuoteRequest(requestID, clientID, requestDate, numberOfTrees, note, size, height, location, proximityToHouse);

                agreedQuotes.add(quote);
            }
        } finally {
            disconnect(); 
        }

        return agreedQuotes;
    }


    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    
    public List<QuoteRequest> getHighestTreesCutByContractor() throws SQLException {
        List<QuoteRequest> highestTrees = new ArrayList<>();
        connect_func(); 

        try {
            String sql =
           " SELECT QR.RequestID, QR.ClientID, QR.RequestDate, QR.NumberOfTrees, QR.Size, QR.Height " +
           " FROM QuoteRequest QR " +
           " JOIN OrderOfWork OW ON QR.RequestID = OW.RequestID " +
           " WHERE OW.ContractorID = 1 " +
           " ORDER BY QR.Height DESC " +
           " LIMIT 1 ";

            preparedStatement = connect.prepareStatement(sql);
            

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int requestID = resultSet.getInt("RequestID");
                int clientID = resultSet.getInt("ClientID");
                String requestDate = resultSet.getString("RequestDate");
                int numberOfTrees = resultSet.getInt("NumberOfTrees");
                String size = resultSet.getString("Size");
                double height = resultSet.getDouble("Height");
               

                QuoteRequest highestTree = new QuoteRequest(requestID, clientID, requestDate, numberOfTrees, size, height);
                highestTrees.add(highestTree);
            }
        } finally {
            disconnect();
        }

        return highestTrees;
    }


    public void insert(QuoteRequest request) throws SQLException {
        connect_func("john", "Pass1550");
        String sql = "INSERT INTO QuoteRequest (ClientID, RequestDate,  Note, NumberOfTrees, Size, Height, Location, ProximityToHouse) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        
        preparedStatement.setInt(1, request.getClientID());
        preparedStatement.setString(2, request.getRequestDate());
        preparedStatement.setString(3, request.getNote());
        preparedStatement.setInt(4, request.getNumberOfTrees()); 
        preparedStatement.setString(5, request.getSize());
        preparedStatement.setDouble(6, request.getHeight());
        preparedStatement.setString(7, request.getLocation());
        preparedStatement.setDouble(8, request.getProximityToHouse());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }

    
    public boolean delete(int RequestID) throws SQLException {
        String sql = "DELETE FROM QuoteRequest WHERE RequestID = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, RequestID);

        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;
    }

     
    public boolean updateQuoteRequest(QuoteRequest quoteRequest) throws SQLException {
        String sql = "UPDATE QuoteRequest SET ClientID=?, RequestDate=?,  Note=?, NumberOfTrees=?, Size=?, Height=?, Location=?, ProximityToHouse=? WHERE RequestID=?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, quoteRequest.getClientID());       
        preparedStatement.setString(2, quoteRequest.getRequestDate());
        preparedStatement.setString(3, quoteRequest.getNote());
        preparedStatement.setInt(4, quoteRequest.getNumberOfTrees()); 
        preparedStatement.setString(5, quoteRequest.getSize());
        preparedStatement.setDouble(6, quoteRequest.getHeight());
        preparedStatement.setString(7, quoteRequest.getLocation());
        preparedStatement.setDouble(8, quoteRequest.getProximityToHouse());
        preparedStatement.setInt(9, quoteRequest.getRequestID());

        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        disconnect();
        return rowUpdated;
    }

    
    public QuoteRequest getQuoteRequest(int requestID) throws SQLException {
        QuoteRequest quoteRequest = null;
        String sql = "SELECT * FROM QuoteRequest WHERE RequestID = ?";

        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, requestID);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int clientID = resultSet.getInt("ClientID");            
            String requestDate = resultSet.getString("RequestDate");
            String note = resultSet.getString("Note");
            int numberOfTrees = resultSet.getInt("NumberOfTrees");                     
            String size = resultSet.getString("Size");
            double height = resultSet.getDouble("Height");
            String location = resultSet.getString("Location");
            double proximityToHouse = resultSet.getDouble("ProximityToHouse");

            quoteRequest = new QuoteRequest(requestID, clientID, requestDate, numberOfTrees, note, size, height, location, proximityToHouse);
        }

        resultSet.close();
        preparedStatement.close();
        disconnect(); 

        return quoteRequest;
    }

    
    public boolean checkRequestID(int RequestID) throws SQLException {
        boolean checks = false;
        String sql = "SELECT * FROM QuoteRequest WHERE RequestID = ?";
        connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, RequestID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            checks = true;
        }

        resultSet.close();
        preparedStatement.close();
        return checks;
    }
    
   
    public boolean isValid(int RequestID, int ClientID) throws SQLException {
        String sql = "SELECT * FROM QuoteRequest WHERE RequestID = ? AND ClientID = ?";
        connect_func();

        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, RequestID);
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
            "drop table if exists QuoteRequest;",
            "CREATE TABLE if not exists QuoteRequest (\n"
            + "    RequestID INT AUTO_INCREMENT PRIMARY KEY,\n"
            + "    ClientID INT,   \n"
            + "    RequestDate DATE,\n"
            + "    Note VARCHAR(200),\n"
            + "	NumberOfTrees INT,\n"
            + "    Size VARCHAR(10), \n"
            + "    Height DECIMAL(3, 1), \n"
            + "    Location VARCHAR(100),\n"
            + "    ProximityToHouse FLOAT (20),\n"
            + "    UNIQUE (RequestID),\n"
            + "    FOREIGN KEY (ClientID) REFERENCES Client (ClientID)    \n"
            + ");"
        };

        String[] TUPLES = {
        	    "(1,'2010-01-01', 'note1', 1, 10, 10.1, 'frontyard', 16.9),\n"
        	    + "(2,'2011-02-02', 'note2', 2, 6, 11.2, 'backyard', 15.8),\n"
        	    + "(3,'2012-03-03', 'note3', 3, 5, 13.3, 'frontyard', 14.7),\n"
        	    + "(4,'2013-04-04', 'note4', 4, 8, 9.4, 'backyard', 7.6),\n"
        	    + "(5,'2014-05-05', 'note5', 5, 9, 8.5, 'frontyard', 5.5),\n"
        	    + "(6,'2015-06-06', 'note6', 6, 4, 7.6, 'backyard', 9.4),\n"
        	    + "(7,'2016-07-07', 'note7', 7, 4, 14.7, 'frontyard', 13.3),\n"
        	    + "(8,'2017-08-08', 'note8', 8, 9, 15.8, 'backyard', 11.2),\n"
        	    + "(9,'2018-09-09', 'note9', 9, 12, 16.9, 'frontyard', 10.1),\n"
        	    + "(10, '2019-10-10', 'note10', 10, 13, 17.8, 'backyard', 9.8),\n"
        	    + "(11, '2020-11-11', 'note11', 11, 14, 18.8, 'backyard', 10.8),\n"
        	    + "(12, '2021-12-12', 'note12', 12, 15, 19.8, 'backyard', 11.8),\n"
        	    + "(13, '2022-01-01', 'note13', 13, 16, 20.8, 'backyard', 12.8),\n"
        	    + "(14, '2023-02-02', 'note14', 14, 17, 21.8, 'backyard', 13.8),\n"
        	    + "(15, '2024-03-03', 'note15', 15, 18, 22.8, 'backyard', 14.8),\n"
        	    + "(16, '2025-04-14', 'note16', 16, 19, 23.8, 'backyard', 15.8);"
        	};


        for (int i = 0; i < INITIAL.length; i++)
            statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)
            statement.execute("INSERT INTO QuoteRequest (ClientID, RequestDate, Note, NumberOfTrees, Size, Height, Location, ProximityToHouse) VALUES " + TUPLES[i]);
        disconnect();
    }
}