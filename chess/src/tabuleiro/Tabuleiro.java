package tabuleiro;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new Excecao("Erro criando o tabuleiro. É necessário que haja pelo menos 1 linha e 1 coluna.");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if (!posicaoExiste(linha, coluna)) {
            throw new Excecao("Posição inexistente.");
        }
        return pecas[linha][coluna];
        // Nesse método, vamos retornar a peça que se encontra naquela posição do tabuleiro, recebendo a linha e a coluna diretamente.
    }

    public Peca peca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new Excecao("Posição inexistente.");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
        // Nesse método, vamos retornar a peça que se encontra naquela posição do tabuleiro recebendo a posição específica do tabuleiro.
    }

    public void colocarPeça(Peca peca, Posicao posicao) {
        if (peçaExiste(posicao)) {
            throw new Excecao("Já existe uma peça nesse local.");
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removerPeça(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new Excecao("Posição Inexistente");
        }
        if (peca(posicao) == null) {
            return null;
        }
        Peca aux = peca(posicao);
        aux.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return aux;
    }

    public boolean posicaoExiste(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoExiste(Posicao posicao){
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    public boolean peçaExiste(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new Excecao("Posição inexistente.");
        }
        return peca(posicao) != null;
    }
}
