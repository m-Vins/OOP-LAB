package diet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	private String name;
	private Food food;
	private SortedSet<TimeTable> TimeTables=new TreeSet<TimeTable>((a,b)->(a.compareTo(b)));
	private Map<String,Menu> Menus=new HashMap<String,Menu>();
	private ArrayList<Order> Orders= new ArrayList<Order>();
	
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name=name;
		this.food=food;
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		if(hm.length%2!=0) {
			System.err.println("Errore formato orari!");
			return;
		}
		for(int i=0;i+1<hm.length;i+=2) {
			TimeTables.add(new TimeTable(hm[i],hm[i+1]));
		}
	}
	
	public Menu getMenu(String name) {
		return Menus.get(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu ret=new Menu(name,new HashMap<String,Recipe>(food.getRecipes()),
				new HashMap<String,Product>(food.getProducts()));
		Menus.put(name,ret);
		return ret;
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		String ret="";
		
		return null;
	}
	
	public void addOrder(Order order) {
		this.Orders.add(order);
	}
	
	
	public LocalTime getDeliveryTime(LocalTime time){
		for(TimeTable x:TimeTables) {
			if(time.isBefore(x.getStart()))
				return x.getStart();
			if(x.checkTime(time))
				return time;
		}
		//nel caso in cui l'ordine viene fatto dopo la chiusura
		//allora la consegna avviena all'apertura del giorno dopo
		return TimeTables.first().getStart();
	}
	
	public boolean checkTime(LocalTime time) {
		for(TimeTable x:TimeTables) 
			if(x.checkTime(time))
				return true;
		return false;
	}
}


	
	
	
	
	
	
	
	