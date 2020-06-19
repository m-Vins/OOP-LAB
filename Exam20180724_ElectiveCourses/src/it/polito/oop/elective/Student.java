package it.polito.oop.elective;

import java.util.LinkedList;
import java.util.List;

public class Student {
	private String id;
	private double avg;
	private LinkedList<Course> Requests=new LinkedList<Course>();
	private boolean isAssigned=false;
	private Course assignedCourse;
	
	public Student(String id,double avg) {
		this.id=id;
		this.avg=avg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public boolean checkAvg(double avg1,double avg2) {
		return avg1<=this.avg&&avg2>=this.avg;
	}
	
	public void addRequest(Course c) {
		Requests.add(c);
	}
	
	public void setAssigned() {
		this.isAssigned=true;
	}
	
	public boolean isAssigned() {
		return this.isAssigned;
	}
	
	public List<Course> getRequest(){
		return this.Requests;
	}

	public Course getAssignedCourse() {
		return assignedCourse;
	}

	public void setAssignedCourse(Course assignedCourse) {
		this.assignedCourse = assignedCourse;
	}
	
	public boolean isChoiceNumber(int n) {
		return Requests.get(n).equals(assignedCourse);
	}
}
