package transactions;

import java.util.SortedSet;
import java.util.TreeSet;

public class Carrier {
	private String Name;
	private SortedSet<Region> Regions=new TreeSet<Region>((a,b)->a.getName().compareTo(b.getName()));
	
	public Carrier(String Name) {
		this.Name=Name;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void addRegion(Region region) {
		this.Regions.add(region);
	}
	
	public SortedSet<Region> getRegions(){
		return this.Regions;
	}
	
	public boolean checkRegion(Region region) {
		return Regions.contains(region);
	}
}
