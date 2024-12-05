import java.util.ArrayList;
import java.util.Random;

public class Campo {
	private ArrayList<Inimigo> inimigos = new ArrayList<>();
	private String nome = "Campo";
	
	Campo(String n){
		nome = n;
	}
	
	public String get_nome() {
		return nome;
	}
	
	public void adicionar_inimigo(Inimigo i) {
		inimigos.add(i);
	}
	
	public Inimigo encontro(Random random) {
		return inimigos.get(random.nextInt(inimigos.size()));
	}
}
