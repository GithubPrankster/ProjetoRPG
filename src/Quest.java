
public class Quest {
	private String nome = "Quest";
	private String descricao = "Descrição";
	private Item recompensa = null;
	// Necessário para implementar lógica específica para cada Quest.
	private Runnable action = null;
	
	Quest(String n, String d, Item r, Runnable u){
		nome = n;
		descricao = d;
		recompensa = r;
		action = u;
	}
	
	public String get_nome() {
		return nome;
	}
	
	public String get_descricao() {
		return descricao;
	}
	
	public Item get_recompensa() {
		return recompensa;
	}
	
	public void rodar_quest() {
		action.run();
	}
	
}
