package ticketing;


import java.util.Set;

import ticketing.IssueManager.UserClass;

public class User {
	
	private String username;
	private Set<UserClass> classes;
	
	public User(String username,Set<UserClass> classes) {
		this.username=username;
		this.classes=classes;
	}
	
	public Set<UserClass> getUserClasses(){
		return this.classes;
	}
	
	public String getUsername() {
		return username;
	}


}
