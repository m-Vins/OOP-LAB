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
	private static final int FIRSTSTUD=10000;
	
	private Course[] Courses;
	private int nextCourse;
	private static final int MAXCOURSE=50;
	private static final int FIRSTCOURSE=10;
	
	

	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.Name=name;
		this.nextCod=FIRSTSTUD;
		Students= new Student[MAXSTUD];
		this.nextCourse=FIRSTCOURSE;
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
		if (this.nextCod-FIRSTSTUD>=MAXSTUD) {
			System.err.println("limite studenti superato!");
			return -1;
		}
		this.Students[nextCod-FIRSTSTUD]= new Student(first,last,this.nextCod++);
		return nextCod-1;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		if(id>=FIRSTSTUD||id<FIRSTSTUD+nextCod)
			return Students[id-FIRSTSTUD].toString();
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
		this.Courses[nextCourse-FIRSTCOURSE]=new Course(title,teacher,nextCourse++);
		return nextCourse-1;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		if(code>=FIRSTCOURSE||code<FIRSTCOURSE+nextCourse)
			return this.Courses[code-FIRSTCOURSE].toString();
		System.err.println("indice errato");
		return "indice errato";
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		if(courseCode<FIRSTCOURSE||courseCode>nextCourse) {
			System.err.println("errore courseCODE!");
			return ;
		}
		if(studentID<FIRSTSTUD||studentID>=nextCod) {
			System.err.println("errore STUDENT_ID!");
			return ;
		}
		this.Students[studentID-FIRSTSTUD].newCourse(courseCode);
		this.Courses[courseCode-FIRSTCOURSE].newStudent(studentID);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		if(courseCode<FIRSTCOURSE||courseCode>nextCourse) {
			System.err.println("errore courseCODE!");
			return "";
		}
		
		int[] ArrayCod=this.Courses[courseCode-FIRSTCOURSE].StudentsArray();
		int i;
		String tmp="";
		
		for(i=0;i<this.Courses[courseCode-FIRSTCOURSE].CountStudents();i++) {
			tmp+=this.Students[ArrayCod[i]-FIRSTSTUD].toString()+"\n";
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
		if(studentID<FIRSTSTUD||studentID>=nextCod) {
			System.err.println("errore STUDENT_ID!");
			return "";
		}
		int[] ArrayCod=this.Students[studentID-FIRSTSTUD].CoursesArray();
		String tmp="";
		int i;
		
		for (i=0;i<this.Students[studentID-FIRSTSTUD].CountCourses();i++) {
			tmp+=this.Courses[ArrayCod[i]-FIRSTCOURSE].toString() + "\n";
		}
		return i==0? "lo studente non frequenta corsi\n":tmp;
	}
	
	/**
	 * Adds an exam
	 * 
	 * @param StudentID id of the Student
	 * @param CourseID  id of the Course
	 * @param vote	number of points
	 */
	
	public void exam(int StudentID, int CourseID, int vote) {
		Exam exam=new Exam(StudentID,CourseID,vote);
		this.Students[StudentID-FIRSTSTUD].addExam(exam);
		this.Courses[CourseID-FIRSTCOURSE].addExam(exam);
	}
	
	/**
	 * Retrieves the AVG of the Student
	 * @param StudentID StudentID of the Student
	 * @return	
	 */
	public String studentAvg(Integer StudentID) {
		if(this.Students[StudentID-FIRSTSTUD].isEmpty()) {
			return "Student " + StudentID.toString() + "hasn't taken any exams";
		}
		return "Student " +StudentID.toString() + " : " + ((Integer)this.Students[StudentID-FIRSTSTUD].getAvg()).toString() ;
	}
	
	public String courseAvg(Integer CourseID) {
		if(this.Courses[CourseID-FIRSTCOURSE].isEmpty()) {
			return "No student has taken the exam in " + this.Courses[CourseID-FIRSTCOURSE].toName();
		}
		return "the average for the course " + this.Courses[CourseID-FIRSTCOURSE].toName() + " is :" + 
		((Integer)this.Courses[CourseID-FIRSTCOURSE].getAVG()).toString();
	}
}
