package groups;


import java.util.SortedSet;
import java.util.TreeSet;

public class Customer {
	private String name;
	private SortedSet<Group> Groups=new TreeSet<Group>((a,b)
							->a.getName().compareTo(b.getName()));
	
	
	
	public Customer(String name) {
		super();
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addGroup(Group g) {
		this.Groups.add(g);
	}
	
	public SortedSet<Group> getGroups(){
		return this.Groups;
	}
	
	

}
