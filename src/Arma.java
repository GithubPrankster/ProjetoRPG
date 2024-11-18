// Baseado em código feito por Guillherme.

import java.util.Random;

public class Arma extends Item {
    private int dano;
    private int nivel;
    private int preco; 
    
    public Arma(int nivel, int tipo) {
        this.nivel = nivel;
        this.calcularChanceDrop();
        this.calcularRaridade(); 
        
        switch (nivel) {
            case 1:
                if (tipo == 1) {
                    this.nome = "Espada Curta";
                    this.dano = 6;
                    this.preco = 20;
                } else {
                    this.nome = "Clava";
                    this.dano = 5;
                    this.preco = 15;
                }
                break;
            case 2:
                if (tipo == 1) {
                    this.nome = "Espada de Madeira";
                    this.dano = 8;
                    this.preco = 25;
                } else {
                    this.nome = "Maça";
                    this.dano = 7;
                    this.preco = 30;
                }
                break;
            case 3:
                if (tipo == 1) {
                    this.nome = "Espada de Duas Mãos";
                    this.dano = 12;
                    this.preco = 40;
                } else {
                    this.nome = "Machado";
                    this.dano = 10;
                    this.preco = 35;
                }
                break;
            case 4:
                if (tipo == 1) {
                    this.nome = "Sabre";
                    this.dano = 15;
                    this.preco = 45;
                } else {
                    this.nome = "Arco e Flecha";
                    this.dano = 14;
                    this.preco = 50;
                }
                break;
            case 5:
                if (tipo == 1) {
                    this.nome = "Lança";
                    this.dano = 18;
                    this.preco = 60;
                } else {
                    this.nome = "Adaga";
                    this.dano = 17;
                    this.preco = 55;
                }
                break;
            case 6:
                if (tipo == 1) {
                    this.nome = "Picareta";
                    this.dano = 20;
                    this.preco = 65;
                } else {
                    this.nome = "Manopla";
                    this.dano = 22;
                    this.preco = 70;
                }
                break;
            case 7:
                if (tipo == 1) {
                    this.nome = "Dardo";
                    this.dano = 25;
                    this.preco = 80;
                } else {
                    this.nome = "Porrete";
                    this.dano = 23;
                    this.preco = 75;
                }
                break;
            case 8:
                if (tipo == 1) {
                    this.nome = "Katana";
                    this.dano = 28;
                    this.preco = 90;
                } else {
                    this.nome = "Açoite";
                    this.dano = 26;
                    this.preco = 85;
                }
                break;
            case 9:
                if (tipo == 1) {
                    this.nome = "Bumerangue";
                    this.dano = 30;
                    this.preco = 100;
                } else {
                    this.nome = "Arco Duplo";
                    this.dano = 32;
                    this.preco = 110;
                }
                break;
            case 10:
                if (tipo == 1) {
                    this.nome = "Besta";
                    this.dano = 35;
                    this.preco = 120;
                } else {
                    this.nome = "Espada de Ferro";
                    this.dano = 40;
                    this.preco = 130;
                }
                break;
            default:
                this.nome = "Arma Desconhecida";
                this.dano = 0;
                break;
        }
    }

    private void calcularRaridade() {
        Random random = new Random();
        
        if (this.nivel >= 6) {
            this.rara = random.nextInt(100) < 20;
        } else {
            this.rara = false;
        }
    }

	public int getDano() {
		return dano;
	}

	public int getPreco() {
		return preco;
	}
}
