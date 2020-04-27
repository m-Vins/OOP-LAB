package hydraulic;

import java.util.ArrayList;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	private String name;
	private Element next;
	private double FlowIn;
	private double FlowOut;
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	public Element(String name){
		this.name=name;
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem){
		this.next=elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		return this.next;
	}


	public double getFlowOut() {
		return FlowOut;
	}

	protected void setFlowOut(double flowOut) {
		FlowOut = flowOut;
	}

	public double getFlowIn() {
		return FlowIn;
	}

	protected void setFlowIn(double flowIn) {
		FlowIn = flowIn;
	}
	
	
	protected abstract void setNextFlow(SimulationObserver Observer);
	
	protected abstract String layoutR(String l, int nSpace, ArrayList<Integer> posSlash);
}
