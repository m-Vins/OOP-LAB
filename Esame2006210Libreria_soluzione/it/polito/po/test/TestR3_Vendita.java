package it.polito.po.test;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import libreria.Editore;
import libreria.EditoreInesistente;
import libreria.Libreria;
import libreria.Libro;

public class TestR3_Vendita extends TestCase {

    public void test1RegistraVendita() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Mondadori",5,"ordini@adlephi.it");
        Libro l = lib.creaLibro("Codice da Vinci","Brown",2005,8.5,"Mondadori");

        l.setQuantita(100);
        
        l.registraVendita(1,1);
        l.registraVendita(1,2);
        
        assertEquals(98,l.getQuantita());
    }

    public void test2ClassificaSettimana() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");
        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        
        l1.setParametri(8,10);

        l1.setQuantita(10);
        l2.setQuantita(15);
        
        l1.registraVendita(1,1);
        l2.registraVendita(1,1);
        l2.registraVendita(1,1);

        l1.registraVendita(3,1);
        l1.registraVendita(4,1);
        l1.registraVendita(4,1);
        
        Collection sett = lib.getClassificaSettimana(1);
        
        
        Iterator iter = sett.iterator();
        assertSame(l2,iter.next());
        assertSame(l1,iter.next());
    }
    
    public void test3ClassificaMese() throws EditoreInesistente{
        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");
        
        Libro l1 = lib.creaLibro("Siddartha","Hesse",2000,5.5,"Adelphi");
        Libro l2 = lib.creaLibro("Promessi Sposi","Manzoni",1995,10.2,"Mondadori");
        
        l1.setParametri(8,10);

        l1.setQuantita(100);
        l2.setQuantita(150);
        
        l1.registraVendita(1,1);
        l2.registraVendita(1,1);
        l2.registraVendita(1,1);

        l1.registraVendita(3,1);
        l1.registraVendita(4,1);
        l1.registraVendita(4,1);
        
        Collection mese = lib.getClassificaMese(1);
        
        
        Iterator iter = mese.iterator();
        assertSame(l1,iter.next());
        assertSame(l2,iter.next());
    }
}
