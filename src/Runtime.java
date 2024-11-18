import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class Runtime {
	final static String monstros_texto = "monstros.txt";
	
	static Random random = new Random();
	static Scanner entrada = new Scanner(System.in);
	
	static ArrayList<Inimigo> inimigos = new ArrayList<>();
	static Jogador jogador = new Jogador();
	
	public static void main(String[] args) {
		String[] nomes = null;
		try {
			nomes = Files.readAllLines(Paths.get(monstros_texto))
			        .toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String nome : nomes) {
			inimigos.add(new Inimigo(nome));
		}
		
		Inimigo inimigo = inimigos.get(random.nextInt(inimigos.size()));
		
		System.out.println("Você encontrou: " + inimigo.get_nome() + "!");
		
		int poder = 10;
		int custo = 4;
		
		boolean batalhando = true;
		boolean turno = true;
		
		while(batalhando) {
			System.out.println("<=========================>");
			System.out.println(inimigo);
			System.out.println("<=========================>");
			System.out.println(jogador);
			System.out.println("<=========================>");
			System.out.println("Suas Opções:");
			System.out.println("1. Ataque");
			System.out.println("2. Defesa");
			System.out.println("3. Magica");
			System.out.println("4. Item");
			System.out.println("<=========================>");
			
			int dano_total = 0;
			int num = entrada.nextInt();
			switch(num) {
			case 1:
				dano_total = jogador.calcule_ataque();
				turno = !turno;
				break;
			case 2:
				jogador.defesa = true;
				turno = !turno;
				break;
			case 3:
				if(jogador.mana - custo >= 0) {
					dano_total = poder;
					jogador.mana -= custo;
					turno = !turno;
					
					System.out.println("Você lança um feitiço!");
				}else {
					System.out.println("Você não tem mana suficiente!");
				}
				break;
			default:
				System.out.println("Opção Inválida.");
				break;
			}
			
			if(!turno) {
				if(inimigo.receba_dano(dano_total)) {
					System.out.println("Inimigo " + inimigo.get_nome() + " foi derrotado!!");
					batalhando = false;
				}else {
					System.out.println("Inimigo " + inimigo.get_nome() + " lhe ataca!");
					
					if(jogador.calcule_dano(inimigo.getPoder())) {
						System.out.println("Você foi derrotado...");
						batalhando = false;
					}else {
						turno = !turno;
					}
				}
			}
		}
		
		entrada.close();
	}

}
