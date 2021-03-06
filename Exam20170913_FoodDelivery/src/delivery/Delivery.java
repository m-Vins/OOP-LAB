package delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import java.util.Comparator;


public class Delivery {
    
    public static enum OrderStatus { NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED } 
   
    private HashMap<Integer,Customer> Customers=new HashMap<Integer,Customer>();
    private int nextID=0;
    
    private List<Menu> Menus=new ArrayList<Menu>();
    
    private HashMap<Integer,Order> Orders=new HashMap<Integer,Order>();
    private int nextOrder=0;
    
    /**
     * Creates a new customer entry and returns the corresponding unique ID.
     * 
     * The ID for the first customer must be 1.
     * 
     * 
     * @param name name of the customer
     * @param address customer address
     * @param phone customer phone number
     * @param email customer email
     * @return unique customer ID (positive integer)
     */
    public int newCustomer(String name, String address, String phone, String email) throws DeliveryException {
        if(Customers.values().stream().filter(s->s.getEmail().
        		equals(email)).findAny().isPresent())
        	throw new DeliveryException();
        Customers.put(++nextID, new Customer(nextID,name,address,phone,email));
    	return nextID;
    }
    
    /**
     * Returns a string description of the customer in the form:
     * <pre>
     * "NAME, ADDRESS, PHONE, EMAIL"
     * </pre>
     * 
     * @param customerId
     * @return customer description string
     */
    public String customerInfo(int customerId){
        return Customers.get(customerId).getInfo();
    }
    
    /**
     * Returns the list of customers, sorted by name
     * 
     */
    public List<String> listCustomers(){
        return Customers.values().stream().sorted((a,b)->a.getName().compareTo(b.getName()))
        		.collect(Collectors.mapping(Customer::getInfo, Collectors.toList()));
    }
    
    /**
     * Add a new item to the delivery service menu
     * 
     * @param description description of the item (e.g. "Pizza Margherita", "Bunet")
     * @param price price of the item (e.g. 5 EUR)
     * @param category category of the item (e.g. "Main dish", "Dessert")
     * @param prepTime estimate preparation time in minutes
     */
    public void newMenuItem(String description, double price, String category, int prepTime){
        Menus.add(new Menu(description,price,category,prepTime));
    }
    
    /**
     * Search for items matching the search string.
     * The items are sorted by category first and then by description.
     * 
     * The format of the items is:
     * <pre>
     * "[CATEGORY] DESCRIPTION : PRICE"
     * </pre>
     * 
     * @param search search string
     * @return list of matching items
     */
    public List<String> findItem(String search){
        return Menus.stream().filter(s->s.checkItem(search)).sorted((a,b)->{
        	int ret=a.getCategory().compareTo(b.getCategory());
        	if(ret==0)
        		return a.getDescription().compareTo(b.getDescription());
        	return ret;
        }).collect(Collectors.mapping(Menu::toString,Collectors.toList()));
    }
    
    /**
     * Creates a new order for the given customer.
     * Returns the unique id of the order, a progressive
     * integer number starting at 1.
     * 
     * @param customerId
     * @return order id
     */
    public int newOrder(int customerId){
    	Orders.put(++nextOrder, new Order(nextOrder,customerId));
        return nextOrder;
    }
    
    /**
     * Add a new item to the order with the given quantity.
     * 
     * If the same item is already present in the order is adds the
     * given quantity to the current quantity.
     * 
     * The method returns the final total quantity of the item in the order. 
     * 
     * The item is found through the search string as in {@link #findItem}.
     * If none or more than one item is matched, then an exception is thrown.
     * 
     * @param orderId the id of the order
     * @param search the item search string
     * @param qty the quantity of the item to be added
     * @return the final quantity of the item in the order
     * @throws DeliveryException in case of non unique match or invalid order ID
     */
    public int addItem(int orderId, String search, int qty) throws DeliveryException {
    	List<Menu> Item=Menus.stream().filter(s->s.checkItem(search)).
    			collect(Collectors.toList());
    	if(Item.size()!=1||!Orders.containsKey(orderId))
    		throw new DeliveryException();
        return Orders.get(orderId).addItems(Item.get(0), qty);
    }
    
    /**
     * Show the items of the order using the following format
     * <pre>
     * "DESCRIPTION, QUANTITY"
     * </pre>
     * 
     * @param orderId the order ID
     * @return the list of items in the order
     * @throws DeliveryException when the order ID in invalid
     */
    public List<String> showOrder(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId))
    		throw new DeliveryException();
        return Orders.get(orderId).getItems().entrySet().stream().
        		map(s->s.getKey().getDescription()+", "+s.getValue())
        		.collect(Collectors.toList());
    }
    
    /**
     * Retrieves the total amount of the order.
     * 
     * @param orderId the order ID
     * @return the total amount of the order
     * @throws DeliveryException when the order ID in invalid
     */
    public double totalOrder(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId))
    		throw new DeliveryException();
    	
        return Orders.get(orderId).getItems().entrySet().stream().
            	mapToDouble(s->s.getKey().getPrice()*s.getValue()).sum();
    }
    
    /**
     * Retrieves the status of an order
     * 
     * @param orderId the order ID
     * @return the current status of the order
     * @throws DeliveryException when the id is invalid
     */
    public OrderStatus getStatus(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId))
    		throw new DeliveryException();
        return Orders.get(orderId).getState();
    }
    
    /**
     * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>start-up delay (conventionally 5 min)
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code NEW}
     */
    public int confirm(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId)||!Orders.get(orderId).getState().equals(OrderStatus.NEW))
    		throw new DeliveryException();
    	Orders.get(orderId).setState(OrderStatus.CONFIRMED);
    	return 20+Orders.get(orderId).getItems().keySet().stream().
        		mapToInt(Menu::getPrepTime).max().orElseThrow(()->new DeliveryException());
    }

    /**
     * Start the order preparation. The status goes from {@code CONFIRMED} to {@code PREPARATION}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code CONFIRMED}
     */
    public int start(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId)||!Orders.get(orderId).getState().equals(OrderStatus.CONFIRMED))
    		throw new DeliveryException();
    	Orders.get(orderId).setState(OrderStatus.PREPARATION);
    	return 15+Orders.get(orderId).getItems().keySet().stream().
        		mapToInt(Menu::getPrepTime).max().orElseThrow(()->new DeliveryException());
    }

    /**
     * Begins the order delivery. The status goes from {@code PREPARATION} to {@code ON_DELIVERY}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code PREPARATION}
     */
    public int deliver(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId)||!Orders.get(orderId).getState().equals(OrderStatus.PREPARATION))
    		throw new DeliveryException();
    	Orders.get(orderId).setState(OrderStatus.ON_DELIVERY);
        return 15;
    }
    
    /**
     * Complete the delivery. The status goes from {@code ON_DELIVERY} to {@code DELIVERED}
     * 
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code ON_DELIVERY}
     */
    public void complete(int orderId) throws DeliveryException {
    	if(!Orders.containsKey(orderId)||!Orders.get(orderId).getState().equals(OrderStatus.ON_DELIVERY))
    		throw new DeliveryException();
    	Orders.get(orderId).setState(OrderStatus.DELIVERED);
    }
    
    /**
     * Retrieves the total amount for all orders of a customer.
     * 
     * @param customerId the customer ID
     * @return total amount
     */
    public double totalCustomer(int customerId){
        return Orders.values().stream().filter(s->!s.getState().
        		equals(OrderStatus.NEW)).filter(s->s.getIDcustomer()==(customerId))
        		.flatMap(s->s.getItems().entrySet().stream()).mapToDouble(s->s.getKey().getPrice()*s.getValue())
        		.sum();
    }
    
    /**
     * Computes the best customers by total amount of orders.
     *  
     * @return the classification
     */
    public SortedMap<Double,List<String>> bestCustomers(){
        return Customers.values().stream().collect(Collectors.groupingBy(
    			s->this.totalCustomer(s.getID()),
    			()->new TreeMap<Double,List<String>>(Comparator.reverseOrder()),
    			Collectors.mapping(s->s.getInfo(), Collectors.toList())
    			));
    }
    
// NOT REQUIRED
//
//    /**
//     * Computes the best items by total amount of orders.
//     *  
//     * @return the classification
//     */
//    public List<String> bestItems(){
//        return null;
//    }
//    
//    /**
//     * Computes the most popular items by total quantity ordered.
//     *  
//     * @return the classification
//     */
//    public List<String> popularItems(){
//        return null;
//    }

}
