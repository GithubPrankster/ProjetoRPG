
public class Quest {
	private String nome = "Quest";
	private String descricao = "Descrição";
	private Item recompensa = null;
	
	Quest(String n, String d, Item r){
		nome = n;
		descricao = d;
		recompensa = r;
	}
	
	public String get_nome() {
		return nome;
	}
	
	public String get_descricao() {
		return descricao;
	}
}
