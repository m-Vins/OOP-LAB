package it.polito.po.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import junit.framework.TestCase;
import libreria.Editore;
import libreria.Libreria;
import libreria.Libro;

public class TestR5_LetturaFile extends TestCase {

    public void test1Editori(){
        
        String content = "E;Rizzoli;15;consegne@rizzoli.com\n" +
        				 "E;Pearson;7;orders@pearson.co.uk\n"
        				;
        writeFile(file,content);
        
        Libreria lib = new Libreria();
        
        lib.leggi(file);
        
        Collection editori = lib.getEditori();
        
        assertEquals(2,editori.size());
        
        Editore e1 = lib.getEditore("Rizzoli");
        
        assertEquals("Rizzoli",e1.getNome());
        assertEquals("consegne@rizzoli.com",e1.getEmail());
    }

    public void test2Libri(){
        
        String content = "E;Rizzoli;15;consegne@rizzoli.com\n" +
        				 "E;Pearson;7;orders@pearson.co.uk\n" +
        				 "L;Deception Point;Dan Brown;2005;15.0;Rizzoli;100\n"
        				;
        writeFile(file,content);
        
        Libreria lib = new Libreria();
        
        lib.leggi(file);
        
        Libro l = lib.getLibro("Dan Brown",null);
        
        assertNotNull("Non e' stato letto il libro",l);
        
        assertEquals("Deception Point",l.getTitolo());
        assertEquals(2005,l.getAnno());
        assertEquals(lib.getEditore("Rizzoli"),l.getEditore());
        assertEquals(100,l.getQuantita());
    }
    
    public void test3Errori(){
        
        String content = 
            "X;Deception Point;Dan Brown;2005;15.0;Rizzoli;100\n" + // wrong type 
        	"E;Rizzoli;10;\n" + // missing field
        	"E;Rizzoli;hh;consegne@rizzoli.com\n" + // integer format
            "E;Pearson;15;consegne@rizzoli.com\n"
     				;
        writeFile(file,content);
        
        Libreria lib = new Libreria();
        
        lib.leggi(file);
        
        Libro l = lib.getLibro("Dan Brown",null);
        
        assertNull("E' stato letto un libro per sbaglio",l);

        assertEquals(1,lib.getEditori().size());
        
        assertNotNull("Manca l'editore Pearson",lib.getEditore("Pearson"));
    }

	  private static void writeFile(String fileName,String content) {
		  	try{
		  		FileOutputStream fos = new FileOutputStream(fileName);
		  		fos.write(content.getBytes());
		  		fos.close();
			}catch(IOException ioe){
				throw new RuntimeException(ioe.getMessage());
			}
		  }
		  
	private String file = "libreria.txt";

}
