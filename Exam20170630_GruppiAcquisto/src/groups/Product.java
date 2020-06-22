package groups;

import java.util.HashSet;
import java.util.Set;

public class Product {
	private String productTypeName;
	private Set<Supplier> suppliers=new HashSet<Supplier>();

	public Product(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	
	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public void addSupplier(Supplier s) {
		this.suppliers.add(s);
	}
	
	public Set<Supplier> getSuppliers(){
		return this.suppliers;
	}
	
	@Override 
	public boolean equals(Object g) {
		if(g==this)
			return true;
		if(g instanceof Product) {
			return this.productTypeName.equals(((Product) g).getProductTypeName());
		}
		return false;
	}
	
	
}
