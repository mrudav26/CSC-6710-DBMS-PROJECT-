 public class QuoteRequest {
    protected int RequestID;
    protected int ClientID;
    protected String RequestDate;
    protected int NumberOfTrees;     
    protected String Note;
    protected String Size;
    protected double Height;
    protected String Location;
    protected double ProximityToHouse;
    protected int NumberOfTreesCut;
    protected String DateOfCut;
    protected Status status;

    
    public enum Status {
    	Accepted, Rejected, Pending, RequestAgain
	}


    public QuoteRequest(String note) {
        this.Note = note;
    }
    
    
    public QuoteRequest(int requestID, int clientID, String requestDate, int numberOfTrees,  String size, double height  ) {
        this.RequestID = requestID;
        this.ClientID = clientID;
        this.RequestDate = requestDate;
        this.NumberOfTrees = numberOfTrees;
        this.Size = size;
        this.Height = height;
    }
    
    
    public int getNumberOfTreesCut() {
		return NumberOfTreesCut;
	}
    

	public void setNumberOfTreesCut(int numberOfTreesCut) {
		NumberOfTreesCut = numberOfTreesCut;
	}
	

	public String getDateOfCut() {
		return DateOfCut;
	}
	

	public void setDateOfCut(String dateOfCut) {
		DateOfCut = dateOfCut;
	}
	

	public QuoteRequest(int clientID, String requestDate, String note, String size, double height, String location, double proximityToHouse, int numberOfTrees) {
        this.ClientID = clientID;
        this.RequestDate = requestDate;       
        this.Note = note;
        this.Size = size;
        this.Height = height;
        this.Location = location;
        this.ProximityToHouse = proximityToHouse;
        this.NumberOfTrees = numberOfTrees;
    }
	

    public QuoteRequest(int requestID, int clientID, String requestDate, int numberOfTrees, String note, String size, double height, String location, double proximityToHouse) {
        this.RequestID = requestID;
        this.ClientID = clientID;
        this.RequestDate = requestDate;
        this.NumberOfTrees = numberOfTrees;       
        this.Note = note;
        this.Size = size;
        this.Height = height;
        this.Location = location;
        this.ProximityToHouse = proximityToHouse;   
    }

    // Getter and setter methods
    
    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }
    
    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getNumberOfTrees() {
        return NumberOfTrees;
    }

    public void setNumberOfTrees(int numberOfTrees) {
        NumberOfTrees = numberOfTrees;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public double getProximityToHouse() {
        return ProximityToHouse;
    }

    public void setProximityToHouse(double proximityToHouse) {
        ProximityToHouse = proximityToHouse;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        status = status;
    }
}