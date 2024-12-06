
public class Restauravel extends Item{
	private boolean afeta_todos = false;
	private int hp_restauracao = 0;
	private int mp_restauracao = 0;
	
	Restauravel(String n, int hp_restaura, int mp_restaura, boolean afeta){
		nome = n;
		hp_restauracao = hp_restaura;
		mp_restauracao = mp_restaura;
		afeta_todos = afeta;
	}
	
	public int restaura_vida() {
		return hp_restauracao;
	}
	
	public int restaura_mp() {
		return mp_restauracao;
	}
	
	public boolean todos() {
		return afeta_todos;
	}
}
