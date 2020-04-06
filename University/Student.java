package university;


/**
 * @author Master
 *
 */
public class Student {
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
	 * @return int AVG
	 */
	public int getAvg() {
		int m=0;
		for(Exam x: this.exams) {
			m+=x.getPoints();
		}
		m/=this.nextExam;
		return m;
	}
}
