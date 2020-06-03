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
import java.util.StringTokenizer;
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
    	if(autore==null) {
    		if(LibriPerTitolo.containsKey(titolo))
    			return LibriPerTitolo.get(titolo).get(0);
    		return null;
    	}
    	if(titolo==null) {
    		if(LibriPerAutore.containsKey(autore))
    			return LibriPerAutore.get(autore).get(0);
    		return null;
    	}
    	return Libri.stream().
    			filter(s->s.getAutore().equals(autore)).
    			findFirst().orElse(null);
        
    }
    
    public Collection getClassificaSettimana(final int settimana){
    	List<Libro> ret=new ArrayList<Libro>(Libri);
    	ret.sort((a,b)->b.getCopieSettimana(settimana)-a.getCopieSettimana(settimana));
    	return ret;
    }
    
    public Collection getClassificaMese(final int mese){
    	List<Libro> ret=new ArrayList<Libro>(Libri);
    	ret.sort((a,b)->b.getCopieMese(mese)-a.getCopieMese(mese));
    	return ret;
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
//    	try(BufferedReader reader=new BufferedReader(new FileReader(file))){
//    		String riga;
//    		while((riga=reader.readLine())!=null) {
//    			try{
//    			String[] subString=riga.split(";");
//    			if(subString[0].equals("E")) {
//    				this.creaEditore(subString[1],Integer.parseInt(subString[2]),subString[3]);
//    			}else if(subString[0].equals("L")) {
//    				this.creaLibro(subString[1], subString[2], Integer.parseInt(subString[3]), Double.parseDouble(subString[4]), subString[5])
//    				.setQuantita(Integer.parseInt(subString[6]));
//    			} 
//    				
//    			}catch(EditoreInesistente e) {
//    				
//    			}catch(NoSuchElementException e) {
//    				
//    			}catch(NumberFormatException e) {
//    				
//    			}catch(NullPointerException e) {
//    				
//    			}
//    		}
//    		
//    	}catch(FileNotFoundException e) {
//    		
//    	}catch(IOException e) {
//    		  
//    	} 
    	
    	 try{
 			BufferedReader reader = new BufferedReader(new FileReader(file));
 			
 			String line;
 			while((line=reader.readLine())!=null){
 				StringTokenizer tok = new StringTokenizer(line,";");
 				try {
 				String type = tok.nextToken();
 				
 				if(type.equals("E")){
 				    String nome = tok.nextToken();
 				    int tempo = Integer.parseInt(tok.nextToken());
 				    String email = tok.nextToken();
 				    
 				    this.creaEditore(nome,tempo,email);
 				}else
 				if(type.equals("L")){
 				    String titolo = tok.nextToken();
 				    String autore = tok.nextToken();
 				    int anno = Integer.parseInt(tok.nextToken());
 				    double prezzo = Double.parseDouble(tok.nextToken());
 				    String editore = tok.nextToken();
 				    int quantita = Integer.parseInt(tok.nextToken());
 				    
 				    Libro l = this.creaLibro(titolo,autore,anno,prezzo,editore);
 				    l.setQuantita(quantita);
 				}
 				
 				} catch (NoSuchElementException e) {
 					System.err.println("Ignoring wrong line: " + e);
 				} catch (NumberFormatException e) {
 					System.err.println("Ignoring wrong line: " + e);
 				} catch (EditoreInesistente e) {
 					System.err.println("Ignoring wrong line: " + e);
                 }
 			}
 	} catch (FileNotFoundException e) {
 		System.err.println("Ignoring wrong line");
 	} catch (IOException e) {
 		System.err.println("Ignoring wrong line");
 	}
    }
}
