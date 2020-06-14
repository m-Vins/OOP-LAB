package managingProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PropertyManager {
	private HashMap<String,Building> Buildings=new HashMap<String,Building>();
	private HashMap<String,List<String>> OwnerApartments=new HashMap<String,List<String>>();
	private HashMap<String,Profession> Professions=new HashMap<String,Profession>();
	
	/**
	 * Add a new building 
	 */
	public void addBuilding(String building, int n) throws PropertyException {
		if(Buildings.containsKey(building)) 
			throw new PropertyException("building "+ building +" già inserito");
		Buildings.put(building,new Building(building,n));
	}

	public void addOwner(String owner, String... apartments) throws PropertyException {
		if(OwnerApartments.containsKey(owner)) 
			throw new PropertyException("owner "+owner+" già inserito");
		this.OwnerApartments.put(owner,new ArrayList<String>());
		try{
			for(String x:apartments) {
			String[] subString=x.split(":");
			if(!Buildings.containsKey(subString[0]))
				throw new PropertyException("building "+subString[0]+ " inesistente");
			Buildings.get(subString[0]).addOwners(owner, Integer.parseInt(subString[1]));
			OwnerApartments.get(owner).add(x);
		    }
		}catch(PropertyException e) {
			this.OwnerApartments.remove(owner);
			throw new PropertyException();
		}
		
	}

	/**
	 * Returns a map ( number of apartments => list of buildings ) 
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		return Buildings.values().stream().sorted((a,b)->a.getId().compareTo(b.getId())).
		collect(Collectors.groupingBy(Building::getN,TreeMap::new,Collectors.
				mapping(Building::getId, Collectors.toList())));
	}

	public void addProfessionals(String profession, String... professionals) throws PropertyException {
				if(Professions.containsKey(profession)) 
					throw new PropertyException("professione "+profession+" già esistente");
				for(String x:professionals) {
					if(Professions.values().stream().filter(s->s.containId(x)).findFirst().isPresent())
						throw new PropertyException("id "+ x+" già esistente");
				}
				Professions.put(profession, new Profession(profession,professionals));
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		SortedMap<String,Integer> ret=new TreeMap<String,Integer>();
		Professions.values().stream().forEach(s->ret.put(s.getProfession(), s.getSize()));
		return ret;
	}

	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		
		return 0;
	}

	public void assign(int requestN, String professional) throws PropertyException {
		
		
	}

	public List<Integer> getAssignedRequests() {
		
		return null;
	}

	
	public void charge(int requestN, int amount) throws PropertyException {
		
		
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		
		return null;
	}
	
	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		
		return null;
	}

	/**
	 * Returns the map ( building => ( profession => total expenses) ).
	 * Both buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		
		return null;
	}

}
