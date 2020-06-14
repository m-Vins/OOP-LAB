package managingProperties;

import java.util.ArrayList;
import java.util.List;

public class Profession {
	private String profession;
	private List<String> ids;
	
	
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
}
