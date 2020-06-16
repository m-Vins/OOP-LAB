package managingProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Profession {
	private String profession;
	private List<String> ids;
	private HashMap<String,Integer> Assigned=new HashMap<String,Integer>();
	
	
	public Profession(String profession,String... id) {
		this.setProfession(profession);
		this.ids=new ArrayList<String>();
		for(String x:id)
			ids.add(x);
	}
	
	public boolean containId(String id) {
		return ids.contains(id);
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public int getSize() {
		return ids.size();
	}
	
	public void Assign(String id,int requestN) throws PropertyException {
		if(!ids.contains(id))
			throw new PropertyException();
		Assigned.put(id, requestN);
	}
}
