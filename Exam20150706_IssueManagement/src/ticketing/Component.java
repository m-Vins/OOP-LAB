package ticketing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Component {
	private String name;
	private Map<String,Component> subComponents=new HashMap<String,Component>();
	private Component parent;
	
	private String path="";
	
	
	
	public Component(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public Component getParent() {
		return parent;
	}



	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	
	public void addSubComponent(Component sub) throws TicketException {
		if(subComponents.containsKey(sub.getName()))
			throw new TicketException();
		sub.setParent(this);
		sub.setPath(this.path + "/" + sub.getName());
		subComponents.put(sub.getName(),sub);
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public Set<Component> getSubComponent(){
		return new HashSet<Component>(this.subComponents.values());
	}

}
