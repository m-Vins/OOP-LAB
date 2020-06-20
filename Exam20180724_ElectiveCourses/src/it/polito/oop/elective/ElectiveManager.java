package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {
	private HashMap<String,Course> Courses=new HashMap<String,Course>();
	private HashMap<String,Student> Students=new HashMap<String,Student>();
	private List<Notifier> Notifiers=new ArrayList<Notifier>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        Courses.put(name, new Course(name,availablePositions));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return new TreeSet<String>(Courses.keySet());
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, 
                                  double gradeAverage){
    	if(Students.containsKey(id)) {
    		Students.get(id).setAvg(gradeAverage);
    		return;
    	}
    	Students.put(id, new Student(id,gradeAverage));
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return Students.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return Students.values().stream().filter(s->s.checkAvg(inf, sup)).
        		map(s->s.getId()).collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
    	if(!Students.containsKey(id)||courses.size()<1||courses.size()>=3)
    		throw new ElectiveException();
    	Student s=Students.get(id);
    	int i=0;
    	for(String c:courses) {
    		s.addRequest(Courses.get(c));
    		Courses.get(c).addStudentRequest(s,i);
    		i++;
    	}
    	Notifiers.stream().forEach(a->a.requestReceived(id));
        return courses.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
    	Map<String,List<Long>> ret=new HashMap<String,List<Long>>();
    	Courses.values().stream().forEach(s->ret.put(s.getName(), new ArrayList<Long>(s.getNumStudentRequestForPriority())));
        return ret;
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        return Students.values().stream().sorted((a,b)->(int) (b.getAvg()-a.getAvg()))
            	.filter(s->!assignStudent(s)).count();
    }
    
    
    private boolean assignStudent(Student s) {
    	for(Course c:s.getRequest()) {
    		if(c.assignStudent(s)) {
    			Notifiers.stream().forEach(a->a.assignedToCourse(s.getId(), c.getName()));
    			return true;
    		}
    	}
    	return false;
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        Map<String,List<String>> ret=Students.values().stream().filter(Student::isAssigned).
            	sorted((a,b)->(int) (b.getAvg()-a.getAvg())).
            	collect(Collectors.
            			groupingBy(s->s.getAssignedCourse().getName(),
            					Collectors.mapping(s->s.getId(),Collectors.toList())));
        
        for(Course c:Courses.values()) {
        	if(!ret.containsKey(c.getName()))
        		ret.put(c.getName(),new ArrayList<String>());
        }
        return ret;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        Notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
    	double ret=Students.values().stream().filter(Student::isAssigned).
    			filter(s->s.isChoiceNumber(choice-1)).count();
    	ret=ret/Students.size();
        return ret;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return Students.values().stream().filter(s->!s.isAssigned()).
        		map(s->s.getId()).collect(Collectors.toList());
    }
    
    
}
