import java.util.*;

public class Jogador {
	public ArrayList<Item> inventario = new ArrayList<>();
	public ArrayList<Magia> feitiços = new ArrayList<>();
	
	private Armadura armadura_ativa = null;
	private Arma arma_ativa = null;
	
	private int exp = 0;
	private int vida_max = 20;
	private int mana_max = 10;
	
	public int vida = 20;
	public int ataque = 5;
	public int mana = 10;
	
	public boolean defesa = false;
	
	public Jogador() {
		feitiços.add(new Magia("Bola de Fogo", 10, 3));
	}
	
	public Jogador(int v, int a, int m) {
		this();
		vida = v;
		ataque = a;
		mana = m;
		
		vida_max = v;
		mana_max = m;
	}
	
	public void restauração() {
		vida = vida_max;
		mana = mana_max;
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

	public int getExp() {
		return exp;
	}
}
