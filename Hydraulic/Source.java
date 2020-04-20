package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	public Source(String name) {
		super(name);
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow){
		this.setFlowIn(flow);
	}

	public void setAllFlow(SimulationObserver Observer) {
		this.setNextFlow(Observer);
	}

	@Override
	protected void setNextFlow(SimulationObserver Observer) {
		this.setFlowOut(this.getFlowIn());
		Observer.notifyFlow("Source", getName(), SimulationObserver.NO_FLOW, this.getFlowOut());
		this.getOutput().setFlowIn(getFlowOut());
		this.getOutput().setNextFlow(Observer);
	}
	

}