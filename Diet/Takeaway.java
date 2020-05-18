package diet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	private Map<String,Restaurant> Restaurants=new HashMap<String,Restaurant>();
	private SortedSet<User> Users=new TreeSet<User>((a,b)->{
			int ret;
			if((ret=a.getLastName().compareTo(b.getLastName()))==0)
				return a.getFirstName().compareTo(b.getFirstName());
			return ret;});
	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		Restaurants.put(r.getName(),r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		return Restaurants.keySet().stream()
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User ret=new User(firstName, lastName, email, phoneNumber);
		Users.add(ret);
		return ret;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		return this.Users;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Order ret=new Order(user, this.Restaurants.get(restaurantName), h, m);
		this.Restaurants.get(restaurantName).addOrder(ret);
		return 	ret;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		return Restaurants.values()		
				.stream().filter(a->a.checkTime(LocalTime.parse(time)))
				.sorted((a,b)->a.getName().compareTo(b.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	
	
}
