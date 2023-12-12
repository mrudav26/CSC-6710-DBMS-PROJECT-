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

import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Connect
 */

@WebServlet("/ContractorRespondToClientResponseDAO")
public class ContractorRespondToClientResponseDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public ContractorRespondToClientResponseDAO() {
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
			connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Pro?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=Pass1550");
			System.out.println(connect);
		}
	}
	

	public boolean database_login(String email, String password) throws SQLException {
		try {
			connect_func("john", "Pass1550");
			String sql = "select * from ContractorRespondToClientResponseDAO where ContractorResponseID = ?";
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
	

	public List<ContractorRespondToClientResponse> listAllClientResponse() throws SQLException {
		List<ContractorRespondToClientResponse> listClientResponses = new ArrayList<ContractorRespondToClientResponse>();
		String sql = "SELECT * FROM ContractorRespondToClientResponse";
		connect_func();
		statement = (Statement) connect.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int contractorResponseID = resultSet.getInt("ContractorResponseID");
			int contractorID = resultSet.getInt("ContractorID");
			int clientResponseID = resultSet.getInt("ClientResponseID");
			String responseDate = resultSet.getString("ResponseDate");
			String note = resultSet.getString("Note");
			double modifiedPrice = resultSet.getDouble("ModifiedPrice");
			String modifiedWorkPeriodFrom = resultSet.getString("ModifiedWorkPeriodFrom");
			String modifiedWorkPeriodTo = resultSet.getString("ModifiedWorkPeriodTo");		
			String statusString = resultSet.getString("Status");
			ContractorRespondToClientResponse.Status status = ContractorRespondToClientResponse.Status.valueOf(statusString);

			ContractorRespondToClientResponse contractorResponse = new ContractorRespondToClientResponse(contractorResponseID, contractorID, clientResponseID, responseDate, status, note, modifiedPrice,modifiedWorkPeriodFrom, modifiedWorkPeriodTo);
			listClientResponses.add(contractorResponse);
		}

		resultSet.close();
		disconnect();
		return listClientResponses;
	}
	

	private void disconnect() {
		// TODO Auto-generated method stub

	}
	

	public void insert(ContractorRespondToClientResponse contractorResponse) throws SQLException {
		connect_func("john", "Pass1550");
		String sql = "INSERT INTO ContractorRespondToClientResponse ( contractorID, clientResponseID, responseDate, status, note, modifiedPrice,modifiedWorkPeriodFrom, modifiedWorkPeriodTo) VALUES (?, ?, ?, ?, ?, ?,?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		preparedStatement.setInt(1, contractorResponse.getContractorID());
		preparedStatement.setInt(2, contractorResponse.getClientResponseID());
		preparedStatement.setString(3, contractorResponse.getResponseDate());
		preparedStatement.setString(4, contractorResponse.getStatus().toString());
		preparedStatement.setString(5, contractorResponse.getNote());
		preparedStatement.setDouble(6, contractorResponse.getModifiedPrice());
		preparedStatement.setString(7, contractorResponse.getModifiedWorkPeriodFrom());
		preparedStatement.setString(8, contractorResponse.getModifiedWorkPeriodTo());

		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	

	public boolean deleteById(int ContractorResponseID) throws SQLException {
		String sql = "DELETE FROM ContractorRespondToClientResponse WHERE ContractorResponseID = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, ContractorResponseID);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		return rowDeleted;
	}
	

	public boolean update(ContractorRespondToClientResponse contractorResponse) throws SQLException {
		String sql = "UPDATE ContractorRespondToClientResponse SET ContractorResponseID=?, ContractorID=?, ClientResponseID=?, ResponseDate=?, Status=?, Note=?,ModifiedPrice=?,ModifiedWorkPeriodFrom=?,ModifiedWorkPeriodTo=? WHERE ResponseID=?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		preparedStatement.setInt(1, contractorResponse.getContractorResponseID());
		preparedStatement.setInt(2, contractorResponse.getContractorID());
		preparedStatement.setInt(3, contractorResponse.getClientResponseID());
		preparedStatement.setString(4, contractorResponse.getResponseDate());
		preparedStatement.setString(5, contractorResponse.getStatus().toString());
		preparedStatement.setString(6, contractorResponse.getNote());
		preparedStatement.setDouble(7, contractorResponse.getModifiedPrice());
		preparedStatement.setString(8, contractorResponse.getModifiedWorkPeriodFrom());
		preparedStatement.setString(9, contractorResponse.getModifiedWorkPeriodTo());

		boolean rowUpdated = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		return rowUpdated;
	}
	

	public ContractorRespondToClientResponse getContractorRespondToClientResponse(int contractorResponseID)
			throws SQLException {
		ContractorRespondToClientResponse contractorResponse = null;
		String sql = "SELECT * FROM ContractorRespondToClientResponse WHERE ContractorResponseID = ?";

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, contractorResponseID);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			
			int contractorID = resultSet.getInt("ContractorID");
			int clientResponseID = resultSet.getInt("ClientResponseID");
			String responseDate = resultSet.getString("ResponseDate");
			String statusString = resultSet.getString("Status");
			String note = resultSet.getString("Note");
			double modifiedPrice = resultSet.getDouble("ModifiedPrice");
			String modifiedWorkPeriodFrom = resultSet.getString("ModifiedWorkPeriodFrom");
			String modifiedWorkPeriodTo = resultSet.getString("ModifiedWorkPeriodTo");

			contractorResponse = new ContractorRespondToClientResponse(contractorResponseID, contractorID,clientResponseID, responseDate, ContractorRespondToClientResponse.Status.valueOf(statusString),
					note, modifiedPrice, modifiedWorkPeriodFrom, modifiedWorkPeriodTo);
		}

		resultSet.close();
		preparedStatement.close();
		disconnect();

		return contractorResponse;
	}
	

	public boolean checkContractorResponseID(int ContractorResponseID) throws SQLException {
		boolean checks = false;
		String sql = "SELECT * FROM ContractorRespondToClientResponse WHERE ContractorResponseID = ?";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, ContractorResponseID);
		ResultSet resultSet = preparedStatement.executeQuery();

		System.out.println(checks);

		if (resultSet.next()) {
			checks = true;
		}

		System.out.println(checks);
		return checks;
	}
	

	public boolean isValid(int ContractorResponseID, int contractorID) throws SQLException {
		String sql = "SELECT * FROM ContractorRespondToClientResponse WHERE ContractorResponseID = ? AND ContractorID = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, ContractorResponseID);
		preparedStatement.setInt(2, contractorID);

		ResultSet resultSet = preparedStatement.executeQuery();
		boolean isValid = resultSet.next(); 

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
				"create database Pro; ", "use Pro; ",
				"drop table if exists ContractorRespondToClientResponse; ",
				"CREATE TABLE if not exists ContractorRespondToClientResponse ( ContractorResponseID INT AUTO_INCREMENT PRIMARY KEY,\n"
				+ "    ContractorID INT,\n"
				+ "    ClientResponseID INT,\n"
				+ "    Status ENUM('Accepted', 'Rejected', 'Pending', 'RequestAgain'),\n"
				+ "    ResponseDate DATE,\n"
				+ "    Note VARCHAR(255),\n"
				+ "    ModifiedPrice DOUBLE,\n"
				+ "    ModifiedWorkPeriodFrom DATE,\n"
				+ "    ModifiedWorkPeriodTo DATE,  \n"
				+ "    FOREIGN KEY (ContractorID) REFERENCES Contractor(ContractorID),\n"
				+ "    FOREIGN KEY (ClientResponseID) REFERENCES ClientRespondToQuoteResponse(ClientResponseID)); " };

		String[] TUPLES = {
				"INSERT INTO ContractorRespondToClientResponse ( ContractorID, ClientResponseID, ResponseDate, Status, Note,ModifiedPrice,ModifiedWorkPeriodFrom,ModifiedWorkPeriodTo) "
						+ "VALUES (1, 1, 'Accepted', '2010-01-03', 'hi hello', 100, '2010-02-02', '2010-02-12'),\n"
						+ "(1, 3, 'Accepted', '2012-03-05', 'hi hi', 300, '2012-04-04', '2012-04-14'),\n"
						+ "(1, 4, 'Rejected', '2013-04-06', 'bye bye', 350, '2013-05-05', '2013-05-15'), \n"
						+ "(1, 5, 'Accepted', '2014-05-07', 'hi hello', 500, '2014-06-06', '2014-06-16'), \n"
						+ "(1, 7, 'Accepted', '2016-07-09', 'hi hello', 700, '2016-08-08', '2016-08-18'), \n"
						+ "(1, 8, 'Rejected', '2017-08-10', 'bye bye', 750, '2017-09-09', '2017-09-19'), \n"
						+ "(1, 9, 'Accepted', '2018-09-11', 'hi hello', 900, '2018-10-10', '2018-10-20'),\n"
						+ "(1, 12, 'Accepted', '2021-12-14', 'hi hi', 1200, '2021-01-13', '2020-02-23'),\n"
						+ "(1, 14, 'Accepted', '2023-02-16', 'hi hello', 1400, '2023-03-03', '2023-03-13'),\n"
						+ "(1, 15, 'Rejected', '2021-12-14', 'hi hi', 1500, '2024-04-04', '2024-04-14'),\n"
						+ "(1, 16, 'Accepted', '2021-12-14', 'bye bye', 1600, '2025-05-05', '2025-05-15');" };

		for (int i = 0; i < INITIAL.length; i++) {
			statement.execute(INITIAL[i]);
		}

		for (int i = 0; i < TUPLES.length; i++) {
			statement.execute(TUPLES[i]);
		}

		disconnect();
	}
}