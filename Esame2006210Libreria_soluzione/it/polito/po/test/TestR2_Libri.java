package it.polito.po.test;

import junit.framework.TestCase;
import libreria.Editore;
import libreria.EditoreInesistente;
import libreria.Libreria;
import libreria.Libro;

public class TestR2_Libri extends TestCase {

    public void test1CreaLibro() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        String editore = "Adelphi";
        Editore a = lib.creaEditore(editore,5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");

        String titolo = "Umano Troppo Umano";
        String autore = "Nietzsche";
        int anno = 1988;
        double prezzo = 25.0;
        Libro l = lib.creaLibro(titolo,autore,anno,prezzo,editore);
        
        assertNotNull("creaLibro restituisce null",l);
        
        assertEquals(titolo,l.getTitolo());
        assertEquals(autore,l.getAutore());
        assertEquals(prezzo,l.getPrezzo(),0.001);
        assertEquals(anno,l.getAnno());
    }

    public void test2Quantita() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        String editore = "Adelphi";
        Editore a = lib.creaEditore(editore,5,"ordini@adlephi.it");

        String titolo = "Umano Troppo Umano";
        String autore = "Nietzsche";
        int anno = 1988;
        double prezzo = 25.0;
        Libro l = lib.creaLibro(titolo,autore,anno,prezzo,editore);
        
        l.setQuantita(100);
        assertEquals(100,l.getQuantita());
    }
    
    public void test3Errore() {
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        
        try {
            Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Rizzoli");
            fail("L'editore non esiste, mancata eccezione");
        } catch (EditoreInesistente e) {
            assertTrue(true);
        }        
    }

    public void test4Ricerca() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");
        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        Libro l3 = lib.creaLibro("Divina Commendia","Alighieri",1300,45.5,"Adelphi");
        Libro l4 = lib.creaLibro("Codice da Vinci","Brown",2005,8.5,"Mondadori");
        
        Libro lr1 = lib.getLibro("Hesse","Siddartha");
        assertNotNull(lr1);
        assertSame(l1,lr1);
        assertSame(l2,lib.getLibro(null,"Promessi Sposi"));
        assertSame(l4,lib.getLibro("Brown",null));
    }
}
