package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
	private int ID;
    private String Description;
	private Severity Severity;
	private String Author;
	private String Component;
	private State State;
	
	
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic }
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed };
    
    @SuppressWarnings("static-access")
	public Ticket(String Description, Severity severity,String Component, int ID, String Author) {
    	this.Description=Description;
    	this.Severity=severity;
    	this.Component=Component;
    	this.ID=ID;
    	this.State=State.Open;
    	this.Author=Author;
    }
    
    public int getId(){
        return this.ID;
    }

    public String getDescription(){
        return this.Description;
    }
    
    public Severity getSeverity() {
        return this.Severity;
    }

    public String getAuthor(){
        return this.Author;
    }
    
    public Ticket setAuthor(String author) {
    	this.Author=author;
    	return this;
    }
    
    public String getComponent(){
        return this.Component;
    }
    
    public State getState(){
        return this.State;
    }
    
    public Ticket setState(State s) {
    	this.State=s;
    	return this;
    }
    
    @SuppressWarnings("static-access")
	public String getSolutionDescription() throws TicketException {
    	if(!this.getState().equals(State.Closed)) throw new TicketException();
        return Description;
    }



	public void setSolution(String solution) {
		Description=solution;
	}
}
