package hydraulic;

import java.util.ArrayList;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	Element[] Elements;
	double[] Proportions;
	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name); 
		this.Elements= new Element[numOutput];
		this.Proportions=new double[numOutput];
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
		if(noutput>=this.Elements.length||noutput<0) {			
			System.err.println("NUMERO USCITA NON VALIDO");
			return;
		}
		this.Elements[noutput]=elem;
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		//TODO:
	}
	
	@Override
	protected String layoutR(String l, int nSpace, ArrayList<Integer> posSlash) {
		String ret=" -> ["+this.getName()+"]Split +";
		int length=nSpace+ret.length()-1;
		posSlash.add(length);
		ret=l+ret;
		for(int i=0;i<Elements.length;i++) {
			if(Elements[i]!=null) {
				ret = this.Elements[i].layoutR(ret, length,posSlash);
				if(i+1<Elements.length&&Elements[i+1]!=null) {
					for(int j=0,pos=0;j<length;j++) {
						if(posSlash.get(pos)==j) {
							ret+="|";
							pos++;
						}else ret+=" ";
					}
					ret+= "|\n";
					for(int j=0,pos=0;j<length;j++) {
						if(posSlash.get(pos)==j) {
							ret+="|";
							pos++;
						}else ret+=" ";
					}
					ret+="+";
				}
			}
		}
		posSlash.remove(posSlash.size()-1);
		return ret;
	}
	
}
