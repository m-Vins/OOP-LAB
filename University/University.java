package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	private Rector rector;
	
	private String Name;
	
	private Student[] Students;
	private int nextCod;
	private static final int MAXSTUD=1000;
	private static final int MINSTUD=1000;
	
	private Course[] Courses;
	private int nextCourse;
	private static final int MAXCOURSE=50;
	private static final int MINCOURSE=10;
	

	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.Name=name;
		this.nextCod=MINSTUD;
		Students= new Student[MAXSTUD];
		this.nextCourse=MINCOURSE;
		Courses= new Course[MAXCOURSE];
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return this.Name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		this.rector=new Rector(first,last);		
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public String getRector(){
		return this.rector.toString();
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last){
		this.Students[nextCod-MINSTUD]= new Student(first,last,nextCod++);
		return nextCod-1;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		if(id>=MINSTUD&&id<MINSTUD+nextCod)
			return Students[id-MINSTUD].toString();
		return null;
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		this.Courses[nextCourse-MINCOURSE]=new Course(title,teacher,nextCourse++);
		return nextCourse-1;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		if(code>=MINCOURSE&&code<MINCOURSE+nextCourse)
			return this.Courses[code-MINCOURSE].toString();
		return null;
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		this.Students[studentID-MINSTUD].newCourse(courseCode);
		this.Courses[courseCode-MINCOURSE].newStudent(studentID);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		int[] ArrayCod=this.Courses[courseCode-MINCOURSE].StudentsArray();
		int i;
		String tmp="";
		
		for(i=0;i<this.Courses[courseCode-MINCOURSE].CountStudents();i++) {
			tmp+=this.Students[ArrayCod[i]-MINSTUD].toString()+"\n";
		}
		return i==0? "il corso non contiene studenti":tmp;
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */ 
	public String studyPlan(int studentID){
		int[] ArrayCod=this.Students[studentID-MINSTUD].CoursesArray();
		String tmp="";
		int i;
		
		for (i=0;i<this.Students[studentID-MINSTUD].CountCourses();i++) {
			tmp+=this.Courses[ArrayCod[i]-MINCOURSE].toString() + "\n";
		}
		return i==0? "lo studente non frequenta corsi\n":tmp;
	}
	
}
