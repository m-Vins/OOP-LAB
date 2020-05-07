package hydraulic;

import java.util.ArrayList;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {

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
	
	
	public void setAllFlowExt(SimulationObserverExt Observer, boolean enableMaxFlowCheck) {
		this.setNextFlow(Observer,enableMaxFlowCheck);
	}
	

	@Override
	protected void setNextFlow(SimulationObserver Observer) {
		this.setFlowOut(this.getFlowIn());
		Observer.notifyFlow("Source", getName(), SimulationObserver.NO_FLOW, this.getFlowOut());
		this.getOutput().setFlowIn(getFlowOut());
		this.getOutput().setNextFlow(Observer);
	}

	public String layoutWrapper() {
		return this.layoutR("", 0, new ArrayList<Integer>());
	}
	
	@Override
	protected String layoutR(String l, int nSpace, ArrayList<Integer> posSlash) {
		String ret= l+" ["+this.getName()+"]Source";
		return this.getOutput().layoutR(ret, ret.length(),posSlash);
	}

	@Override
	protected void setNextFlow(SimulationObserverExt Observer, boolean enableMaxFlowCheck) {
		this.setFlowOut(this.getFlowIn());
		Observer.notifyFlow("Source", getName(), SimulationObserver.NO_FLOW, this.getFlowOut());
		this.getOutput().setFlowIn(getFlowOut());
		((ElementExt)this.getOutput()).setNextFlow(Observer,enableMaxFlowCheck);
		
	}
	

}
