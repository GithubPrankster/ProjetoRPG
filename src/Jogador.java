import java.util.*;

public class Jogador {
	public ArrayList<Item> inventario = new ArrayList<>();
	
	private Armadura armadura_ativa = null;
	private Arma arma_ativa = null;
	
	public int vida = 20;
	public int ataque = 5;
	public int mana = 10;
	public boolean defesa = false;
	
	public Jogador() {
	}
	
	public Jogador(int v, int a, int m) {
		vida = v;
		ataque = a;
		mana = m;
	}
	
	public void equipar_armadura(Armadura armadura) {
		armadura_ativa = armadura;
	}
	
	public void equipar_arma(Arma arma) {
		arma_ativa = arma;
	}
	
	public boolean calcule_dano(int dano) {
		int num_defesa = armadura_ativa != null ? armadura_ativa.getDefesa() : 0;
		float multiplo = (float) (1.0 - (num_defesa / 100.0));
		multiplo *= defesa ? 0.6 : 1;
		
		vida -= (int)(dano * multiplo);
		defesa = false;
		return vida <= 0;
	}
	
	public int calcule_ataque() {
		return arma_ativa != null ? arma_ativa.getDano() : ataque;
	}
	
	@Override
	public String toString() {
		return "Sua Vida: " + vida + " | Seu Ataque: " + ataque + " | Sua Mana: " + mana;
	}
}
