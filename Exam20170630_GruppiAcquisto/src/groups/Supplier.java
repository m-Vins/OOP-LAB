package groups;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Supplier {
	private String Name;
	private SortedSet<Product> Products=new TreeSet<Product>((a,b)->
						a.getProductTypeName().
						compareTo(b.getProductTypeName()));
	private Set<Group> Groups=new HashSet<Group>();
	
	private int vote=0;
	private int nBids=0;
	
	
	

	public Supplier(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}

	public void addProduct(Product p) {
		this.Products.add(p);
	}
	
	public SortedSet<Product> getProducts(){
		return this.Products;
	}
	
	public void addGroup(Group g) {
		this.Groups.add(g);
	}
	
	public Set<Group> getGroups(){
		return this.Groups;
	}
	
	public boolean checkProduct(Product p) {
		return Products.contains(p);
	}
	
	public void addVote() {
		this.vote++;
	}
	
	public int getVote() {
		return this.vote;
	}
	
	public void addBid() {
		this.nBids++;
	}
	
	public int getNBids() {
		return this.nBids;
	}
}
