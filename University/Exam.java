package university;


public class Exam{
	private int Points;
	private int StudentID;
	private int CourseID;
	
	public Exam(int Points, int StudentID, int CourseID) {
		this.Points=Points;
		this.StudentID=StudentID;
		this.CourseID=CourseID;
	}
	
	
	/**
	 * Retrieves the number of Points
	 * @return
	 */
	public int getPoints() {
		return this.Points;
	}
	/**
	 * Retrieves the StudentID
	 * @return
	 */
	public int getStudentID() {
		return this.StudentID;
	}
	
	/**
	 * Retrieves the CourseID
	 * @return
	 */
	public int getCourseID() {
		return this.CourseID;
	}
	
}
