package transactions;

import java.util.SortedSet;
import java.util.TreeSet;

public class Region {
	private String Name;
	private SortedSet<String> Places=new TreeSet<String>();
	
	public Region(String Name) {
		this.Name=Name;
	}

	public String getName() {
		return this.Name;
	}
	
	public void addPlace(String place) {
		Places.add(place);
	}
	
	public SortedSet<String> getPlaces(){
		return this.Places;
	}
	
	public boolean checkPlace(String placeName) {
		return Places.contains(placeName);
	}
}
