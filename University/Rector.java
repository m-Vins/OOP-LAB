package university;

public class Rector {
	private String Name;
	private String Surname;

	public Rector(String Name,String Surname) {
		this.Name=Name;
		this.Surname=Surname;
	}
	
	public String toString() {
		return Name + " " +Surname;
	}

}
