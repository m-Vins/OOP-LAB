package university;

public class Student {
	private String Name;
	private String Surname;
	private Integer Key; 
	private int[] Courses;
	private static final int MAXCOURSES=25;
	
	
	public Student(String first, String last,Integer key ) { 
		this.Name=first;
		this.Surname=last;
		this.Key=key;
		this.Courses= new int[MAXCOURSES];
	}
	
	public String toString() {
		return Key.toString() + " " + Name + " " + Surname;
	}
	
	public boolean cmp(int key) {
		return this.Key.intValue()==key? true:false;
	}
}
