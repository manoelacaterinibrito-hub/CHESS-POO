package tabuleiro;

public class Peca {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        // Aqui você considera como se a peça ainda não tivesse sido colocada no tabuleiro.
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Tabuleiro getTabuleiro() {
        // Somente classes dentro do mesmo pacote e subclasses poderão executar esse método
        return tabuleiro;
    }

    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(Posicao posicao) {
        return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
    }

    public boolean qualquerMovimentoPossivel() {
        boolean[][] mat = movimentosPossiveis();
        for (int i = 0; i< mat.length; i++) {
            for (int j = 0; j<mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
