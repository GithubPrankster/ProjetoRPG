// Baseado em código feito por Guillherme.

import java.util.Random;

public class Armadura extends Item {
    private int defesa;
    private int nivel;
    private int preco;

    public Armadura(int nivel, int tipo) {
        this.nivel = nivel;
        this.calcularChanceDrop();
        this.calcularRaridade();  //RARIDADE
        
        switch (nivel) {
            case 1:
                if (tipo == 1) {
                    this.nome = "Armadura de algodão";
                    this.defesa = 10;
                    this.preco = 50;
                } else {
                    this.nome = "Capa";
                    this.defesa = 13;
                    this.preco = 55;
                }
                break;
            case 2:
                if (tipo == 1) {
                    this.nome = "Armadura de Tecido";
                    this.defesa = 15;
                    this.preco = 55;
                } else {
                    this.nome = "Armadura de Lã";
                    this.defesa = 18;
                    this.preco = 55;
                }
                break;
            case 3:
                if (tipo == 1) {
                    this.nome = "Armadura de Pele";
                    this.defesa = 20;
                    this.preco = 60;
                } else {
                    this.nome = "Armadura de Couro";
                    this.defesa = 25;
                    this.preco = 60;
                }
                break;
            case 4:
                if (tipo == 1) {
                    this.nome = "Armadura de Placa";
                    this.defesa = 25;
                    this.preco = 65;
                } else {
                    this.nome = "Armadura de Madeira";
                    this.defesa = 28;
                    this.preco = 65;
                }
                break;
            case 5:
                if (tipo == 1) {
                    this.nome = "Armadura de Corrente";
                    this.defesa = 30;
                    this.preco = 70;
                } else {
                    this.nome = "Armadura de Ferro";
                    this.defesa = 33;
                    this.preco = 70;
                }
                break;
            case 6:
                if (tipo == 1) {
                    this.nome = "Armadura de Prata";
                    this.defesa = 35;
                    this.preco = 75;
                } else {
                    this.nome = "Armadura de Cobre";
                    this.defesa = 38;
                    this.preco = 75;
                }
                break;
            case 7:
                if (tipo == 1) {
                    this.nome = "Armadura de Ouro";
                    this.defesa = 40;
                    this.preco = 80;
                } else {
                    this.nome = "Armadura de Platina";
                    this.defesa = 43;
                    this.preco = 85;
                }
                break;
            case 8:
                if (tipo == 1) {
                    this.nome = "Armadura de Cristal";
                    this.defesa = 45;
                    this.preco = 90;
                } else {
                    this.nome = "Armadura do Guerreiro";
                    this.defesa = 48;
                    this.preco = 95;
                }
                break;
            case 9:
                if (tipo == 1) {
                    this.nome = "Armadura de Paladino";
                    this.defesa = 50;
                    this.preco = 100;
                } else {
                    this.nome = "Armadura de Mago";
                    this.defesa = 53;
                    this.preco = -1; 
                }
                break;
            case 10:
                if (tipo == 1) {
                    this.nome = "Armadura de Safira";
                    this.defesa = 55;
                    this.preco = -1; 
                } else {
                    this.nome = "Armadura de Esmeralda";
                    this.defesa = 60;
                    this.preco = 1000;
                }
                break;
            default:
                this.nome = "Armadura Desconhecida";
                this.defesa = 0;
                break;
        }
    }

    private void calcularRaridade() {
        Random random = new Random();
       
        if (this.nivel >= 6) {
            // CHANCE DE 20%
            this.rara = random.nextInt(100) < 20;
        } else {
            // ARMADURA DE NIVEL 1 ATE 5 SÃO COMUNS
            this.rara = false;
        }
    }

	public int getDefesa() {
		return defesa;
	}

	public int getPreco() {
		return preco;
	}
}
