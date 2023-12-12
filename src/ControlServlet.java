import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private administratorDAO administratorDAO = new administratorDAO();
    private String currentAdministrator;
    private clientDAO clientDAO = new clientDAO();
    private String currentClient;
    private contractorDAO contractorDAO = new contractorDAO();
    private String currentContractor;
    private QuoteRequestDAO QuoteRequestDAO = new QuoteRequestDAO(); 
    private String currentQuoteRequest;  
    private QuoteResponseDAO QuoteResponseDAO = new QuoteResponseDAO(); 
    private String currentQuoteResponse;
    private ClientRespondToQuoteResponseDAO ClientRespondToQuoteResponseDAO = new ClientRespondToQuoteResponseDAO(); 
    private String currentClientRespondToQuoteResponse;
    private ContractorRespondToClientResponseDAO ContractorRespondToClientResponseDAO = new ContractorRespondToClientResponseDAO(); 
    private String currentContractorRespondToClientResponseDAO;
    private OrderOfWorkDAO OrderOfWorkDAO = new OrderOfWorkDAO();
    private String currentOrder;
    private BillsDAO BillsDAO = new BillsDAO();
    private String currentBills;
    private ResponseToBillDAO ResponseToBillDAO = new ResponseToBillDAO();
    private String currentResponseToBill;
    private HttpSession session = null;

    public ControlServlet() {

    }

    public void init() {
        clientDAO = new clientDAO();
        setCurrentClient("");
        
        administratorDAO = new administratorDAO();
        setCurrentAdministrator("");
        
        contractorDAO = new contractorDAO();
        setCurrentContractor("");
        
        QuoteRequestDAO = new  QuoteRequestDAO();
        setCurrentQuoteRequest("");
        
        QuoteResponseDAO = new  QuoteResponseDAO();
        setCurrentQuoteResponse("");
        
        ClientRespondToQuoteResponseDAO = new  ClientRespondToQuoteResponseDAO();
        setCurrentClientRespondToQuoteResponse ("");
        
        ContractorRespondToClientResponseDAO = new ContractorRespondToClientResponseDAO();
        setCurrentContractorRespondToClientResponse ("");
        
       
    }   
      
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
	

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);

        try {
            switch (action) {
                case "/login":
                    login(request, response);
                    break;
                case "/register":
                    register(request, response);
                    break;
                case "/initialize":
                    try {

                        List<client> clients = clientDAO.listAllClients();
                        request.setAttribute("listClient", clients);
                        System.out.println("Client data successfully initialized!");


                        List<administrator> Admin = administratorDAO.listAllAdministrator();
                        request.setAttribute("listAdministrator", Admin);
                        System.out.println("Administrator data successfully initialized!");
                        
  
                        List<contractor> contractor = contractorDAO.listAllContractor();
                        request.setAttribute("listContractor", contractor );
                        System.out.println("Contractor data successfully initialized!");

                                            
                        request.getRequestDispatcher("rootView.jsp").forward(request, response);

                    } catch (SQLException e) {
                        e.printStackTrace();
                  
                    }
                    break;

                case "/root":
                    request.getRequestDispatcher("rootView.jsp").forward(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                
                case "/RequestQuote":
                    requestQuote(request, response);
                    request.getRequestDispatcher("QuoteRequest.jsp").forward(request, response);
                    break;
                   
                case "/ResponseQuote":
                	responseQuote(request, response);
                    request.getRequestDispatcher("QuoteResponse.jsp").forward(request, response);
                    break;
                    
                case "/ClientResponse":
                    clientResponse(request, response);
                    request.getRequestDispatcher("ClientRespondToQuoteResponse.jsp").forward(request, response);
                    break;
                    
                case "/ContractorResponse":
                    contractorResponse(request, response); 
                    request.getRequestDispatcher("ContractorRespondToClientResponse.jsp").forward(request, response);
                    break;
                    
                case "/ClientList":            	   
					  List<client> clients = clientDAO.listAllClients();
					  request.setAttribute("listClient", clients);
					  request.getRequestDispatcher("ClientList.jsp").forward(request, response);
					  System.out.println("Client data successfully initialized!");		
					  break;

                case "/QuoteRequest":                   
                	List<QuoteRequest> requests = QuoteRequestDAO.listAllRequests();
                	 request.setAttribute("listRequests", requests); 					 
                    request.getRequestDispatcher("QuoteRequest.jsp").forward(request, response);
                    System.out.println("QuoteRequest data successfully initialized!");
                    break;  
                                            
                case "/QuoteResponse":                   
                	List<QuoteResponse> responses = QuoteResponseDAO.listAllResponses();
                    request.setAttribute("listResponses", responses ); 					 
                    request.getRequestDispatcher("QuoteResponse.jsp").forward(request, response);
                    System.out.println("QuoteResponse data successfully initialized!");
                    break;
                                                           
                case "/ClientRespondToQuoteResponse":
                    List<ClientRespondToQuoteResponse> clientResponses = ClientRespondToQuoteResponseDAO.listAllQuoteResponse();
                    request.setAttribute("listQuoteResponses", clientResponses);                  
                    
                    request.getRequestDispatcher("ClientRespondToQuoteResponse.jsp").forward(request, response);                    
                    System.out.println("ClientResponse data successfully initialized!");
                    break;
                    
                case "/ContractorRespondToClientResponse":                   
                      List<ContractorRespondToClientResponse> contractorResponses = ContractorRespondToClientResponseDAO.listAllClientResponse();                     
                      request.setAttribute("listClientResponses", contractorResponses);                      
                      request.getRequestDispatcher("ContractorRespondToClientResponse.jsp").forward(request, response);                      
                      System.out.println("ContractorResponse data successfully initialized!");                  
                       break;                      
                                      
                case "/OrderOfWork":
                	List<OrderOfWork> listOrders = OrderOfWorkDAO.listAllOrders();
                    request.setAttribute("listOrders", listOrders);
                    request.getRequestDispatcher("OrderOfWork.jsp").forward(request, response);
                    System.out.println("List of all orders successfully initialized!");       
                    break;
                    
                case "/Bills":
                    List<Bills> bills = BillsDAO.listAllBills();
                    request.setAttribute("listBills", bills);
                    request.getRequestDispatcher("Bills.jsp").forward(request, response);
                    System.out.println("Bills data successfully initialized!");
                    break;      
                    
                case "/ResponseToBill":
                    List<ResponseToBill> listResponseToBills = ResponseToBillDAO.listAllResponseToBills();
                    request.setAttribute("listResponseToBills", listResponseToBills);
                    request.getRequestDispatcher("ResponseToBill.jsp").forward(request, response);
                    System.out.println("ResponseToBills data successfully initialized!");
                    break;
                                                     
                case "/ClientsWithCompletedTreeCutting":
                    
                    List<client> clientsWithCompletedTreeCutting = clientDAO.getClientsWithCompletedTreeCutting();
                    request.setAttribute("clientsWithCompletedTreeCutting", clientsWithCompletedTreeCutting);
                    request.getRequestDispatcher("ClientsWithCompletedTreeCutting.jsp").forward(request, response);
                    System.out.println("Clients with completed tree cutting data successfully initialized!");       
                    break;
                    
                case "/BigClients":
                    List<client> bigClients = clientDAO.getBigClients();
                    request.setAttribute("bigClients", bigClients);
                    request.getRequestDispatcher("BigClients.jsp").forward(request, response);
                    System.out.println("BigClients data successfully initialized!");
                    break;
                                        
                case "/EasyClients":
                    List<client> easyClients = clientDAO.getEasyClients();
                    request.setAttribute("easyClients", easyClients);
                    request.getRequestDispatcher("EasyClients.jsp").forward(request, response);
                    System.out.println("EasyClients data successfully initialized!");
                    break;
                    
                case "/getAgreedQuotesForOneTree":
                    List<QuoteRequest> agreedQuotes = QuoteRequestDAO.getAgreedQuotesForOneTree();
                    request.setAttribute("listRequests", agreedQuotes);
                    request.getRequestDispatcher("OneTreeQuotes.jsp").forward(request, response);
                    System.out.println("Agreed quotes data successfully initialized!");
                    break;
                    
                case "/ClientsWithNoOrdersOfWork":
                    List<client> clientsWithNoOrders = clientDAO.getClientsWithNoOrdersOfWork();
                    request.setAttribute("clientsWithNoOrders", clientsWithNoOrders);
                    request.getRequestDispatcher("ProspectiveClients.jsp").forward(request, response);
                    System.out.println("Clients with no orders of work data successfully initialized!");
                    break;
                    
                case "/getHighestTreesCutByContractor":                    
                    List<QuoteRequest> highestTrees = QuoteRequestDAO.getHighestTreesCutByContractor();
                    request.setAttribute("highestTrees", highestTrees);
                    request.getRequestDispatcher("HighestTrees.jsp").forward(request, response);
                    System.out.println("Highest trees cut by contractor data successfully initialized!");
                    break;

                case "/getOverdueBills":
                    List<Bills> overdueBills = BillsDAO.getOverdueBillsWithDetails();
                    request.setAttribute("overdueBills", overdueBills);
                    request.getRequestDispatcher("OverdueBills.jsp").forward(request, response);
                    System.out.println("Overdue bills data successfully initialized!");
                    break;
                    
                case "/getBadClientsWithOverdueBills":
                    List<client> badClients = clientDAO.getBadClientsWithOverdueBills();
                    request.setAttribute("badClients", badClients);
                    request.getRequestDispatcher("BadClients.jsp").forward(request, response);
                    System.out.println("Bad clients with overdue bills data successfully initialized!");
                    break;

                case "/getGoodClients":
                  
                        List<client> goodClients = clientDAO.getGoodClients();
                        request.setAttribute("goodClients", goodClients);
                        request.getRequestDispatcher("GoodClients.jsp").forward(request, response);
                        System.out.println("Good clients data successfully initialized!");
                         break;

                case "/getClientsStatistics":
                   
                        List<client> clientStatisticsList = clientDAO.getClientsStatistics();
                        request.setAttribute("clientStatisticsList", clientStatisticsList);
                        request.getRequestDispatcher("clientStatistics.jsp").forward(request, response);
                        System.out.println("Client statistics data successfully initialized!");
               
                    break;              	 
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

               
    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
        System.out.println("root view");
        
        request.setAttribute("listClient", clientDAO.listAllClients());

        request.setAttribute("listAdministrator", administratorDAO.listAllAdministrator());
        
        request.setAttribute("listContractor", contractorDAO.listAllContractor());
        
        request.getRequestDispatcher("rootView.jsp").forward(request, response);
    }
  
        
    private void QuoteRequest(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
    	List<QuoteRequest> requests = QuoteRequestDAO.listAllRequests();
    	System.out.println("Number of requests retrieved: " + requests.size());    	
        request.setAttribute("listRequests", QuoteRequestDAO.listAllRequests());  
        request.getRequestDispatcher("QuoteRequest.jsp").forward(request, response); 
    }
    
    private void QuoteResponse(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
    	List<QuoteResponse> responses = QuoteResponseDAO.listAllResponses();
    	System.out.println("Number of responses retrieved: " + responses.size());  
    	
        request.setAttribute("listResponses", QuoteResponseDAO.listAllResponses());  
        request.getRequestDispatcher("QuoteResponse.jsp").forward(request, response); 
    }

  
    private void ClientRespondToQuoteResponse(HttpServletRequest request, HttpServletResponse response, String view)throws ServletException, IOException, SQLException {
        List<ClientRespondToQuoteResponse> quoteResponses = ClientRespondToQuoteResponseDAO.listAllQuoteResponse();
        System.out.println("Number of quote responses retrieved: " + quoteResponses.size());  

        request.setAttribute("listQuoteResponses", quoteResponses);  
        request.getRequestDispatcher("ClientRespondToQuoteResponse.jsp").forward(request, response); 
    }
    

    public class UserRole {
        public static final String CLIENT = "Client";
        public static final String ADMINISTRATOR = "Administrator";
        public static final String CONTRACTOR = "Contractor";
    }
    

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String rolefromjsppage = request.getParameter("role");
        System.out.println(rolefromjsppage);


        if (rolefromjsppage.equals("Administrator")) {            
            response.sendRedirect("administrator.jsp");
        } else if (rolefromjsppage.equals("Client")) {            
            response.sendRedirect("activitypage.jsp");
        } else if (rolefromjsppage.equals("Contractor")) {           
            response.sendRedirect("contractoractivitypage.jsp");
        } else {
            
            request.setAttribute("loginStr", "Invalid Credentials: Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    private String getUserRoleByEmail(String email) {
           return "Client";
    }
    
    
    private void requestQuote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        System.out.println("request view");


        int clientID = Integer.parseInt(request.getParameter("ClientID"));
        String requestDate = request.getParameter("RequestDate");
        String note = request.getParameter("Note");
        String size = request.getParameter("Size");
        double height = Double.parseDouble(request.getParameter("Height"));
        String location = request.getParameter("Location");
        double proximityToHouse = Double.parseDouble(request.getParameter("Proximity"));
        int numberOfTrees = Integer.parseInt(request.getParameter("NumberOfTrees"));

        System.out.println("ClientID: " + clientID);
        System.out.println("RequestDate: " + requestDate);
        System.out.println("Note: " + note);
        System.out.println("Size: " + size);
        System.out.println("Height: " + height);
        System.out.println("Location: " + location);
        System.out.println("ProximityToHouse: " + proximityToHouse);
        System.out.println("NumberOfTrees: " + numberOfTrees);
       
        QuoteRequest requests = new QuoteRequest(clientID, requestDate, note, size, height, location, proximityToHouse, numberOfTrees);

        QuoteRequestDAO quoteRequestDAO = new QuoteRequestDAO();
        quoteRequestDAO.connect_func("john", "Pass1550");
        quoteRequestDAO.insert(requests);

        request.setAttribute("listRequests", quoteRequestDAO.listAllRequests());

        quoteRequestDAO.disconnect();

        request.getRequestDispatcher("Successful.jsp").forward(request, response);
    }

   
    private void responseQuote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        System.out.println("request view");

        int requestID = Integer.parseInt(request.getParameter("RequestID"));
        int clientID = Integer.parseInt(request.getParameter("ClientID"));
        String responseDate = request.getParameter("ResponseDate");
        double price = Double.parseDouble(request.getParameter("Price"));
        String workPeriodFrom = request.getParameter("WorkPeriodFrom");
        String workPeriodTo = request.getParameter("WorkPeriodTo");
        String note = request.getParameter("Note");

        System.out.println("RequestID: " + requestID);
        System.out.println("ClientID: " + clientID);
        System.out.println("ResponseDate: " + responseDate);
        System.out.println("Price: " + price);
        System.out.println("WorkPeriodFrom: " + workPeriodFrom);
        System.out.println("WorkPeriodTo: " + workPeriodTo);
        System.out.println("Note: " + note);

        QuoteResponse responses = new QuoteResponse(requestID, clientID, responseDate, price, workPeriodFrom, workPeriodTo, note);

        QuoteResponseDAO quoteResponseDAO = new QuoteResponseDAO();
        quoteResponseDAO.connect_func("john", "Pass1550");
        quoteResponseDAO.insert(responses);
        quoteResponseDAO.disconnect();

        request.setAttribute("listResponses", quoteResponseDAO.listAllResponses());

        request.getRequestDispatcher("Successful2.jsp").forward(request, response);
    }

    
    private void clientResponse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        System.out.println("client respond to quote response view");

        int contractorID = Integer.parseInt(request.getParameter("ContractorID"));
        int responseID = Integer.parseInt(request.getParameter("ResponseID"));
        String responseDate = request.getParameter("ResponseDate");
        String status = request.getParameter("Status"); // Make sure to have an input for the status in your HTML form
        String note = request.getParameter("Note");

        System.out.println("ContractorID: " + contractorID);
        System.out.println("ResponseID: " + responseID);
        System.out.println("ResponseDate: " + responseDate);
        System.out.println("Status: " + status);
        System.out.println("Note: " + note);

        ClientRespondToQuoteResponse clientResponse = new ClientRespondToQuoteResponse(contractorID, responseID, responseDate, status, note);

        ClientRespondToQuoteResponseDAO clientRespondToQuoteResponseDAO = new ClientRespondToQuoteResponseDAO();
        clientRespondToQuoteResponseDAO.connect_func("john", "Pass1550");
        clientRespondToQuoteResponseDAO.insert(clientResponse);

        request.setAttribute("listQuoteResponses", clientRespondToQuoteResponseDAO.listAllQuoteResponse());        

        request.getRequestDispatcher("Successful.jsp").forward(request, response);
    }

    
    private void contractorResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("contractor respond to client response view");

        int contractorID = Integer.parseInt(request.getParameter("ContractorID"));
        int clientResponseID = Integer.parseInt(request.getParameter("ClientResponseID"));
        String status = request.getParameter("Status"); 
        String responseDate = request.getParameter("ResponseDate");
        String note = request.getParameter("Note");
        double modifiedPrice = Double.parseDouble(request.getParameter("ModifiedPrice"));
        String modifiedWorkPeriodFrom = request.getParameter("ModifiedWorkPeriodFrom");
        String modifiedWorkPeriodTo = request.getParameter("ModifiedWorkPeriodTo");

        System.out.println("ContractorID: " + contractorID);
        System.out.println("ClientResponseID: " + clientResponseID);
        System.out.println("Status: " + status);
        System.out.println("ResponseDate: " + responseDate);       
        System.out.println("Note: " + note);
        System.out.println("ModifiedPrice: " + modifiedPrice);
        System.out.println("ModifiedWorkPeriodFrom: " + modifiedWorkPeriodFrom);
        System.out.println("ModifiedWorkPeriodTo: " + modifiedWorkPeriodTo);

        ContractorRespondToClientResponse contractorResponse = new ContractorRespondToClientResponse(contractorID, clientResponseID, responseDate, status, note, modifiedPrice, modifiedWorkPeriodFrom,
             modifiedWorkPeriodTo);

        ContractorRespondToClientResponseDAO contractorRespondToClientResponseDAO = new ContractorRespondToClientResponseDAO();
        contractorRespondToClientResponseDAO.connect_func("john", "Pass1550");
        contractorRespondToClientResponseDAO.insert(contractorResponse);

        request.setAttribute("listClientResponses", contractorRespondToClientResponseDAO.listAllClientResponse());

        request.getRequestDispatcher("Successful2.jsp").forward(request, response);
    }
    

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String email = request.getParameter("Email");
        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String password = request.getParameter("Password");
        String address = request.getParameter("Address");
        String creditCardInfo = request.getParameter("CreditCardInfo");
        String phoneNumber = request.getParameter("PhoneNumber");       
        String confirm = request.getParameter("confirmation");      
        String username = request.getParameter("FirstName");; 
        String role = request.getParameter("Role");

        if (address == null || address.isEmpty()) {
            address = "Default";
        }
        if (creditCardInfo == null || creditCardInfo.isEmpty()) {
            creditCardInfo = "Default";
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            phoneNumber = "Default ";
        }

        if (password.equals(confirm)) {
            if ("Client".equals(role)) {
                if (!clientDAO.checkEmail(email)) {
                    System.out.println("Registration Successful! Added to the database");
                    client clients = new client(email, firstName, lastName, password, address, creditCardInfo, phoneNumber);
                    clientDAO.insert(clients);
                    response.sendRedirect("login.jsp");
                } else {
                    System.out.println("Email taken, please enter a new email");
                    request.setAttribute("errorOne", "Registration failed: Email taken, please enter a new email.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            
            } else {
                System.out.println("Invalid role selected");
                request.setAttribute("errorOne", "Registration failed: Invalid role selected.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            System.out.println("Password and Password Confirmation do not match");
            request.setAttribute("errorTwo", "Registration failed: Password and Password Confirmation do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        
    }


    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCurrentClient("");
        response.sendRedirect("login.jsp");
    }
    

	public String getCurrentAdministrator() {
		return currentAdministrator;
	}
	

	public void setCurrentAdministrator(String currentAdministrator) {
		this.currentAdministrator = currentAdministrator;
	}
	

	public String getCurrentClient() {
		return currentClient;
	}
	

	public void setCurrentClient(String currentClient) {
		this.currentClient = currentClient;
	}
	
	
	private void setCurrentContractor(String currentContractor) {
		this.currentContractor = currentContractor;
			
	}
	
	 
	public String getCurrentContractor() {
		return currentContractor;
	}
	
	 
	private void setCurrentQuoteRequest(String currentQuoteRequest) {
	    this.currentQuoteRequest = currentQuoteRequest;
			
	}
	
	
	public String getCurrentQuoteRequest() {
		return currentQuoteRequest;
	}
	
	 
	private void setCurrentQuoteResponse(String currentQuoteResponse) {		    
		this.currentQuoteResponse = currentQuoteResponse;
	}
	
	 
	public String getCurrentQuoteResponse() {
		return currentQuoteResponse;
	}
	

	private void setCurrentClientRespondToQuoteResponse(String currentClientRespondToQuoteResponse) {		    
		this.currentClientRespondToQuoteResponse = currentClientRespondToQuoteResponse;
	}
	
	 
	public String getCurrentClientRespondToQuoteResponse() {
		return currentClientRespondToQuoteResponse;
	}
	

	private void setCurrentContractorRespondToClientResponse(String response) {		    
		this.currentClientRespondToQuoteResponse = response;
	}
	
	
	public String getCurrentContractorRespondToClientResponse() {
		return currentClientRespondToQuoteResponse;
	} 
}