package transactions;
import java.util.*;
import java.util.stream.Collectors;

//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class TransactionManager {
	private HashMap<String,Region> Regions=new HashMap<String,Region>();
	private HashMap<String,Carrier> Carriers=new HashMap<String,Carrier>();
	
//R1
	public List<String> addRegion(String regionName, String... placeNames) { 
		if(!Regions.containsKey(regionName)) {
			Regions.put(regionName, new Region(regionName));
		}
		Region ret=Regions.get(regionName);
		for(String x:placeNames) {
			if(!Regions.values().stream().flatMap(s->s.getPlaces().stream()).
					filter(s->s.equals(x)).findAny().isPresent())
				ret.addPlace(x);
		}
		return new ArrayList<String>(ret.getPlaces());
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) { 
		if(!Carriers.containsKey(carrierName)) {
			Carriers.put(carrierName, new Carrier(carrierName));
		}
		Carrier ret=Carriers.get(carrierName);
		for(String x:regionNames) {
			if(Regions.containsKey(x))
				ret.addRegion(Regions.get(x));
		}
		return new ArrayList<String>(ret.getRegions().stream().map(Region::getName).collect(Collectors.toList()));
	}
	
	public List<String> getCarriersForRegion(String regionName) { 
		return Carriers.values().stream().filter(s->s.checkRegion(Regions.get(regionName)))
				.map(s->s.getName()).sorted().collect(Collectors.toList());
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) 
			throws TMException {
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		return false;
	}
	
//R4
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		return new TreeMap<Long, List<String>>();
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return new TreeMap<String, Integer>();
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return new TreeMap<String, Long>();
	}
	
	
}

