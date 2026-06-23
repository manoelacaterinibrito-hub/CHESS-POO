package tabuleiro;

public class Posicao {
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    void escolherValores(int linha_nova, int coluna_nova) {
        this.linha = linha_nova;
        this.coluna = coluna_nova;
    }

    @Override
    public String toString() {
        return linha + ". " + coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void setValores(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
