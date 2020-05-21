package diet;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an order in the take-away system
 */
public class Order {
	private Restaurant Restaurant;
	private User user;
	private LocalTime Delivery;
	private OrderStatus Status;
	private PaymentMethod Payment;
	private Map<Menu,Integer> Menus=new HashMap<Menu,Integer>();
	
	
	public Order(User user,Restaurant restaurant,int hh, int mm) {
		this.user=user;
		this.Restaurant=restaurant;
		this.Delivery=restaurant.getDeliveryTime(LocalTime.of(hh, mm));
		this.setStatus(OrderStatus.ORDERED);
		this.setPaymentMethod(PaymentMethod.CASH);
	}
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
		
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		Payment=method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.Payment;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.Status=newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return this.Status;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		if(this.Menus.containsKey(Restaurant.getMenu(menu)))
			this.Menus.remove(Restaurant.getMenu(menu));
		this.Menus.put(Restaurant.getMenu(menu), quantity);
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		String ret= Restaurant.getName()+", "+
					user.getFirstName()+" "+
					user.getLastName()+" : "+
					"("+Delivery.toString()+")"+
					":\n";
		return Menus.keySet().stream().map(a->("\t"+a.getName()+"->"+Menus.get(a)+"\n"))
				.reduce(ret,(a,b)->(a+b));
	}
	
	public boolean checkStatus(OrderStatus Status) {
		return this.getStatus()==Status ? true:false;
	}
	
	
}
