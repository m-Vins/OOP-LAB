package it.polito.oop.elective;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class Course {
	private String name;
	private int availablePositions;
	private LinkedList<Student> StudentRequests=new LinkedList<Student>();
	private long[] priorityNum=new long[3];
	private List<Student> AssignedStudents=new ArrayList<Student>();
	
	public Course(String name,int posti) {
		this.name=name;
		this.availablePositions=posti;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getavailablePositions() {
		return availablePositions;
	}

	public void setavailablePositions(int posti) {
		this.availablePositions = posti;
	}
	
	public void addStudentRequest(Student s,int priority) {
		StudentRequests.add(s);
		priorityNum[priority]++;
	}
	
	public Collection<Long> getNumStudentRequestForPriority(){
		Collection<Long> ret=new ArrayList<Long>();
		ret.add(priorityNum[0]);
		ret.add(priorityNum[1]);
		ret.add(priorityNum[2]);
		return ret;
	}

	public boolean assignStudent(Student s) {
		if(availablePositions>0) {
			AssignedStudents.add(s);
			availablePositions--;
			s.setAssigned();
			s.setAssignedCourse(this);
			return true;
		}
		return false;
	}
	
	public List<Student> getAssignedStudents(){
		return this.AssignedStudents;
	}
}
