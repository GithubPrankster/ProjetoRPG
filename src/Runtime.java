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
	final static int ESTADO_TAVERNA = 5;
	final static int ESTADO_DERROTA = 10;
	
	static Random random = new Random();
	static Scanner entrada = new Scanner(System.in);
	
	static ArrayList<Jogador> jogadores = new ArrayList<>();
	static ArrayList<Item> inventario = new ArrayList<>();
	
	static ArrayList<Cidade> cidades = new ArrayList<>();
	static Cidade cidade_atual = null;
	static Campo campo_atual = null;
	
	static int estado = ESTADO_CIDADE;
	
	static Quest quest_ativa = null;
	static int quest_progresso = 0;
	
	static int ouro = 20;
	
	public static void quest_bree() {
		switch(quest_progresso) {
		case 0:
			Campo c = new Campo("Esconderijo do Demônio");
			c.adicionar_inimigo(new Inimigo("Demônio Poderoso", 50, 10));
			cidade_atual.campos.add(c);
			System.out.println("Você recebe notícia de que o Demônio se esconde em Bree...");
			
			quest_progresso = 1;
			break;
		case 1:
			if(campo_atual.get_nome() == "Esconderijo do Demônio" && estado == ESTADO_ESCAPE) {
				campo_atual.limpar();
				System.out.println("O demônio grita de forma agravante ao ser mandado de volta ao seu domínio.");
				System.out.println("Apesar da joia ter tornado-o forte, não era o bastante contra vocês unidos.");
				System.out.println("O caçador agradece a todos pelo bom trabalho e começa a jornada de volta à capital com a joia da princesa.");
				
				quest_ativa = null;
				quest_progresso = 0;
			}
			break;
		}
	}
	
	public static void quest_goblin() {
		switch(quest_progresso) {
		case 0:
			for(Campo c: cidade_atual.campos) {
				if(c.get_nome() == "City of Goblins") {
					c.adicionar_inimigo(new Inimigo("Goblin", 20, 6));
					c.adicionar_inimigo(new Inimigo("Goblin com Armadura", 40, 8));
					c.adicionar_inimigo(new Inimigo("Goblin com Escudo", 32, 7));
				}
			}
			System.out.println("Parta para City of Goblins para derrotar os invasores!");
			quest_progresso = 1;
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			if(campo_atual.get_nome() == "City of Goblins" && estado == ESTADO_ESCAPE) {
				if(quest_progresso < 4) {
					int diff = (4 - quest_progresso);
					if(diff > 1)
						System.out.println("Ainda restam " + diff + " goblins!");
					else
						System.out.println("Ainda resta " + diff + " goblin!");
					quest_progresso++;
				}else {
					campo_atual.limpar();
					System.out.println("Graças ao esforço dos guerreiros, os goblins cessam sua invasão.");
					System.out.println("Há esperança que possam parar de querer expandir ao custo de vidas inocentes..");
					
					quest_ativa = null;
					quest_progresso = 0;
				}
			}
			break;
			
		}
	}
	
	public static void quest_mago() {
		switch(quest_progresso) {
		case 0:
			System.out.println("Seria bom talvez tentar atrair a atenção de quem tiver feito os magos desaparecerem.");
			System.out.println("Que tal dormir na Inn da cidade?");
			quest_progresso = 1;
			break;
		case 1:
			System.out.println("Enquanto dormiam, um de vocês escuta um barulho... e se depara com um esqueleto!");
			System.out.println("O resto dos guerreiros acorda para derrotar a ameaça.");
			batalha(new Inimigo("Esqueleto", 40, 5));
			if(estado != ESTADO_DERROTA) {
				System.out.println("O esqueleto, antes de voltar a ser uma pilha de ossos, exclama que o Lich irá ter a vingança dele.");
				System.out.println("O Lich precisa da força de outros seres para exercer poder, portanto faz sentido o plano dele.");
				System.out.println("Agora é hora de por um fim nele nas terras perigosas de Poison.");
				
				for(Campo c: cidade_atual.campos) {
					if(c.get_nome() == "Poison") {
						c.adicionar_inimigo(new Inimigo("Lich", 80, 10));
					}
				}
				
				for(Jogador jogador : jogadores)
					jogador.restauração();
				
				quest_progresso = 2;
			}
			break;
		case 2:
			System.out.println("Lich:'Vocês n-não entendem! Pensem sobre o quanto de poder eu poderia compartilhar com v-vocês!'");
			System.out.println("Lich:'Poderiam se tornar invencíveis s-se juntassem-se a mim-'");
			System.out.println("Com uma cara de total desprezo, um dos guerreiros dá o golpe final no parasita sobrenatural.");
			System.out.println("Sem sombra de dúvida, a ameaça desse monstro não existe mais...");
			System.out.println("Os magos lentamente começam a acordar, e embora enfraquecidos, não tem nada a não ser respeito pelo que fizeram.");
			
			quest_ativa = null;
			quest_progresso = 0;
			break;
		}
	}
	
	public static void quest_sereia() {
		switch(quest_progresso) {
		case 0:
			for(Campo c: cidade_atual.campos) {
				if(c.get_nome() == "Crow's Voice") {
					c.adicionar_inimigo(new Inimigo("Sereia", 80, 10));
				}
			}
			System.out.println("Agora é hora de ir para Crow's Voice para confrontar quem estiver lá..");
			quest_progresso = 1;
			break;
		case 1:
			if(campo_atual.get_nome() == "Crow's Voice" && estado == ESTADO_ESCAPE) {
				campo_atual.limpar();
				System.out.println("Com a Sereia derrotada, os guerreiros que ela estava prestes a matar despertam.");
				System.out.println("Graças a você, a caverna está pacífica mais uma vez.");
				
				quest_ativa = null;
				quest_progresso = 0;
			}
			break;
			
		}
	}
	
	public static void batalha(Inimigo... ini) {
		Inimigo inimigo = (ini.length > 0) ? ini[0].clone() : campo_atual.encontro(random).clone();
		
		System.out.println("Você encontrou: " + inimigo.get_nome() + "!");
		
		boolean batalhando = true;
		boolean turno = true;
		
		while(batalhando) {
			boolean ainda_vivo = false;
			
			for(Jogador jogador : jogadores) {
				while(turno) {
					if(jogador.vida <= 0) {
						continue;
					}
					
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
					case 4:
						System.out.println("Escolha um item:");
						
						ArrayList<Restauravel> usaveis = new ArrayList<>();
						
						int e = 1;
						for(Item res : inventario) {
							if(res instanceof Restauravel) {
								usaveis.add((Restauravel) res);
								System.out.println(e + ". " + res.getNome());
								e++;
							}
						}
						System.out.println("0 ou outro. Sair");
						int selec = entrada.nextInt();
						if(selec > 0 && selec <= usaveis.size()) {
							Restauravel escolhido = usaveis.get(selec - 1);
							if(escolhido.todos()) {
								for(Jogador j : jogadores)
									j.restaura_parcial(escolhido.restaura_vida(), escolhido.restaura_mp());
							}else {
								jogador.restaura_parcial(escolhido.restaura_vida(), escolhido.restaura_mp());
							}
							inventario.remove(escolhido);
							turno = !turno;
						}
		
						break;
					case 5:
						if(ini.length > 0) {
							System.out.println("Não há como escapar dessa cilada, bino!");
						}else {
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
						}
						break;
					default:
						System.out.println("Opção Inválida.");
						break;
					}
					
					if(!turno) {
						if(inimigo.receba_dano(dano_total)) {
							System.out.println("Inimigo " + inimigo.get_nome() + " foi derrotado!!");
							
							for(Jogador j : jogadores) {
								if(j.vida > 0)
									j.ganharXP(inimigo.getPoder() * 5 + (inimigo.getVida() / 2));
							}
							
							int res_ouro = inimigo.getPoder() * 4; 
							ouro += res_ouro;
							System.out.println("Você ganha " + res_ouro + " peças de ouro!");
							
							batalhando = false;
							if(ini.length == 0)
								estado = ESTADO_ESCAPE;
							ainda_vivo = true;
							break;
						}else {
							System.out.println("Inimigo " + inimigo.get_nome() + " ataca " + jogador.getNome() + "!");
							
							if(jogador.calcule_dano(inimigo.getPoder())) {
								System.out.println(jogador.getNome() + "foi derrotado...");
							}else {
								
								ainda_vivo = true;
							}
						}
					}
				}
				
				if(!batalhando)
					break;
				
				turno = true;
			}
			
			if(!ainda_vivo) {
				System.out.println("Todos os guerreiros foram derrotados...");
				batalhando = false;
				estado = ESTADO_DERROTA;
			}
		}
		
		
		
	}
	
	public static void main(String[] args) {
		// Inicializando os guerreiros
		
		inventario.add(new Armadura(random.nextInt(2), random.nextInt(2)));
		inventario.add(new Arma(random.nextInt(2), random.nextInt(2)));
		
		inventario.add(new Armadura(random.nextInt(2), random.nextInt(2)));
		inventario.add(new Arma(random.nextInt(2), random.nextInt(2)));
		
		inventario.add(new Armadura(random.nextInt(2), random.nextInt(2)));
		inventario.add(new Arma(random.nextInt(2), random.nextInt(2)));
		
		inventario.add(new Armadura(random.nextInt(2), random.nextInt(2)));
		inventario.add(new Arma(random.nextInt(2), random.nextInt(2)));
		
		Jogador garcias = new Jogador("Garcias", 30, 8, 5);
		garcias.equipar_armadura((Armadura)inventario.get(0));
		garcias.equipar_arma((Arma)inventario.get(1));
		
		jogadores.add(garcias);
		
		Jogador rafaela = new Jogador("Rafaela", 20, 10, 10);
		garcias.equipar_armadura((Armadura)inventario.get(2));
		garcias.equipar_arma((Arma)inventario.get(3));
		
		jogadores.add(rafaela);
		
		Jogador markus = new Jogador("Markus", 50, 5, 5);
		garcias.equipar_armadura((Armadura)inventario.get(4));
		garcias.equipar_arma((Arma)inventario.get(5));
		
		jogadores.add(markus);
		
		Jogador leslie = new Jogador("Leslie", 22, 3, 20);
		garcias.equipar_armadura((Armadura)inventario.get(6));
		garcias.equipar_arma((Arma)inventario.get(7));
		
		jogadores.add(leslie);
		
		for(int i = 0; i < 5; ++i) {
			inventario.add(new Restauravel("Poção de Vida", 15, 0, false));
		}
		
		for(int i = 0; i < 2; ++i) {
			inventario.add(new Restauravel("Poção de Mana", 0, 10, false));
		}
		
		
		// Declarando as cidades
		
		Cidade bree = new Cidade("Bree");
		
		Campo demons_hill = new Campo("Demon's Hill");
		Campo bloody_queen = new Campo("Bloody Queen");
		
		bree.campos.add(demons_hill);
		bree.campos.add(bloody_queen);
		
		bree.quests.add(new Quest("Contrato de Caça", 
				"Há um caçador de recompensas bebendo na taverna que está cansado de perseguir um demônio.\n"
				+ "O demônio havia roubado uma joia poderosa da princesa Liz, e o caçador oferece 50% da recompensa\n"
				+ "pela cabeça dele e a joia de volta.", null, () -> quest_bree()));
		
		cidades.add(bree);
		
		Cidade carlin = new Cidade("Carlin");
		
		Campo city_goblins = new Campo("City of Goblins");
		Campo dark_dungeon = new Campo("Dark Dungeon");
		
		carlin.campos.add(city_goblins);
		carlin.campos.add(dark_dungeon);
		
		carlin.quests.add(new Quest("Proteja Carlin", 
				"City of Goblins tem interesse em expandir seu campos, dispostos a dizimar a "
				+ "população de Carlin.\nProcure guerreiros na cidade, monte um grupo de 4 deles e ajude a defender"
				+ "os acessos à cidade.", null, () -> quest_goblin()));
		
		cidades.add(carlin);
		
		Cidade trevor = new Cidade("Trevor");
		
		Campo thunder = new Campo("Thunder");
		Campo poison = new Campo("Poison");
		Campo crows_voice = new Campo("Crow's Voice");
		
		trevor.campos.add(thunder);
		trevor.campos.add(poison);
		trevor.campos.add(crows_voice);
		
		trevor.quests.add(new Quest("Os Magos de Trevor", 
				"Os magos que se hospedavam na cidade desapareceram misteriosamente.\nOs cidadões querem descobrir o que causou o sumiço.", null, () -> quest_mago()));
		
		trevor.quests.add(new Quest("O Conto da Sereia", 
				"Na caverna de Crow's Voice, um fenômeno tem acontecido onde num certo dia e horário da semana, guerreiros ouvem uma melodia atraente.\n"
				+ "Dos que entram em busca da fonte da canção, alguns não voltam, e os que conseguem, voltam confusos e sem lembrança do que havia lá."
				+ "Os cidadões querem que alguém preparado o bastante possa parar o fenômeno antes que mais vidas sejam tomadas.", null, () -> quest_sereia()));
		
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
		
		System.out.println("Bem-vindo ao mundo!");
		
		boolean jogando = true;
		while(jogando) {
			switch(estado) {
			case ESTADO_CIDADE:
				System.out.println("Seu ouro atual: " + ouro);
				System.out.println("Você está na cidade de " + cidade_atual.getNome() + ". O que deseja fazer?");
				System.out.println("1. Visitar a taverna");
				System.out.println("2. Descansar numa Inn");
				System.out.println("3. Ir ao ferreiro");
				System.out.println("4. Voltar para a estrada");
				
				int sel = entrada.nextInt();
				switch(sel) {
				case 1:
					estado = ESTADO_TAVERNA;
					break;
				case 2:
					if(ouro >= 5) {
						System.out.println("Durma bem!");
						for(Jogador jogador : jogadores)
							jogador.restauração();
						if(quest_ativa != null)
							quest_ativa.rodar_quest();
						ouro -= 5;
					}else {
						System.out.println("Você não tem ouro suficiente para dormir.");
					}
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
				int possibilidade = random.nextInt(4);
				switch(possibilidade) {
					case 0:
					case 1:
						if(!campo_atual.vazio()) {
							System.out.println("Você se depara com um monstro!");
							estado = ESTADO_BATALHA;
						}else {
							System.out.println("Parece que não há inimigos restantes..");
							estado = ESTADO_CIDADE;
						}
						break;
					case 2:
						System.out.println("Você encontra algo...");
						int coisa = random.nextInt(3);
						switch(coisa) {
						default:
							int ourozinho = random.nextInt(20);
							System.out.println("Você se depara com " + ourozinho + " peças de ouro! Oba!");
							ouro += ourozinho;
							break;
						}
						System.out.println("Gostaria de voltar?\n0 = Não, 1 = Sim");
						int al_sel = entrada.nextInt();
						if(al_sel >= 1) {
							System.out.println("Você volta para "+ cidade_atual.getNome() + ".");
							estado = ESTADO_CIDADE;
						}
						break;
					default:
						System.out.println("Você não encontra nada.\nGostaria de voltar?\n0 = Não, 1 = Sim");
						int alt_sel = entrada.nextInt();
						if(alt_sel >= 1) {
							System.out.println("Você volta para "+ cidade_atual.getNome() + ".");
							estado = ESTADO_CIDADE;
						}
						break;
				}
				break;
			case ESTADO_ESCAPE:
				if(quest_ativa != null)
					quest_ativa.rodar_quest();
				
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
				ArrayList<Cidade> visitavel = new ArrayList<>();
				
				int num = 1;
				for(Campo i : cidade_atual.campos) {
					System.out.println(num + ". " + i.get_nome());
					num++;
				}
				
				int diff = num;
				
				for(Cidade c : cidades) {
					if(c.getNome() != cidade_atual.getNome()) {
						visitavel.add(c);
						System.out.println(num + ". " + c.getNome());
						num++;
					}
				}
				
				System.out.println(num + ". De Volta");
				
				int l_sel = entrada.nextInt();
				if(l_sel == num) {
					estado = ESTADO_CIDADE;
				}else {
					if(l_sel >= 1 && l_sel <= num) {
						if(l_sel < diff) {
							campo_atual = cidade_atual.campos.get(l_sel - 1);
							estado = ESTADO_ESTRADA;
						}else {
							cidade_atual = visitavel.get(l_sel - diff);
							estado = ESTADO_CIDADE;
						}
					}else {
						System.out.println("Seleção invalída.");
					}
				}
				
				break;
			case ESTADO_TAVERNA:
				System.out.println("Na taverna, você se depara com uma tabela de Quests...");
				System.out.println("Qual você gostaria de ver?");
				int inum = 1;
				for(Quest q : cidade_atual.quests) {
					System.out.println(inum + ". " + q.get_nome());
					inum++;
				}
				
				System.out.println(inum + ". Sair da Taverna");
				
				int a_sel = entrada.nextInt();
				if (a_sel == inum) {
					estado = ESTADO_CIDADE;
				}else {
					if(a_sel >= 1 && a_sel <= cidade_atual.quests.size()) {
						Quest a_quest = cidade_atual.quests.get(a_sel - 1);
						System.out.println("Descrição:\n" + a_quest.get_descricao());
						System.out.println("Irá aceitar? 0 = Não, 1 = Sim");
						int b_sel = entrada.nextInt();
						if(b_sel >= 1) {
							if(quest_ativa == null) {
								quest_ativa = a_quest;
								System.out.println("Você aceitou a quest.");
								cidade_atual.quests.remove(a_sel - 1);
								
								quest_progresso = 0;
								quest_ativa.rodar_quest();
							}else {
								System.out.println("Você já tem uma quest ativa...");
							}
						}
					}
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
