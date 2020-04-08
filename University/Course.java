package university;


public class Course {
	private String Name;
	private String Teacher;
	private Integer Cod;
	private int[] Students;
	private int nextCod;
	private final static int MAXSTUD=100;
	
	private Exam[] exams;
	private int nextExam;
	
	public Course(String Name, String Teacher,Integer Cod) {
		this.Name=Name;
		this.Teacher=Teacher;
		this.Cod=Cod;
		this.Students= new int[MAXSTUD];
		this.nextCod=0;
		this.exams= new Exam[MAXSTUD];
		this.nextExam=0;
	}
	
	public String toName() {
		return this.Name;
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
	/**
	 * adds the exam to the course
	 * @param e	exam
	 */
	public void addExam(Exam e) {
		if(nextExam>=MAXSTUD) {
			System.err.println("NUMERO ESAMI PER CORSO SUPERATO");
			return;
		}
		this.exams[nextExam++]=e;
	}
	
	/**
	 * 
	 * @return TRUE if the course hasn't exams
	 */
	public boolean isEmpty() {
		return nextExam==0;
	}
	
	public int getAVG() {
		int m=0;
		for(Exam x: exams) {
			if(x==null) break;
			m+=x.getPoints();
		}
		m/=this.nextExam;
		return m;
	}
}
