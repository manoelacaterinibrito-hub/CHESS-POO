package Xadrez;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXad extends Peca{
    private Cor cor;
    private int contadorMovimentos;

    public PecaXad(Tabuleiro tabuleiro, Cor cor) {
        // Nesse caso, no nosso construtor, temos a utilzação do construtor da nossa superclasse (Peça)
        super(tabuleiro);
        this.cor = cor;
    }


    public Cor getCor() {
        return cor;
    }

    public int getContadorMovimentos() {
        return contadorMovimentos;
    }

    public void aumentarMovimentos() {
        contadorMovimentos++;
    }

    public void diminuirMovimentos() {
        contadorMovimentos--;
    }

    protected boolean temPeçaOponente(Posicao posicao) {
        PecaXad p = (PecaXad) getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }

    public PosicaoXad getPosiçaoXadrez() {
        return PosicaoXad.conversaoInversa(posicao);
    }

}