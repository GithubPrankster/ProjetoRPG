import java.util.ArrayList;

public class Cidade {
	private ArrayList<Campo> campos = new ArrayList<>();
	private String nome = "Cidade";
	
	Cidade(String n){
		nome = n;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Campo getCampo(int i) {
		return campos.get(i);
	}
	
	public void adicionar_campo(Campo c) {
		campos.add(c);
	}
}
