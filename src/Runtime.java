import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class Runtime {
	final static String monstros_texto = "monstros.txt";
	
	static Random random = new Random();
	static Scanner entrada = new Scanner(System.in);
	
	static ArrayList<Inimigo> inimigos = new ArrayList<>();
	static Jogador jogador = new Jogador();
	
	static int estado = 0;
	
	static int quest_ativa = 0;
	
	public static void batalha() {
		Inimigo inimigo = inimigos.get(random.nextInt(inimigos.size()));
		
		System.out.println("Você encontrou: " + inimigo.get_nome() + "!");
		
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
			System.out.println("5. Escapar");
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
				System.out.println("Escolha um feitiço:");
				int i = 1;
				for(Magia feitiço : jogador.feitiços) {
					System.out.println(i + ". " + feitiço.getNome());
				}
				System.out.println("0 ou outro. Sair");
				int sel = entrada.nextInt();
				if(sel > 0 && sel <= jogador.feitiços.size()) {
					Magia escolhido = jogador.feitiços.get(sel - 1);
					int custo = escolhido.getCusto();
					if(jogador.mana - custo >= 0) {
						dano_total = escolhido.getPoder();
						jogador.mana -= custo;
						turno = !turno;
						
						System.out.println("Você lança o feitiço " + escolhido.getNome() + "!");
					}else {
						System.out.println("Você não tem mana suficiente!");
					}
				}

				break;
			case 5:
				System.out.println("Você tenta escapar...");
				int sim = random.nextInt(3);
				if(sim >= 1) {
					System.out.println("Você consegue escapar!");
					batalhando = false;
					estado = 0;
				}else {
					System.out.println("Você não conseguiu escapar!");
					turno = !turno;
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
					estado = 0;
				}else {
					System.out.println("Inimigo " + inimigo.get_nome() + " lhe ataca!");
					
					if(jogador.calcule_dano(inimigo.getPoder())) {
						System.out.println("Você foi derrotado...");
						batalhando = false;
						estado = 4;
					}else {
						turno = !turno;
					}
				}
			}
		}
		
	}
	
	public static void taverna() {
		System.out.println("Na taverna, você se depara com uma tabela de Quests...");
		
	}
	
	public static void main(String[] args) {
		String[] nomes = null;
		try {
			nomes = Files.readAllLines(Paths.get(monstros_texto))
			        .toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < nomes.length; i++) {
			inimigos.add(new Inimigo(nomes[i]));
		}
		
		System.out.println("Bem-vindo ao mundo!");
		
		jogador.inventario.add(new Armadura(1, 1));
		jogador.inventario.add(new Arma(1, 1));
		jogador.equipar_armadura((Armadura)jogador.inventario.get(0));
		jogador.equipar_arma((Arma)jogador.inventario.get(1));
		
		boolean jogando = true;
		while(jogando) {
			switch(estado) {
			case 0:
				System.out.println("Você está na cidade de Bree. O que deseja fazer?");
				System.out.println("1. Visitar a taverna");
				System.out.println("2. Descansar numa Inn");
				System.out.println("3. Ir ao ferreiro");
				System.out.println("4. Voltar para a estrada");
				
				int sel = entrada.nextInt();
				switch(sel) {
				case 2:
					System.out.println("Bons sonhos!");
					jogador.restauração();
					break;
				case 4:
					System.out.println("Você começar a andar um pouco...");
					int possibilidade = random.nextInt(3);
					if(possibilidade == 0) {
						System.out.println("Você se depara com um monstro!");
						estado = 1;
					}
					break;
				default:
					System.out.println("Opção desconhecida!");
					break;
				}
				break;
			case 1:
				batalha();
				break;
			case 4:
				System.out.println("Fim de Jogo");
				jogando = false;
				break;
			}
		}
		
		
		
		entrada.close();
	}

}
