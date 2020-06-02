package libreria;

import java.util.Collection;
import java.util.HashMap;

public class Libreria {
	private HashMap<String,Editore> Editori=new HashMap<String,Editore>();

    public Editore creaEditore(String nome, int tempoConsegna, String email){
    	Editore ret=new Editore(nome,tempoConsegna,email);
    	Editori.put(nome,ret);
        return ret;
    }

    public Editore getEditore(String nome){
        return Editori.get(nome);
    }

    public Collection getEditori(){
        return Editori.values();
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
        return null;
    }
    
    public Libro getLibro(String autore, String titolo){
        return null;
    }
    
    public Collection getClassificaSettimana(final int settimana){
        return null;
    }
    
    public Collection getClassificaMese(final int mese){
        return null;
    }
    
    public Collection getOrdini(){
        return null;
    }
    
    public void ordineRicevuto(int numOrdine){
    }
    
    public void leggi(String file){
    }
}
