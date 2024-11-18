
public class Magia {
	private String nome = "Magia";
	private int poder = 10;
	private int custo = 4;
	
	public Magia(String n, int p, int c) {
		nome = n;
		poder = p;
		custo = c;
	}

	public String getNome() {
		return nome;
	}

	public int getPoder() {
		return poder;
	}

	public int getCusto() {
		return custo;
	}
}
