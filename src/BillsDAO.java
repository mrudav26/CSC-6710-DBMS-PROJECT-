import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;

import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Connect
 */

@WebServlet("/BillsDAO")
public class BillsDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public BillsDAO() {
	}

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
			connect = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Pro?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=Pass1550");
			System.out.println(connect);
		}
	}

	
	public boolean database_login(String email, String password) throws SQLException {
		try {
			connect_func("john", "Pass1550");
			String sql = "select * from Bills where BillID = ?";
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
			connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pro?"
					+ "useSSL=false&user=" + username + "&password=" + password);
			System.out.println(connect);
		}
	}
	

	public List<Bills> listAllBills() throws SQLException {
	    List<Bills> listBills = new ArrayList<>();
	    String sql = "SELECT * FROM Bills";
	    connect_func();
	    statement = (Statement) connect.createStatement();
	    ResultSet resultSet = statement.executeQuery(sql);

	    while (resultSet.next()) {
	        int billId = resultSet.getInt("BillID");
	        int contractorID = resultSet.getInt("ContractorID");
	        int orderID = resultSet.getInt("OrderID");       
	        String generatedDate = resultSet.getString("GeneratedDate");
	        double amount = resultSet.getDouble("Amount");
	        String billStatusString = resultSet.getString("BillStatus");
	        Bills.BillStatus billStatus = Bills.BillStatus.valueOf(billStatusString);

	        String paymentStatusString = resultSet.getString("PaymentStatus");
	        Bills.PaymentStatus paymentStatus = Bills.PaymentStatus.valueOf(paymentStatusString);

	        Bills bills = new Bills(billId, amount, generatedDate, billStatus, paymentStatus, orderID, contractorID);
	        listBills.add(bills);
	    }

	    resultSet.close();
	    disconnect();
	    return listBills;
	}


	public List<Bills> getOverdueBillsWithDetails() throws SQLException {
	    List<Bills> overdueBills = new ArrayList<>();
	    connect_func(); 

	    try {
	    	String sql = "SELECT B.BillID, B.GeneratedDate, B.BillStatus, B.PaymentStatus, B.OrderID, OW.ClientID " +
	                "FROM Bills B " +
	                "JOIN OrderOfWork OW ON B.OrderID = OW.OrderID " +
	                "WHERE B.PaymentStatus = 'Unpaid' AND DATEDIFF(NOW(), B.GeneratedDate) > 7";

	        preparedStatement = connect.prepareStatement(sql);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int billId = resultSet.getInt("BillID");
	            String generatedDate = resultSet.getString("GeneratedDate");
	            String billStatusString = resultSet.getString("BillStatus");
		        Bills.BillStatus billStatus = Bills.BillStatus.valueOf(billStatusString);
		        String paymentStatusString = resultSet.getString("PaymentStatus");
		        Bills.PaymentStatus paymentStatus = Bills.PaymentStatus.valueOf(paymentStatusString);	           
	            int orderID = resultSet.getInt("OrderID");
	            int clientID = resultSet.getInt("ClientID");

	            Bills bill = new Bills (billId, generatedDate, billStatus, paymentStatus, orderID, clientID);
	            overdueBills.add(bill);
	        }
	    } finally {
	        disconnect(); 
	    }

	    return overdueBills;
	}
	

	
	private void disconnect() {
		// TODO Auto-generated method stub

	}
	

	public void insert(Bills bills) throws SQLException {
		connect_func("john", "Pass1550");
		String sql = "INSERT INTO Bills ( BillId, ContractorID, OrderID, Amount, GeneratedDate, BillStatus, PaymentStatus ) VALUES (?, ?, ?, ?, ?, ?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		preparedStatement.setInt(1, bills.getBillId());
		preparedStatement.setInt(2, bills.getContractorID());
		preparedStatement.setInt(3, bills.getOrderID());
		preparedStatement.setDouble(3, bills.getAmount());
		preparedStatement.setString(4, bills.getGeneratedDate());
		preparedStatement.setString(5, bills.getBillStatus().toString());
		preparedStatement.setString(6, bills.getPaymentStatus().toString());
		

		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	

	public boolean deleteById(int BillID) throws SQLException {
		String sql = "DELETE FROM Bills WHERE BillID = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, BillID);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		return rowDeleted;
	}

	
	public boolean update(Bills bills) throws SQLException {
		String sql = "UPDATE Bills SET BillID=?, ContractorID=?, OrderID=?, Amount=?, GeneratedDate=?, billStatus=?, paymentStatus=? WHERE BillID=?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		preparedStatement.setInt(1, bills.getBillId());
		preparedStatement.setInt(2, bills.getContractorID());
		preparedStatement.setInt(3, bills.getOrderID());
		preparedStatement.setDouble(3, bills.getAmount());
		preparedStatement.setString(4, bills.getGeneratedDate());
		preparedStatement.setString(5, bills.getBillStatus().toString());
		preparedStatement.setString(6, bills.getPaymentStatus().toString());

		boolean rowUpdated = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		return rowUpdated;
	}
	

	public Bills getBill(int BillID) throws SQLException {
		Bills bills = null;
		String sql = "SELECT * FROM Bills WHERE BillID = ?";

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, BillID);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			int billId = resultSet.getInt("BillId");
			int contractorID = resultSet.getInt("ContractorID");
			int orderID = resultSet.getInt("OrderID");
			String generatedDate = resultSet.getString("GeneratedDate");
			double amount = resultSet.getDouble("Amount");
			String billStatusString = resultSet.getString("BillStatus");
			Bills.BillStatus billStatus = Bills.BillStatus.valueOf(billStatusString);
			String paymentStatusString = resultSet.getString("PaymentStatus");
			Bills.PaymentStatus paymentStatus = Bills.PaymentStatus.valueOf(paymentStatusString);

			bills = new Bills(billId, amount, generatedDate, Bills.BillStatus.valueOf(billStatusString),
					Bills.PaymentStatus.valueOf(paymentStatusString), orderID, contractorID);

		}

		resultSet.close();
		preparedStatement.close();
		disconnect();

		return bills;
	}
	

	public boolean checkBillID(int BillID) throws SQLException {
		boolean checks = false;
		String sql = "SELECT * FROM Bills WHERE BillID = ?";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, BillID);
		ResultSet resultSet = preparedStatement.executeQuery();

		System.out.println(checks);

		if (resultSet.next()) {
			checks = true;
		}

		System.out.println(checks);
		return checks;
	}
	

	public boolean isValid(int BillID, int contractorID) throws SQLException {
		String sql = "SELECT * FROM Bills WHERE BillID = ? AND ContractorID = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, BillID);
		preparedStatement.setInt(2, contractorID);

		ResultSet resultSet = preparedStatement.executeQuery();
		boolean isValid = resultSet.next(); // Check if a matching record is found

		resultSet.close();
		preparedStatement.close();
		disconnect();

		return isValid;
	}
	

	public void init() throws SQLException, FileNotFoundException, IOException {
	    connect_func();
	    statement = (Statement) connect.createStatement();

	    String[] INITIAL = {
	            "drop database if exists pro; ",
	            "create database Pro; ",
	            "use Pro; ",
	            "drop table if exists Bills; ",
	            "CREATE TABLE if not exists Bills ( BillID INT AUTO_INCREMENT PRIMARY KEY,\n"
	            + "    ContractorID INT,\n"
	            + "    OrderID INT,  \n"
	            + "    Amount DECIMAL(10, 2),\n"
	            + "    GeneratedDate DATE,\n"
	            + "    BillStatus ENUM('Issued', 'Pending'),\n"
	            + "    PaymentStatus ENUM('Paid', 'Unpaid', 'Overdue'),\n"
	            + "    FOREIGN KEY (OrderID) REFERENCES OrderOfWork(OrderID) ON DELETE CASCADE,\n"
	            + "    FOREIGN KEY (ContractorID) REFERENCES Contractor(ContractorID) ON DELETE CASCADE); "
	    };


	    String[] TUPLES = {
	            "INSERT INTO Bills ( ContractorID, OrderID, Amount, GeneratedDate, BillStatus, PaymentStatus) "
	                    + "VALUES (1, 1, 100, '2010-02-12', 'Issued', 'Paid'),\n"
	                    + "(1, 2, 300, '2012-04-14', 'Issued', 'Paid'),\n"
	                    + "(1, 3, 500, '2014-06-16', 'Issued', 'Overdue'),\n"
	                    + "(1, 4, 700, '2016-08-18', 'Issued', 'Paid'),\n"
	                    + "(1, 5, 900, '2018-10-20', 'Issued', 'Overdue'),\n"
	                    + "(1, 6, 1200, '2020-02-23', 'Issued', 'Paid'),\n"
	                    + "(1, 7, 1400, '2023-03-13', 'Issued', 'Unpaid'),\n"
	                    + "(1, 8, 1600, '2025-05-15', 'Issued', 'Paid');"
	    };

	    for (int i = 0; i < INITIAL.length; i++) {
	        statement.execute(INITIAL[i]);
	    }

	    for (int i = 0; i < TUPLES.length; i++) {
	        statement.execute(TUPLES[i]);
	    }

	    disconnect();
	}
}