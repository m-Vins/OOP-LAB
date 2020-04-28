package hydraulic;


/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String ret="";
		for(Element x:this.getElements()) {
			if(x instanceof Source) 
				ret=((Source) x).layoutWrapper();
		}
		return ret;
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		Element Elemento=null;
		
		for(Element x:Elements)
			if(x.getName().equals(name)) Elemento=x;
		//se non trova niente ritorna
		if(Elemento==null) return;
		
		if(Elemento instanceof Split || Elemento instanceof Multisplit) {
			Elements.remove(Elemento);
			return;
		}
		
		for(Element x:Elements)
			if(x.getOutput()==Elemento) {
				x.connect(Elemento.getOutput());
				Elements.remove(Elemento);
				return;
			}
		
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {		
		for(Element x : Elements) {
			if(x==null) break;
			if(x instanceof Source) {
				((Source) x).setAllFlowExt(observer,enableMaxFlowCheck);
			}
		}
	}
	
	public void simulate(SimulationObserverExt observer) {		
		for(Element x : Elements) {
			if(x==null) break;
			if(x instanceof Source) {
				((Source) x).setAllFlowExt(observer,false);
			}
		}
	}
	
}
