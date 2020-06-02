package it.polito.po.test;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import libreria.Editore;
import libreria.Libreria;

public class TestR1_Editori extends TestCase {

    
    public void test1CreaEditore(){
        Libreria lib = new Libreria();
        
        String nome = "Adelphi";
        int consegna = 5;
        String email = "ordini@adlephi.it";
        Editore a = lib.creaEditore(nome,consegna,email);
        
        assertNotNull("creaEditore() restituisce null",a);
        
        assertEquals(nome,a.getNome());
        assertEquals(consegna,a.getTempoConsegna());
        assertEquals(email,a.getEmail());

    }
    
    public void test2GetEditore(){

        Libreria lib = new Libreria();
        
        String nome = "Adelphi";
        int consegna = 5;
        String email = "ordini@adlephi.it";
        Editore a = lib.creaEditore(nome,consegna,email);
        
        Editore b = lib.getEditore(nome);
        
        assertNotNull("getEditore() restituisce null",b);
        assertSame(a,b);

    }

    public void test2GetEditori(){

        Libreria lib = new Libreria();
        
        Editore a = lib.creaEditore("Adelphi",5,"ordini@adlephi.it");
        Editore b = lib.creaEditore("Mondadori",10,"richieste@mondadori.it");
        Editore c = lib.creaEditore("Garzanti",10,"richieste@garzanti.it");
        

        Collection eds = lib.getEditori();
        
        assertEquals("Dovrebbero esserci 3 elementi",3,eds.size());
        
        String prev = "A";
        for (Iterator iter = eds.iterator(); iter.hasNext();) {
            Editore e = (Editore) iter.next();
            
            assertTrue(prev.compareTo(e.getNome())<=0);
        }
    }
}
