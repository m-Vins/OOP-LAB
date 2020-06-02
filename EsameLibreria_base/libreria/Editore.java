package libreria;

public class Editore {
	private String Nome;
	private int TempoConsegna;
	private String Email;
	
	public Editore(String nome, int TC, String Email) {
		this.Nome=nome;
		this.TempoConsegna=TC;
		this.Email=Email;
	}
    
    public String getNome(){
        return this.Nome;
    }
    
    public int getTempoConsegna(){
        return this.TempoConsegna;
    }
    
    public String getEmail(){
        return this.Email;
    }
 
}
