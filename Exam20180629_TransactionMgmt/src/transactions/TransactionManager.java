package transactions;
import java.util.*;
import java.util.stream.Collectors;

//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class TransactionManager {
	private HashMap<String,Region> Regions=new HashMap<String,Region>();
	private HashMap<String,Carrier> Carriers=new HashMap<String,Carrier>();
	private HashMap<String,Request> Requests=new HashMap<String,Request>();
	private HashMap<String,Offer> Offers=new HashMap<String,Offer>();
	private HashMap<String,Transaction> Transactions=new HashMap<String,Transaction>();
	
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
		if(!Regions.values().stream().flatMap(s->s.getPlaces().stream()).
				filter(s->s.equals(placeName)).findAny().isPresent()
				||Requests.containsKey(requestId))
			throw new TMException();
		Requests.put(requestId, new Request(requestId,placeName,Regions.values().stream()
				.filter(s->s.checkPlace(placeName)).findFirst().
				orElseThrow(()->new TMException()),productId));
	}
	
	public void addOffer(String offerId, String placeName, String productId) 
			throws TMException {
		if(!Regions.values().stream().flatMap(s->s.getPlaces().stream()).
				filter(s->s.equals(placeName)).findAny().isPresent()
				||Offers.containsKey(offerId))
			throw new TMException();
		Offers.put(offerId, new Offer(offerId,placeName,Regions.values().stream()
				.filter(s->s.checkPlace(placeName)).findFirst().
				orElseThrow(()->new TMException()),productId));
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) 
			throws TMException {
		
		Request request=Requests.get(requestId);
		Offer offer=Offers.get(offerId);
		Carrier carrier=Carriers.get(carrierName);
		
		if(!request.getProductId().equals(offer.getProductId())
				||!carrier.checkPlace(request.getPlaceId())
				||!carrier.checkPlace(offer.getPlaceName())
				||Transactions.containsKey(transactionId)
				||Transactions.values().stream().
				filter(s->s.getOffer().getOfferId().equals(offerId)||
						s.getRequest().getRequestId().equals(requestId))
				.findAny().isPresent())
			throw new TMException();
		
		Transactions.put(transactionId,new Transaction(transactionId,request,offer,carrier));
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		Transactions.get(transactionId).setScore(score);
		return score>=1&&score<=10;
	}
	
//R4
	public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
		
		Map<String,Long> tmp= Transactions.values().stream().
		collect(Collectors.groupingBy(s->s.getDeliveryRegion().getName(),
				Collectors.counting()));
		
		SortedMap<Long,List<String>> ret=new TreeMap<Long,List<String>>(Comparator.reverseOrder());
		
		tmp.entrySet().stream().sorted((a,b)->a.getKey().compareTo(b.getKey())).
		forEach(s->{
			if(!ret.containsKey(s.getValue()))
				ret.put(s.getValue(), new ArrayList<String>());
			ret.get(s.getValue()).add(s.getKey());
		});
		
		return ret;
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return Transactions.values().stream().filter(s->s.getScore()>=minimumScore).
		collect(Collectors.groupingBy(s->s.getCarrier().getName(),
				TreeMap::new,
				Collectors.summingInt(s->s.getScore())));
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return Transactions.values().stream().collect(Collectors.groupingBy(s->s.getProductId(),
				TreeMap::new,
				Collectors.counting()));
	}
	
	
}

