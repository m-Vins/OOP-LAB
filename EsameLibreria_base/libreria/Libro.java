package libreria;

import java.util.HashMap;

public class Libro {
	private String Titolo;
	private String Autore;
	private int Anno;
	private double Prezzo;
	private Editore Editore;
	
	private int Qta;
	
	private HashMap<Integer,Integer> CopiePerSettimana=new HashMap<Integer,Integer>();
	private HashMap<Integer,Integer> CopiePerMese=new HashMap<Integer,Integer>();
	
	public Libro(String titolo,String autore,int anno, double prezzo, Editore editore) {
		this.Titolo=titolo;
		this.Autore=autore;
		this.Anno=anno;
		this.Prezzo=prezzo;
		this.Editore=editore;
	}

    public String getTitolo(){
        return this.Titolo;
    }
    
    public String getAutore(){
        return this.Autore;
    }
    
    public int getAnno(){
        return this.Anno;
    }

    public double getPrezzo(){
        return this.Prezzo;
    }
    
    public Editore getEditore(){
        return this.Editore;
    }

    public void setQuantita(int q){   
    	this.Qta=q;
    }
    
    public int getQuantita(){
        return this.Qta;	
    }

    public void registraVendita(int settimana, int mese){
    	if(!CopiePerSettimana.containsKey(settimana))
    		CopiePerSettimana.put(settimana, 1);
    	else
    		CopiePerSettimana.compute(settimana,(k,v)->(v+1));
    	
    	if(!CopiePerMese.containsKey(mese))
    		CopiePerMese.put(mese, 1);
    	else
    		CopiePerMese.compute(mese,(k,v)->(v+1));
    	
    	this.Qta--; 	
    }
    
    public int getCopieSettimana(int settimana) {
    	return CopiePerSettimana.get(settimana);
    }
    
    
    public int getCopieMese(int mese) {
    	return CopiePerSettimana.get(mese);
    }
    
    
    public void setParametri(int soglia, int quantitaRiordino){   
    }
}
