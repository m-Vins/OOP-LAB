package university;

import java.util.Arrays;
import java.util.logging.Logger;

public class UniversityExt extends University {
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	
	@Override
	public int enroll(String first, String last){
		int ret=super.enroll(first, last);
		if(ret!=-1)
			logger.info("New student enrolled: "+ret+","+first+" "+last);
		return ret;
	}
	
	@Override
	public int activate(String title, String teacher){
		int ret= super.activate(title, teacher);
		if(ret!=-1)
			logger.info("New course activated: "+ ret +","+ title+"," +teacher);
		return ret;
	}
	
	
	@Override
	public void register(int studentID, int courseCode) {
		super.register(studentID,courseCode);
		logger.info("Student "+studentID+" signed up for course "+courseCode);
	}
	/**
	 * Adds an exam
	 * 
	 * @param StudentID id of the Student
	 * @param CourseID  id of the Course
	 * @param vote	number of points
	 */
	
	public void exam(int StudentID, int CourseID, int vote) {
		Exam exam=new Exam(vote,StudentID,CourseID);
		this.getStudent(StudentID).addExam(exam);
		this.getCourse(CourseID).addExam(exam);
		logger.info("Student "+StudentID+" took an exam in course "+CourseID+"with grade "+vote);
	}
	
	/**
	 * Retrieves the AVG of the Student
	 * @param StudentID StudentID of the Student
	 * @return	
	 */
	public String studentAvg(Integer StudentID) {
		if(this.getStudent(StudentID).isEmpty()) {
			return "Student " + StudentID.toString() + "hasn't taken any exams";
		}
		return "Student " +StudentID.toString() + " : " + this.getStudent(StudentID).getAvg() ;
	}
	
	public String courseAvg(Integer CourseID) {
		if(this.getCourse(CourseID).isEmpty()) {
			return "No student has taken the exam in " + this.getCourse(CourseID).toName();
		}
		return "the average for the course " + this.getCourse(CourseID).toName() + " is :" + 
		((Integer)this.getCourse(CourseID).getAVG()).toString();
	}
	
	public String topThreeStudents() {
		Student[] Students = this.getStudents();
		
		String MessageOut="";
		
		Arrays.sort(Students,0,this.getStudentCounts());
		
		if(Students[0]!=null) {
			MessageOut+=Students[0].getFirstName() +" "+ Students[0].getLastName() + " "+
					Students[0].getValuation() +"\n";
		}		
		if(Students[1]!=null) {
			MessageOut+=Students[1].getFirstName() +" "+ Students[1].getLastName() + " "+
					Students[1].getValuation() +"\n";
		}
		if(Students[2]!=null) {
			MessageOut+=Students[2].getFirstName() +" "+ Students[2].getLastName() + " "+
					Students[2].getValuation() +"\n";
		}
		
		return MessageOut;
	}
}
