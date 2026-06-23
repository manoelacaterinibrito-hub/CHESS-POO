package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Xadrez.Cor;
import Xadrez.ExcecaoXad;
import Xadrez.PartidaXad;
import Xadrez.PecaXad;
import Xadrez.Pecas.*;
import Xadrez.PosicaoXad;

public class Visual {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printTabuleiro(PecaXad[][] pecas) {
        for (int i = 0; i<pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j< pecas.length; j++) {
                printPeça(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }


    public static void printTabuleiro(PecaXad[][] pecas, boolean[][] movimentosPossiveis) {
        for (int i = 0; i<pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j< pecas.length; j++) {
                printPeça(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPeça(PecaXad peca, boolean colorir) {
        if(colorir) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }

        if (peca == null) {
            System.out.print('-' + ANSI_RESET);
        }else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(' ');
    }

    public static void printPartida(PartidaXad partida, List<PecaXad> capturados) {
        printTabuleiro(partida.verPeças());
        System.out.println();
        printPeçasCapturadas(capturados);
        System.out.println();
        System.out.println("Turno: " + partida.getTurno());
        if (!partida.getCheckmate()) {
            System.out.println("Esperando jogador: " + partida.getJogadorAtual());
            if (partida.getCheck()) {
                System.out.println("CHECK!!");
            }
        }
        else {
            System.out.println("CHECKMATE!");
            System.out.println("Vencedor: " + partida.getJogadorAtual());
        }

    }


    public static PosicaoXad lerPosiçaoXadrez(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new PosicaoXad(coluna, linha);
        }catch(RuntimeException e){
            throw new InputMismatchException("Erro lendo a posição.");
        }
    }

    private static void printPeçasCapturadas(List<PecaXad> capturados) {
        List<PecaXad> brancos = capturados.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
        List<PecaXad> pretos = capturados.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
        System.out.println("Peças Capturadas: ");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(brancos.toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(pretos.toArray()));
        System.out.print(ANSI_RESET);
    }

}