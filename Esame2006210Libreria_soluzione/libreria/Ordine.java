package libreria;

public class Ordine {

    private int numero;
    private Libro libro;
    private int quantita;
    private Editore editore;
    private boolean consegnato;

    /**
     * @param i
     * @param libro
     * @param quantita
     * @param editore
     */
    public Ordine(int i, Libro libro, int quantita, Editore editore) {
        this.numero = i;
        this.libro = libro;
        this.quantita = quantita;
        this.editore = editore;
    }

    public Editore getEditore(){
        return editore;
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public int getQuantita(){
        return quantita;
    }

    public boolean isConsegnato(){
        return consegnato;
    }

    public int getNumero(){
        return numero;
    }

    /**
     * 
     */
    void setConsegnato() {
        if(this.consegnato){
            System.err.println("Ordine " + numero + " gia' consegnato!");
            return;
        }
        libro.incrementa(quantita);
        this.consegnato = true;
    }
}
