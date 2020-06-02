package libreria;

public class Ordine {
	private Editore Editore;
	private Libro Libro;
	private int Qta;
	private boolean Consegnato;
	private int Numero;

	public Ordine(Editore editore,Libro libro,int qta, int numero){
		this.Editore=editore;
		this.Libro=libro;
		this.Qta =qta;
		this.Consegnato=false;
		this.Numero=numero;
	}
	
    public Editore getEditore(){
        return Editore;
    }
    
    public Libro getLibro(){
        return Libro;
    }
    
    public int getQuantita(){
        return Qta;
    }

    public boolean isConsegnato(){
        return Consegnato;
    }

    public int getNumero(){
        return Numero;
    }
    
    public void setConsegnato() {
    	this.Consegnato=true;
    }
}
