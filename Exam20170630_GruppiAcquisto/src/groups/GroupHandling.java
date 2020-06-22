package groups;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;




public class GroupHandling {
	private HashMap<String,Product> Products=new HashMap<String,Product>();
	private HashMap<String,Supplier> Suppliers=new HashMap<String,Supplier>();
	private HashMap<String,Group> Groups=new HashMap<String,Group>();
	private HashMap<String,Customer> Customers=new HashMap<String,Customer>();
	
//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(Products.containsKey(productTypeName))
			throw new GroupHandlingException();
		
		Product p=new Product(productTypeName);
		
		Products.put(productTypeName, p);
		
		for(String x:supplierNames) {
			if(!Suppliers.containsKey(x))
				Suppliers.put(x, new Supplier(x));
			p.addSupplier(Suppliers.get(x));
			Suppliers.get(x).addProduct(p);
		}
	}
	
	public List<String> getProductTypes (String supplierName) {
		return Suppliers.get(supplierName).getProducts().
				stream().collect(Collectors.
						mapping(Product::getProductTypeName,
								Collectors.toList()));
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(Groups.containsKey(name)||!Products.containsKey(productName))
			throw new GroupHandlingException();
		
		
		Group g=new Group(name,Products.get(productName));
		
		Groups.put(name, g);
		
		for(String x:customerNames) {
			if(!Customers.containsKey(x))
				Customers.put(x, new Customer(x));
			g.addCustomer(Customers.get(x));
			Customers.get(x).addGroup(g);
		}
			
	}
	
	public List<String> getGroups (String customerName) {
        return Customers.get(customerName).getGroups().stream()
        		.collect(Collectors.mapping(Group::getName, Collectors.toList()));
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		Group g= Groups.get(groupName);
		
		if(Stream.of(supplierNames).filter(a->!Suppliers.
				containsKey(a)).findAny().isPresent())
			throw new GroupHandlingException();
		
		Stream<Supplier> s=Stream.of(supplierNames).map(a->Suppliers.get(a));
		
		if(s.filter(a->!a.checkProduct(g.getProductType())).
				findFirst().isPresent())
			throw new GroupHandlingException();
		
		Stream.of(supplierNames).map(a->Suppliers.get(a)).forEach(a->{
			g.addSupplier(a);
			a.addGroup(g);
		});
		
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		if(!Groups.containsKey(groupName)||!Suppliers.containsKey(supplierName))
			throw new GroupHandlingException();
		Groups.get(groupName).AddOffer(Suppliers.get(supplierName), price);
		Suppliers.get(supplierName).addBid();
	}
	
	public String getBids (String groupName) {
		return Groups.get(groupName).getOffers().entrySet().stream().
        		sorted((a,b)->a.getValue()-b.getValue()).
        		map(s->s.getKey().getName()+":"+s.getValue()).
        		collect(Collectors.joining(","));
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		Customer c=Customers.get(customerName);
		Group g=Groups.get(groupName);
		Supplier s=Suppliers.get(supplierName);
		
		if(!g.getCustomers().contains(c)||!g.getOffers().keySet().contains(s))
			throw new GroupHandlingException();
		
		s.addVote();
	}
	
	public String  getVotes (String groupName) {
        return Groups.get(groupName).getOffers().keySet().stream().
        		filter(s->s.getVote()!=0).collect(Collectors.
        				mapping(s->s.getName()+":"+s.getVote(), Collectors.joining(",")));
	}
	
	public String getWinningBid (String groupName) {
		Comparator<Supplier> c=(a,b)->{
			int ret=a.getVote()-b.getVote();
			if(ret==0) {
				int pa=Groups.get(groupName).getOffers().get(a);
				int pb=Groups.get(groupName).getOffers().get(b);
				return pb-pa; 
			}
			return ret;
		};
		
		
        return Groups.get(groupName).getOffers().keySet().stream().
        		sorted(c.reversed()).map(s->s.getName()+":"+s.getVote()).
        		findFirst().orElseThrow(NullPointerException::new);
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
		return Groups.values().stream().filter(s->s.getOffers().size()!=0).
				collect(Collectors.toMap(Group::getProductName,
				Group::getMaxOffer,(a,b)->a>b?a:b,TreeMap::new));
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
        return Groups.values().stream().flatMap(s->s.getOffers().keySet().stream()).
        		sorted((a,b)->a.getName().compareTo(b.getName())).distinct().
        		collect(Collectors.groupingBy(Supplier::getNBids,
        				()->new TreeMap<Integer, List<String>>(Comparator.reverseOrder()),
        				Collectors.mapping(Supplier::getName, Collectors.toList())));
        
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
        return Groups.values().stream().collect(Collectors.groupingBy(Group::getProductName,
        		TreeMap::new,
        		Collectors.summingLong(Group::getCustomerNumber)));
	}
	
}
