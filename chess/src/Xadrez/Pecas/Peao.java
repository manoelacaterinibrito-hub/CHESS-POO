package Xadrez.Pecas;

import tabuleiro.Tabuleiro;
import tabuleiro.Posicao;
import Xadrez.Cor;
import Xadrez.PecaXad;
import Xadrez.PartidaXad;

public class Peao extends PecaXad{
	
	private PartidaXad partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXad partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().peçaExiste(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().peçaExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().peçaExiste(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().peçaExiste(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().peçaExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().peçaExiste(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		if (posicao.getLinha() == 3) {
			Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(esquerda) && temPeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partida.getVulnerabilidadeEnPassant()) {
				mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
			}
			
			Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(direita) && temPeçaOponente(direita) && getTabuleiro().peca(direita) == partida.getVulnerabilidadeEnPassant()) {
				mat[direita.getLinha() - 1][direita.getColuna()] = true;
			}
		}
		
		if (posicao.getLinha() == 4) {
			Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(esquerda) && temPeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partida.getVulnerabilidadeEnPassant()) {
				mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
			}
			
			Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(direita) && temPeçaOponente(direita) && getTabuleiro().peca(direita) == partida.getVulnerabilidadeEnPassant()) {
				mat[direita.getLinha() + 1][direita.getColuna()] = true;
			}
		}
		
		return mat;
	}
	
}
