import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class Runtime {
	final static String monstros_texto = "monstros.txt";
	final static int ESTADO_CIDADE = 0;
	final static int ESTADO_BATALHA = 1;
	final static int ESTADO_ESTRADA = 2;
	final static int ESTADO_ESCAPE = 3;
	final static int ESTADO_LUGAR = 4;
	final static int ESTADO_DERROTA = 10;
	
	static Random random = new Random();
	static Scanner entrada = new Scanner(System.in);
	
	static Jogador jogador = new Jogador();
	
	static ArrayList<Cidade> cidades = new ArrayList<>();
	static Cidade cidade_atual = null;
	static Campo campo_atual = null;
	
	static int estado = ESTADO_CIDADE;
	
	static int quest_ativa = 0;
	
	public static void batalha() {
		Inimigo inimigo = campo_atual.encontro(random);
		
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
					estado = ESTADO_ESCAPE;
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
					estado = ESTADO_ESCAPE;
				}else {
					System.out.println("Inimigo " + inimigo.get_nome() + " lhe ataca!");
					
					if(jogador.calcule_dano(inimigo.getPoder())) {
						System.out.println("Você foi derrotado...");
						batalhando = false;
						estado = ESTADO_DERROTA;
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
		// Declarando as cidades
		
		Cidade bree = new Cidade("Bree");
		
		Campo demons_hill = new Campo("Demon's Hill");
		Campo bloody_queen = new Campo("Bloody Queen");
		
		bree.campos.add(demons_hill);
		bree.campos.add(bloody_queen);
		
		cidades.add(bree);
		
		Cidade carlin = new Cidade("Carlin");
		
		Campo city_goblins = new Campo("City of Goblins");
		Campo dark_dungeon = new Campo("Dark Dungeon");
		
		carlin.campos.add(city_goblins);
		carlin.campos.add(dark_dungeon);
		
		cidades.add(carlin);
		
		Cidade trevor = new Cidade("Trevor");
		
		Campo thunder = new Campo("Thunder");
		Campo poison = new Campo("Posion");
		Campo graws_voice = new Campo("Graw's Voice");
		
		trevor.campos.add(thunder);
		trevor.campos.add(poison);
		trevor.campos.add(graws_voice);
		
		cidades.add(trevor);
		
		cidade_atual = cidades.get(0);
		campo_atual = cidade_atual.campos.get(0);
		
		// Carregando os inimigos
		
		String[] nomes = null;
		try {
			nomes = Files.readAllLines(Paths.get(monstros_texto))
			        .toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < nomes.length; i++) {
			Inimigo ini = new Inimigo(nomes[i]);
			
			int poder = ini.getPoder();
			
			if(poder <= 5) {
				demons_hill.adicionar_inimigo(ini);
			}else if(poder >= 4 && poder <= 8) {
				bloody_queen.adicionar_inimigo(ini);
			}
		}
		
		// Equipamento inicial para o jogador
		
		jogador.inventario.add(new Armadura(1, 1));
		jogador.inventario.add(new Arma(1, 1));
		jogador.equipar_armadura((Armadura)jogador.inventario.get(0));
		jogador.equipar_arma((Arma)jogador.inventario.get(1));
		
		System.out.println("Bem-vindo ao mundo!");
		
		boolean jogando = true;
		while(jogando) {
			switch(estado) {
			case ESTADO_CIDADE:
				System.out.println("Você está na cidade de " + cidade_atual.getNome() + ". O que deseja fazer?");
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
					estado = ESTADO_LUGAR;
					break;
				default:
					System.out.println("Opção desconhecida!");
					break;
				}
				break;
			case ESTADO_BATALHA:
				batalha();
				break;
			case ESTADO_ESTRADA:
				System.out.println("Você começar a andar um pouco...");
				int possibilidade = random.nextInt(3);
				switch(possibilidade) {
					case 0:
						System.out.println("Você se depara com um monstro!");
						estado = 1;
						break;
					default:
						System.out.println("Você não encontra nada.\nGostaria de voltar?\n0 = Não, 1 = Sim");
						int alt_sel = entrada.nextInt();
						if(alt_sel >= 1) {
							System.out.println("Você volta para "+ cidade_atual.getNome() + ".");
							estado = 0;
						}
						break;
				}
				break;
			case ESTADO_ESCAPE:
				System.out.println("Gostaria de voltar para " + cidade_atual.getNome() + "?\n0 = Não, 1 = Sim");
				int e_sel = entrada.nextInt();
				if(e_sel >= 1) {
					System.out.println("Você volta para "+ cidade_atual.getNome() + ".");
					estado = ESTADO_CIDADE;
				}else {
					estado = ESTADO_ESTRADA;
				}
				break;
			case ESTADO_LUGAR:
				System.out.println("Para onde gostaria de ir?");
				int num = 1;
				for(Campo i : cidade_atual.campos) {
					System.out.println(num + ". " + i.get_nome());
				}
				break;
			case ESTADO_DERROTA:
				System.out.println("Fim de Jogo");
				jogando = false;
				break;
			}
		}
		
		
		
		entrada.close();
	}

}
