public class Inimigo {
	private String nome = "Inimigo";
	private int vida = 10;
	private int poder = 1;
	
	public Inimigo(String linha) {
		String[] parts = linha.split("\\|");
		nome = parts[0].trim();
		vida = Integer.parseInt(parts[1].trim());
		poder = Integer.parseInt(parts[2].trim());
	}
	
	public int calcule_dano(int sua_vida, boolean defesa) {
		return (int) (sua_vida - (poder * (defesa ? 0.5 : 1)));
	}
	
	public boolean receba_dano(int dano) {
		vida -= dano;
		return vida <= 0;
	}
	
	public String get_nome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return "Inimigo: " + nome + " | Vida: " + vida;
	}
}
