package libreria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Libreria {
	private HashMap<String,Editore> Editori=new HashMap<String,Editore>();
	private HashMap<String,List<Libro>> LibriPerTitolo= new HashMap<String,List<Libro>>();
	private HashMap<String,List<Libro>> LibriPerAutore= new HashMap<String,List<Libro>>();
	private List<Libro> Libri=new ArrayList<Libro>();
	private HashMap<Integer,Ordine> Ordini=new HashMap<Integer,Ordine>();
	private int nextOrder=-1;
	

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
    	if(!Editori.containsKey(nomeEditore)) {
    		throw new EditoreInesistente();
    	}
    	Libro ret=new Libro(titolo,autore,anno,prezzo,Editori.get(nomeEditore),this);
    	
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
    
    public void newOrder(Libro Libro,Editore Editore,int Qta) {
    	Ordine o=new Ordine(Editore,Libro,Qta,++nextOrder);
    	Ordini.put(nextOrder,o);
    }
    
    public Collection getOrdini(){
        return this.Ordini.values();
    }
    
    public void ordineRicevuto(int numOrdine){
    	this.Ordini.get(numOrdine).setConsegnato();
    	this.Ordini.get(numOrdine).getLibro().incrementaQta();
    }
    
    public void leggi(String file){
    	try(BufferedReader reader=new BufferedReader(new FileReader(file))){
    		String riga;
    		while((riga=reader.readLine())!=null) {
    			try{
    			String[] subString=riga.split(";");
    			if(subString[0].equals("E")) {
    				this.creaEditore(subString[1],Integer.parseInt(subString[2]),subString[3]);
    			}else if(subString[0].equals("E")) {
    				this.creaLibro(subString[1], subString[2], Integer.parseInt(subString[3]), Double.parseDouble(subString[4]), subString[5]);
    			}
    			}catch(EditoreInesistente e) {
    				
    			}catch(NoSuchElementException e) {
    				
    			}catch(NumberFormatException e) {
    				
    			}
    		}
    		
    	}catch(FileNotFoundException e) {
    		
    	}catch(IOException e) {
    		  
    	} 
    }
}
