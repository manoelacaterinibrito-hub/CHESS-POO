package Xadrez;
import tabuleiro.Posicao;

public class PosicaoXad {
    private char coluna;
    private int linha;


    public PosicaoXad(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new ExcecaoXad("Posição Inexistente. Valores possíveis: de a1 até h8.");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }


    public int getLinha() {
        return linha;
    }

    protected Posicao converterPosicao(){
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static PosicaoXad conversaoInversa(Posicao posicao) {
        return new PosicaoXad((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
