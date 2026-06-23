package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Xadrez.ExcecaoXad;
import Xadrez.PartidaXad;
import Xadrez.PecaXad;
import Xadrez.Pecas.*;
import Xadrez.PosicaoXad;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXad partida = new PartidaXad();
		List<PecaXad> capturados = new ArrayList<>();

		while(!partida.getCheckmate()) {
			try {
				Visual.limparTela();
				Visual.printPartida(partida, capturados);
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXad origem = Visual.lerPosiçaoXadrez(sc);

				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				Visual.limparTela();
				Visual.printTabuleiro(partida.verPeças(), movimentosPossiveis);

				System.out.println();
				System.out.println("Fim do movimento: ");
				PosicaoXad fim = Visual.lerPosiçaoXadrez(sc);

				PecaXad pecaCapturada = partida.fazerMovimentoXadrez(origem, fim);

				if (pecaCapturada != null) {
					capturados.add(pecaCapturada);
				}

				if (partida.getPromovido() != null) {
					System.out.print("Digite a letra da peça que você deseja evoluir [B/C/Q/T]: ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("Q") && !tipo.equals("C") && !tipo.equals("T")) {
						System.out.print("Valor inválido. Digite a letra da peça que você deseja evoluir [B/C/Q/T]: ");
						tipo = sc.nextLine().toUpperCase();
					}
					partida.substituirPeçaPromovida(tipo);
				}
			}
			catch (ExcecaoXad e){
				System.out.println(e.getMessage());
				sc.nextLine();			}
		}
		Visual.limparTela();
		Visual.printPartida(partida, capturados);
	}

}