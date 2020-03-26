package university;

public class Course {
	private String Name;
	private String Teacher;
	private Integer Cod;
	private final static int MAXSTUD=100;
	private int[] Students;
	
	public Course(String Name, String Teacher,Integer Cod) {
		this.Name=Name;
		this.Teacher=Teacher;
		this.Cod=Cod;
		Students= new int[MAXSTUD];
	}
	
	public String toString() {
		return Cod.toString()+", " + Name +", " + Teacher;
	}
}
