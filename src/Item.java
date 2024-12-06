// Baseado em trabalho feito por Guilherme
import java.util.Random;

public abstract class Item {
	protected String nome;
	protected double[] chanceDrop;
	protected boolean rara;
	
	public double[] getChanceDrop() {
        return chanceDrop;
    }
	
	public void calcularChanceDrop() {
        Random random = new Random();
        int dropChance = random.nextInt(3) + 1;
        switch (dropChance) {
            case 1:
                this.chanceDrop = new double[] {1.0, 0.0, 0.0}; 
                break;
            case 2:
                this.chanceDrop = new double[] {0.0, 1.0, 0.0}; 
                break;
            case 3:
                this.chanceDrop = new double[] {0.0, 0.0, 1.0}; 
                break;
            default:
                this.chanceDrop = new double[] {0.0, 0.0, 0.0}; 
                break;
        }
    }

	public String getNome() {
		return nome;
	}
}
