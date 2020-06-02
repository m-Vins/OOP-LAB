package it.polito.po.test;

import junit.framework.TestCase;
import libreria.Editore;
import libreria.EditoreInesistente;
import libreria.Libreria;
import libreria.Libro;
import libreria.Ordine;

public class TestR4_Ordini extends TestCase {

    public void test1GeneraOrdine() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");
        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        
        l1.setQuantita(10);
        l2.setQuantita(15);

        l1.setParametri(8,10);

        int numOrdiniPre = lib.getOrdini().size();
        
        l1.registraVendita(1,1);
        l2.registraVendita(1,1);
        l2.registraVendita(1,1);

        l1.registraVendita(3,1);
        
        int numOrdiniPost = lib.getOrdini().size();
        l1.registraVendita(4,1);
        l1.registraVendita(4,1);
        int numOrdiniPostPost = lib.getOrdini().size();
        
        assertEquals("Dovrebbe essere stato generato un ordine",
                	1,numOrdiniPost-numOrdiniPre);
        assertEquals("E' stato generato un ordine di troppo",
            	numOrdiniPost,numOrdiniPostPost);
    }
    
    public void test2Ordine() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        
        l1.setQuantita(10);
        l2.setQuantita(20);
        l1.setParametri(8,10);
        l2.setParametri(10,5);

        l1.registraVendita(1,1);
        l2.registraVendita(1,1);
        l2.registraVendita(1,1);

        l1.registraVendita(3,1);
        
        Ordine ordine = (Ordine) lib.getOrdini().iterator().next();
        
        assertSame(l1,ordine.getLibro());
        assertSame(a,ordine.getEditore());
        assertEquals(10,ordine.getQuantita());
    }

    public void test3RicezioneOrdine() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        
        l1.setQuantita(10);
        l2.setQuantita(20);
        l1.setParametri(8,10);
        l2.setParametri(10,5);

        l1.registraVendita(1,1);
        l2.registraVendita(1,1);
        l2.registraVendita(1,1);

        l1.registraVendita(3,1);
        
        Ordine ordine = (Ordine) lib.getOrdini().iterator().next();
        
        assertFalse("Inizialmente l'ordine deve essere NON consegnato",
                	ordine.isConsegnato());
        
        lib.ordineRicevuto(ordine.getNumero());
        
        assertTrue("L'ordine dovrebbe essere stato consegnato",
                ordine.isConsegnato());
        
    }

}
