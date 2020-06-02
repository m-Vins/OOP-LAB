package libreria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Libreria {
    
    TreeMap editori = new TreeMap();
    private Collection libri = new LinkedList();
    private HashSet settimane[] = new HashSet[52];
    private HashSet mesi[] = new HashSet[12];
    private LinkedList ordini = new LinkedList();

    public Editore creaEditore(String nome, int tempoConsegna, String email){
        Editore e = new Editore(nome,tempoConsegna,email);
        editori.put(nome,e);
        return e;
    }

    public Editore getEditore(String nome){
        return (Editore)editori.get(nome);
    }

    public Collection getEditori(){
        return editori.values();
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
        Editore editore = (Editore) editori.get(nomeEditore);
        if(editore==null) 
            	throw new EditoreInesistente();
        Libro l = new Libro(titolo,autore,anno,prezzo,editore,this);
        libri.add(l);
        return l;
    }
    
    public Libro getLibro(String autore, String titolo){
        for (Iterator iter = libri.iterator(); iter.hasNext();) {
            Libro libro = (Libro) iter.next();
            if((autore==null || autore.equals(libro.getAutore()))
               && (titolo==null || titolo.equals(libro.getTitolo()))
               ){
                return libro;
            }
        }
        return null;
    }

    public Collection getClassificaSettimana(final int settimana){
        LinkedList sett = new LinkedList(libri);
        
        Collections.sort(sett,new Comparator(){
            public int compare(Object arg0, Object arg1) {
                Libro a = (Libro)arg0;
                Libro b = (Libro)arg1;
                
                return - a.settimane[settimana-1] + b.settimane[settimana-1];
            }
        });
        return sett;
    }
    
    public Collection getClassificaMese(final int mese){
        LinkedList sett = new LinkedList(libri);
        
        Collections.sort(sett,new Comparator(){
            public int compare(Object arg0, Object arg1) {
                Libro a = (Libro)arg0;
                Libro b = (Libro)arg1;
                
                return - a.mesi[mese-1] + b.mesi[mese-1];
            }
        });
        return sett;
    }
    
    public Collection getOrdini(){
        return ordini;
    }
    
    public void ordineRicevuto(int numOrdine){
        Ordine o = (Ordine)ordini.get(numOrdine);
        
        o.setConsegnato();
    }
    
    public void leggi(String file){
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

    void emettiOrdine(Libro libro, int quantita, Editore editore) {
        Ordine ordine = new Ordine(ordini.size(),libro,quantita,editore);
        ordini.add(ordine);
    }
}
