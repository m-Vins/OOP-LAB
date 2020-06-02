package libreria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Libreria {
	private HashMap<String,Editore> Editori=new HashMap<String,Editore>();
	private HashMap<String,List<Libro>> LibriPerTitolo= new HashMap<String,List<Libro>>();
	private HashMap<String,List<Libro>> LibriPerAutore= new HashMap<String,List<Libro>>();
	private List<Libro> Libri=new ArrayList<Libro>();
	

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
    	if(Editori.containsKey(nomeEditore))
    		throw new EditoreInesistente();
    	Libro ret=new Libro(titolo,autore,anno,prezzo,Editori.get(nomeEditore));
    	
    	if(!LibriPerTitolo.containsKey(titolo)) {
    		LibriPerTitolo.put(titolo,new ArrayList<Libro>());
    	}
    	LibriPerTitolo.get(titolo).add(ret);
    	
    	if(!LibriPerAutore.containsKey(autore)) {
    		LibriPerAutore.put(autore,new ArrayList<Libro>());
    	}
    	LibriPerAutore.get(autore).add(ret);
    	Libri.add(ret);
        return ret;
    }
    
    public Libro getLibro(String autore, String titolo){
    	if(autore==null&&LibriPerTitolo.containsKey(titolo)) {
    		return LibriPerTitolo.get(titolo).get(0);
    	}
    	if(titolo==null&&LibriPerAutore.containsKey(autore)) {
    		return LibriPerAutore.get(autore).get(0);
    	}
    	return LibriPerTitolo.get(titolo).stream().
    			filter(s->s.getAutore().equals(autore)).
    			findFirst().orElse(null);
        
    }
    
    public Collection getClassificaSettimana(final int settimana){
    	return Libri.stream().sorted((a,b)->a.getCopieSettimana(settimana)-b.getCopieSettimana(settimana)).
    	    	collect(Collectors.toList());
    }
    
    public Collection getClassificaMese(final int mese){
    	return Libri.stream().sorted((a,b)->a.getCopieMese(mese)-b.getCopieMese(mese)).
    	collect(Collectors.toList());
    }
    
    public Collection getOrdini(){
        return null;
    }
    
    public void ordineRicevuto(int numOrdine){
    }
    
    public void leggi(String file){
    }
}
