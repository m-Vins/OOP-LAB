package managingProperties;

public class Request {
	private State state;
	private String Owner;
	private String Apartment;
	private String Profession;
	private int id;
	
	private String Professional;
	
	private int amount;
	
	public enum State{pending,assigned,completed};
	
	public Request(String Owner,String Apartment,String Profession,int id) {
		this.Apartment=Apartment;
		this.Owner=Owner;
		this.Profession=Profession;
		this.id=id;
		this.state=State.pending;
	}

	public String getProfessional() {
		return Professional;
	}

	public Request setProfessional(String professional) {
		Professional = professional;
		return this;
	}

	public String getProfession() {
		return Profession;
	}

	public State getState() {
		return this.state;
	}
	
	public String getOwnre() {
		return this.Owner;
	}
	
	public String getApartment() {
		return this.Apartment;
	}
	
	public Request setAssigned() throws PropertyException {
		if(!this.state.equals(State.pending))
			throw new PropertyException();
		this.state=State.assigned;
		return this;
	}
	
	public int getId() {
		return this.id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.state=State.completed;
		this.amount = amount;
	}
}
