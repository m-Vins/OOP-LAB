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

	
	public Student(String first, String last,Integer key ) { 
		this.Name=first;
		this.Surname=last;
		this.Key=key;
		this.Courses= new int[MAXCOURSES];
		this.nextCourse=0;
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
}
