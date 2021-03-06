package hydraulic;

import java.util.ArrayList;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
	}
	
	
	@Override
	public void connect(Element elem) {
		System.err.println("\nCANNOT BE CONNECT AN ELEMENT AFTER SINK\n");
		return;
	}


	@Override
	protected void setNextFlow(SimulationObserver Observer) {
		this.setFlowOut(this.getFlowIn());
		Observer.notifyFlow("Sink", this.getName(), this.getFlowIn(), SimulationObserver.NO_FLOW);
	}



	@Override
	protected String layoutR(String l, int nSpace, ArrayList<Integer> posSlash) {
		return l+ "-> ["+this.getName()+"]Sink *\n";
	}


	@Override
	protected void setNextFlow(SimulationObserverExt Observer, boolean enableMaxFlowCheck) {
		this.setFlowOut(this.getFlowIn());
		if(this.getFlowIn()>this.getMaxFlow())	Observer.notifyFlowError("Source", getName(), getFlowIn(), getMaxFlow());
		Observer.notifyFlow("Sink", this.getName(), this.getFlowIn(), SimulationObserver.NO_FLOW);
		
	}
	

}
