package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	private Element[] Elements;
	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		Elements=new Element[2];
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	return this.Elements;
    	    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		this.Elements[noutput]=elem;
	}

	@Override
	protected void setNextFlow(SimulationObserver Observer) {
		this.setFlowOut(this.getFlowIn()/2);
		Observer.notifyFlow("Split", getName(), getFlowIn(), getFlowOut(), getFlowOut());
		this.Elements[0].setFlowIn(this.getFlowOut());
		this.Elements[0].setNextFlow(Observer);
		this.Elements[1].setFlowIn(this.getFlowOut());
		this.Elements[1].setNextFlow(Observer);
	}
	
}
