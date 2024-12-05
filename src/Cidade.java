import java.util.ArrayList;

public class Cidade {
	public ArrayList<Campo> campos = new ArrayList<>();
	private String nome = "Cidade";
	
	Cidade(String n){
		nome = n;
	}
	
	public String getNome() {
		return nome;
	}
}
