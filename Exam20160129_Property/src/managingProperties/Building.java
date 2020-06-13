package managingProperties;

import java.util.ArrayList;

public class Building {
	private String id;
	private int n;
	private String[] Owners;
	
	public Building(String id, int n) throws PropertyException{
		this.id=id;
		if(n>100||n<0)
			throw new PropertyException("numero appartamento non valido");
		this.setN(n);
		this.Owners=new String[n];
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getId() {
		return id;
	}

	public void addOwners(String owner,int index) throws PropertyException{
		if(index<0||index>this.n||Owners[index]!=null)
			throw new PropertyException();
		Owners[index]=owner;
	}
}
