public class Jogador {
	public int vida = 20;
	public int ataque = 5;
	public int mana = 10;
	
	public Jogador() {
	}
	
	public Jogador(int v, int a, int m) {
		vida = v;
		ataque = a;
		mana = m;
	}
	
	@Override
	public String toString() {
		return "Sua Vida: " + vida + " | Seu Ataque: " + ataque + " | Sua Mana: " + mana;
	}
}
