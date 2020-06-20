package delivery;

import java.util.HashMap;
import java.util.Map;


public class Order {
	private int IDorder;
	private int IDcustomer;
	private HashMap <Menu,Integer> Items=new HashMap<Menu,Integer>();
	
	public Order(int iDorder, int iDcustomer) {
		IDorder = iDorder;
		IDcustomer = iDcustomer;
	}
	
	
	
	public int getIDorder() {
		return IDorder;
	}
	public void setIDorder(int iDorder) {
		IDorder = iDorder;
	}
	public int getIDcustomer() {
		return IDcustomer;
	}
	public void setIDcustomer(int iDcustomer) {
		IDcustomer = iDcustomer;
	}

	public int addItems(Menu Item,int qta) {
		if(!Items.containsKey(Item))
			this.Items.put(Item,qta);
		this.Items.merge(Item, qta, (a,b)->a+b);
		return this.Items.get(Item);
	}
	
	public Map<Menu,Integer> getItems(){
		return this.Items;
	}
}
