package delivery;

public class Customer {
	private int ID;
	private String name;
	private String address;
	private String cell;
	private String email;
	
	
	public Customer(int ID,String name, String address, String cell, String email) {
		this.name = name;
		this.address = address;
		this.cell = cell;
		this.email = email;
		this.ID=ID;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}
	
	public String getInfo() {
		return this.name+", "+this.address+", "+this.cell+", "+this.email;
	}
}
