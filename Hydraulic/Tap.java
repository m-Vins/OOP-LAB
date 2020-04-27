package hydraulic;

import java.util.ArrayList;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	private boolean open;
	
	public Tap(String name) {
		super(name);
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	public void setOpen(boolean open){
		this.open=open;
	}

	/**
	 * retrieve the status of the tap
	 * @return
	 */
	public boolean getStatus() {
		return open;
	}

	@Override
	protected void setNextFlow(SimulationObserver Observer) {
		if(open)
			this.setFlowOut(this.getFlowIn());
		else this.setFlowOut(0.0);
		
		Observer.notifyFlow("Tap", getName(), getFlowIn(), getFlowOut());
		this.getOutput().setFlowIn(this.getFlowOut());
		this.getOutput().setNextFlow(Observer);
		
	}


	@Override
	protected String layoutR(String l, int nSpace, ArrayList<Integer> posSlash) {
		String  ret=l + "-> ["+this.getName()+"]Tap ";
		return this.getOutput().layoutR(ret , ret.length(),posSlash);
	}
	

}
