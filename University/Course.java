package university;

public class Course {
	private String Name;
	private String Teacher;
	private Integer Cod;
	private int[] Students;
	private int nextCod;
	private final static int MAXSTUD=100;
	
	
	public Course(String Name, String Teacher,Integer Cod) {
		this.Name=Name;
		this.Teacher=Teacher;
		this.Cod=Cod;
		Students= new int[MAXSTUD];
		this.nextCod=0;
	}
	
	public String toString() {
		return Cod.toString()+", " + Name +", " + Teacher;
	}
	/**
	 * assign a new student to the course
	 * @param cod ID of the student
	 */
	public void newStudent(int cod) {
		this.Students[nextCod++]=cod;
	}
	
	/**
	 * 
	 * @return the number of the students in the course
	 */
	
	public int CountStudents() {
		return nextCod;
	}
	
	/**
	 * 
	 * @return the Array of the IDs of the student in the course
	 */
	
	public int[] StudentsArray() {
		return this.Students;
	}
}
