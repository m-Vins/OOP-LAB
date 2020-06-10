package ticketing;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ticketing.Ticket.State;


public class IssueManager {
	private Map<String,User> Users=new HashMap<String,User>();
	private Map<String,Component> Components=new HashMap<String,Component>();
	private Map<Integer,Ticket> Tickets=new HashMap<Integer,Ticket>();
	
	private int nextTicket=0;

    /**
     * Eumeration of valid user classes
     */
    public static enum UserClass {
        /** user able to report an issue and create a corresponding ticket **/
        Reporter, 
        /** user that can be assigned to handle a ticket **/
        Maintainer }
    
    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, UserClass... classes) throws TicketException {
    	if(Users.containsKey(username)||classes.length==0) throw new TicketException();
    	Set<UserClass> s=new HashSet<UserClass>();
    	for(UserClass x:classes) {
    		s.add(x);
    	}
    	Users.put(username,new User(username,s));
    }

    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, Set<UserClass> classes) throws TicketException {
    	if(Users.containsKey(username)||classes.size()==0) throw new TicketException();
    	Users.put(username, new User(username,classes));
    }
   
    /**
     * Retrieves the user classes for a given user
     * 
     * @param username name of the user
     * @return the set of user classes the user belongs to
     */
    public Set<UserClass> getUserClasses(String username){
        return Users.get(username).getUserClasses();
    }
    
    /**
     * Creates a new component
     * 
     * @param name unique name of the new component
     * @throws TicketException if a component with the same name already exists
     */
    public void defineComponent(String name) throws TicketException {
        if(Components.values().stream().filter(s->s.getName().equals(name)).
        		findFirst().isPresent()) 
        	throw new TicketException();
        
        Component c=new Component(name);
        c.setPath("/"+name);
        Components.put(c.getPath(),c);
    }
    
    /**
     * Creates a new sub-component as a child of an existing parent component
     * 
     * @param name unique name of the new component
     * @param parentPath path of the parent component
     * @throws TicketException if the the parent component does not exist or 
     *                          if a sub-component of the same parent exists with the same name
     */
    public void defineSubComponent(String name, String parentPath) throws TicketException {
        if(!Components.containsKey(parentPath))
        	throw new TicketException();
        Component c=new Component(name);
        Components.get(parentPath).addSubComponent(c);
        Components.put(c.getPath(),c);
    }
    
    /**
     * Retrieves the sub-components of an existing component
     * 
     * @param path the path of the parent
     * @return set of children sub-components
     */
    public Set<String> getSubComponents(String path){
        return this.Components.get(path).getSubComponent()
        		.stream().map(Component::getName)
        		.collect(Collectors.toSet());
    }

    /**
     * Retrieves the parent component
     * 
     * @param path the path of the parent
     * @return name of the parent
     */
    public String getParentComponent(String path){
    	Component c=Components.get(path);
    	if(c==null)
    		return null;
    	c=c.getParent();
    	if(c==null)
    		return null;
    
        return c.getPath();
    }

    /**
     * Opens a new ticket to report an issue/malfunction
     * 
     * @param username name of the reporting user
     * @param componentPath path of the component or sub-component
     * @param description description of the malfunction
     * @param severity severity level
     * 
     * @return unique id of the new ticket
     * 
     * @throws TicketException if the user name is not valid, the path does not correspond to a defined component, 
     *                          or the user does not belong to the Reporter {@link IssueManager.UserClass}.
     */
    public int openTicket(String username, String componentPath, String description, Ticket.Severity severity) throws TicketException {
        if(!Components.containsKey(componentPath)||!Users.containsKey(username)||
        		!Users.get(username).getUserClasses().contains(UserClass.Reporter))
        		throw new TicketException();
        Tickets.put(++nextTicket, new Ticket(description, severity, Components.get(componentPath).getName(), nextTicket,Users.get(username).getUsername()));
    	return nextTicket;
    }
    
    /**
     * Returns a ticket object given its id
     * 
     * @param ticketId id of the tickets
     * @return the corresponding ticket object
     */
    public Ticket getTicket(int ticketId){
        return Tickets.get(ticketId);
    }
    
    /**
     * Returns all the existing tickets sorted by severity
     * 
     * @return list of ticket objects
     */
    public List<Ticket> getAllTickets(){
        return Tickets.values().stream()
        		.sorted((a,b)->a.getSeverity().compareTo(b.getSeverity()))
        		.collect(Collectors.toList());
    }
    
    /**
     * Assign a maintainer to an open ticket
     * 
     * @param ticketId  id of the ticket
     * @param username  name of the maintainer
     * @throws TicketException if the ticket is in state <i>Closed</i>, the ticket id or the username
     *                          are not valid, or the user does not belong to the <i>Maintainer</i> user class
     */
    public void assingTicket(int ticketId, String username) throws TicketException {
        if(!Tickets.containsKey(ticketId)||!Users.containsKey(username)||
        		!Users.get(username).getUserClasses().contains(UserClass.Maintainer)||
        		Tickets.get(ticketId).getState().equals(State.Closed))
        	throw new TicketException();
        Ticket t= Tickets.get(ticketId);
        t.setState(Ticket.State.Assigned).setAuthor(Users.get(username).getUsername());
    }

    /**
     * Closes a ticket
     * 
     * @param ticketId id of the ticket
     * @param description description of how the issue was handled and solved
     * @throws TicketException if the ticket is not in state <i>Assigned</i>
     */
    public void closeTicket(int ticketId, String description) throws TicketException {
        if(!Tickets.containsKey(ticketId)||!Tickets.get(ticketId).getState().equals(State.Assigned))
        	throw new TicketException();
        Tickets.get(ticketId).setState(State.Closed).setSolution(description);
    }

    /**
     * returns a sorted map (keys sorted in natural order) with the number of  
     * tickets per Severity, considering only the tickets with the specific state.
     *  
     * @param state state of the tickets to be counted, all tickets are counted if <i>null</i>
     * @return a map with the severity and the corresponding count 
     */
    public SortedMap<Ticket.Severity,Long> countBySeverityOfState(Ticket.State state){
    	if(state==null) {
    		return Tickets.values().stream().collect(Collectors.
        			groupingBy(Ticket::getSeverity,()->new TreeMap<Ticket.Severity,Long>(),
        					Collectors.counting()));
    	}
    	return Tickets.values().stream().filter(s->s.getState()==state).collect(Collectors.
    			groupingBy(Ticket::getSeverity,()->new TreeMap<Ticket.Severity,Long>(),
    					Collectors.counting()));
    }

    /**
     * Find the top maintainers in terms of closed tickets.
     * 
     * The elements are strings formatted as <code>"username:###"</code> where <code>username</code> 
     * is the user name and <code>###</code> is the number of closed tickets. 
     * The list is sorter by descending number of closed tickets and then by username.

     * @return A list of strings with the top maintainers.
     */
    public List<String> topMaintainers(){
    	return Tickets.values().stream().filter(s->s.getState()==State.Closed)
    	.collect(Collectors.groupingBy(Ticket::getAuthor,Collectors.counting()))
    	.entrySet().stream().sorted((a,b)->{
    		return b.getValue().equals(a.getValue()) ? a.getKey().compareTo(b.getKey()):b.getValue().compareTo(a.getValue());
    	})
    	.map(s->s.getKey().toString()+":"+s.getValue().toString()).
    	collect(Collectors.toList());
    }

}
