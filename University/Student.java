package university;


/**
 * @author Master
 *
 */
public class Student implements Comparable<Student>{
	private String Name;
	private String Surname;
	private Integer Key; 
	private int[] Courses;
	private int nextCourse;
	private static final int MAXCOURSES=25;
	
	private Exam[] exams;
	private int nextExam;

	
	public Student(String first, String last,Integer key ) { 
		this.Name=first;
		this.Surname=last;
		this.Key=key;
		this.Courses= new int[MAXCOURSES];
		this.nextCourse=0;
		this.exams= new Exam[MAXCOURSES];
		this.nextExam=0;
	}
	
	public String toString() {
		return Key.toString() + " " + Name + " " + Surname;
	}
	/**
	 * assign a new course to the student
	 * @param cod ID of the course
	 */
	public void newCourse(int cod) {
		this.Courses[nextCourse++]=cod;
	}
	
	/**
	 * 
	 * @return the number of the student courses
	 */
	
	public int CountCourses() {
		return this.nextCourse;
	}
	
	/**
	 * 
	 * @return the Array of the IDs of the student courses
	 */
	public int[] CoursesArray() {
		return this.Courses;
	}
	
	/**
	 * adds the exam to the student
	 * @param e	exam
	 */
	public void addExam(Exam e) {
		if(nextExam>=MAXCOURSES) {
			System.err.println("NUMERO ESAMI PER STUDENTE SUPERATO");
			return;
		}
		this.exams[nextExam++]=e;
	}
	
	/**
	 * 
	 * @return TRUE if the student hasn't taken any exams
	 */
	public boolean isEmpty() {
		return nextExam==0;
	}
	
	/**
	 * Retrieves the AVG of the Student
	 * @return  AVG
	 */
	public float getAvg() {
		float m=000;
		for(Exam x: this.exams) {
			if(x==null)	break;
			m+=x.getPoints();
		}
		m/=(float)this.nextExam;
		return m;
	}
	
	/**
	 * Retrieves the valuation of the student
	 * @return
	 */
	public float getValuation() {
		Integer x;
		x=(int)this.getAvg();
		x+=(this.nextExam/this.nextCourse)*10;
		return x;
	}
	
	
	
	
	/**
	 * Retrieves the ID of the Student
	 * @return
	 */
	public int getID() {
		return this.Key;
	}
	
	/**
	 * Retrieves the FirstName of the Student
	 * @return
	 */
	public String getFirstName() {
		return this.Name;
	}
	
	/**
	 * Retrieves the LastName of the student
	 * @return
	 */
	public String getLastName() {
		return this.Surname;
	}

	
	public static int comparator(Student arg0, Student arg1) {
		return (int)(arg1.getValuation()-arg0.getValuation());
	}

	@Override
	public int compareTo(Student arg0) {
		
		return -(int)((Float)this.getValuation()).compareTo((Float)((Student)arg0).getValuation());
	}

}
