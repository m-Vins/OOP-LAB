package groups;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Group {
	private String Name;
	private Product ProductType;
	private Set<Customer> customers=new HashSet<Customer>();
	private Set<Supplier> suppliers=new HashSet<Supplier>();
	private TreeMap<Supplier,Integer> Offers=new TreeMap<Supplier,Integer>((a,b)->
												a.getName().compareTo(b.getName()));
	
	public Group(String name, Product productType) {
		Name = name;
		setProductType(productType);
	}
	
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}



	public Product getProductType() {
		return ProductType;
	}
	
	public String getProductName() {
		return this.ProductType.getProductTypeName();
	}



	public void setProductType(Product productType) {
		ProductType = productType;
	}
	
	public void addCustomer(Customer c) {
		this.customers.add(c);
	}
	
	public Set<Customer> getCustomers(){
		return this.customers;
	}
	
	public void addSupplier(Supplier s){
		suppliers.add(s);
	}
	
	public Set<Supplier> getSuppliers(){
		return this.suppliers;
	}
	
	public void AddOffer(Supplier s,Integer price) throws GroupHandlingException{
		if(!this.suppliers.contains(s))
			throw new GroupHandlingException();
		Offers.put(s, price);
	}
	
	public Map<Supplier,Integer> getOffers(){
		return this.Offers;
	}
	
	public int getMaxOffer() {
		return Offers.values().stream().mapToInt(s->s).max().orElse(0);
	}
	
	public int getCustomerNumber() {
		return customers.size();
	}
}
