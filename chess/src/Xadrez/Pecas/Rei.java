package Xadrez.Pecas;

import tabuleiro.Tabuleiro;
import tabuleiro.Posicao;
import Xadrez.Cor;
import Xadrez.PartidaXad;
import Xadrez.PecaXad;

public class Rei extends PecaXad{

	private PartidaXad partida;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXad partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXad p = (PecaXad)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();	 
		}
	
	private boolean testeRoque(Posicao posicao) {
		PecaXad p = (PecaXad)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
		}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		if (getContadorMovimentos() == 0 && !partida.getCheck()) {
			
			Posicao posicao_torre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeRoque(posicao_torre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
				}
			
			Posicao posicao_torre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testeRoque(posicao_torre2)) {
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p4 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p5 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p3) == null && getTabuleiro().peca(p4) == null && getTabuleiro().peca(p5) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}	
		}
		return mat;
	}
}