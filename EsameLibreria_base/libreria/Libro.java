package libreria;

public class Libro {
	private String Titolo;
	private String Autore;
	private int Anno;
	private double Prezzo;
	private Editore Editore;
	
	private int Qta;
	
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
    }
    

    public void setParametri(int soglia, int quantitaRiordino){   
    }
}
