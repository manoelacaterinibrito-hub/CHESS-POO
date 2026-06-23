package Xadrez;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import Xadrez.Pecas.Bispo;
import Xadrez.Pecas.Cavalo;
import Xadrez.Pecas.Peao;
import Xadrez.Pecas.Rainha;
import Xadrez.Pecas.Rei;
import Xadrez.Pecas.Torre;

public class PartidaXad {
    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkmate;
    private PecaXad vulnerabilidadeEnPassant;
    private PecaXad promovido;

    private List<Peca> peçasTabuleiro = new ArrayList<>();
    private List<Peca> peçasCapturadas = new ArrayList<>();

    public PartidaXad() {
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        setupInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckmate() {
        return checkmate;
    }

    public PecaXad getVulnerabilidadeEnPassant() {
        return vulnerabilidadeEnPassant;
    }

    public PecaXad getPromovido() {
        return promovido;
    }

    public PecaXad[][] verPeças(){
        PecaXad[][] matriz = new PecaXad[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i<tabuleiro.getLinhas(); i++) {
            for (int j = 0; j<tabuleiro.getLinhas(); j++) {
                matriz[i][j] = (PecaXad) tabuleiro.peca(i, j);
                // Aqui faremos um DOWNCASTING de todas as peças do tabuleiro para a classe PeçaXadrez
            }
        }
        return matriz;
    }

    public boolean[][] movimentosPossiveis(PosicaoXad origem){
        Posicao posicao = origem.converterPosicao();
        validarPosiçaoInicial(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaXad fazerMovimentoXadrez (PosicaoXad origemP, PosicaoXad fimP) {
        Posicao origem = origemP.converterPosicao();
        Posicao fim = fimP.converterPosicao();
        validarPosiçaoInicial(origem);
        validarPosiçaoFinal(origem, fim);
        Peca pecaCapturada = fazerMovimento(origem, fim);

        if (testeCheck((jogadorAtual))) {
            desfazerMovimento(origem, fim, pecaCapturada);
            throw new ExcecaoXad("Você não pode se colocar em check.");

        }

        PecaXad pecaMovida = (PecaXad)tabuleiro.peca(fim);

        promovido = null;
        if (pecaMovida instanceof Peao) {
            if ((pecaMovida.getCor() == Cor.BRANCO && fim.getLinha() == 0) ||  (pecaMovida.getCor() == Cor.PRETO && fim.getLinha() == 7)) {
                promovido = (PecaXad)tabuleiro.peca(fim);
                // vamos colocar a rainha por padrão
                promovido = substituirPeçaPromovida("Q");
            }
        }

        check = (testeCheck(oponente(jogadorAtual))) ? true : false;

        if (testeCheckmate(oponente(jogadorAtual))) {
            checkmate = true;
        }
        else {
            proximoTurno();
        }

        // Jogada especial En Passant
        if (pecaMovida instanceof Peao && (fim.getLinha() == origem.getLinha() + 2 || fim.getLinha() == origem.getLinha() - 2)) {
            vulnerabilidadeEnPassant = pecaMovida;
        }
        else {
            vulnerabilidadeEnPassant = null;
        }

        return (PecaXad)pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao fim, Peca pecaCapturada) {
        PecaXad p = (PecaXad)tabuleiro.removerPeça(fim);
        p.diminuirMovimentos();
        tabuleiro.colocarPeça(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPeça(pecaCapturada, fim);
            peçasCapturadas.remove(pecaCapturada);
            peçasTabuleiro.add(pecaCapturada);
        }

        if (p instanceof Rei && fim.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao fimTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXad torre = (PecaXad)tabuleiro.removerPeça(fimTorre);
            tabuleiro.colocarPeça(torre, origemTorre);
            torre.diminuirMovimentos();
        }

        if (p instanceof Rei && fim.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao fimTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXad torre = (PecaXad)tabuleiro.removerPeça(fimTorre);
            tabuleiro.colocarPeça(torre, origemTorre);
            torre.diminuirMovimentos();
        }

        // En Passant
        if (p instanceof Peao) {
            if (origem.getColuna() != fim.getColuna() && pecaCapturada == vulnerabilidadeEnPassant) {
                PecaXad peao = (PecaXad)tabuleiro.removerPeça(fim);				Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(3, fim.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(4, fim.getColuna());
                }

                tabuleiro.colocarPeça(peao, posicaoPeao);
            }
        }
    }

    public PecaXad substituirPeçaPromovida(String tipo) {
        if (promovido == null) {
            throw new IllegalStateException("Não existe peça para ser promovida.");
        }
        if (!tipo.equals("B") && !tipo.equals("Q") && !tipo.equals("C") && !tipo.equals("T")) {
            return promovido;
        }
        Posicao pos = promovido.getPosiçaoXadrez().converterPosicao();
        Peca p = tabuleiro.removerPeça(pos);
        peçasTabuleiro.remove(p);

        PecaXad novaPeça = novaPeça(tipo, promovido.getCor());
        tabuleiro.colocarPeça(novaPeça, pos);
        peçasTabuleiro.add(novaPeça);

        return novaPeça;

    }

    private PecaXad novaPeça(String tipo, Cor cor) {
        if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
        if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
        if (tipo.equals("Q")) return new Rainha(tabuleiro, cor);
        return new Torre(tabuleiro, cor);
    }

    private void validarPosiçaoInicial(Posicao posicao) {
        if (!tabuleiro.peçaExiste(posicao)) {
            throw new ExcecaoXad("Não existe peça na posição de origem.");
        }
        if(jogadorAtual != ((PecaXad)tabuleiro.peca(posicao)).getCor()) {
            throw new ExcecaoXad("Essa peça não corresponde a sua cor.");
        }
        if(!tabuleiro.peca(posicao).qualquerMovimentoPossivel()) {
            throw new ExcecaoXad("Não existe movimentos possíveis para a peça escolhida.");
        }
    }

    public void validarPosiçaoFinal(Posicao origem, Posicao fim) {
        if (!tabuleiro.peca(origem).movimentoPossivel(fim)) {
            throw new ExcecaoXad("A peça escolhida não pode se mover para esse local.");
        }
    }

    private Peca fazerMovimento(Posicao origem, Posicao fim) {
        PecaXad p = (PecaXad)tabuleiro.removerPeça(origem);
        p.aumentarMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeça(fim);
        tabuleiro.colocarPeça(p, fim);

        if (pecaCapturada != null) {
            peçasTabuleiro.remove(pecaCapturada);
            peçasCapturadas.add(pecaCapturada);
        }

        if (p instanceof Rei && fim.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao fimTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXad torre = (PecaXad)tabuleiro.removerPeça(origemTorre);
            tabuleiro.colocarPeça(torre, fimTorre);
            torre.aumentarMovimentos();
        }

        if (p instanceof Rei && fim.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao fimTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXad torre = (PecaXad)tabuleiro.removerPeça(origemTorre);
            tabuleiro.colocarPeça(torre, fimTorre);
            torre.aumentarMovimentos();
        }

        // En Passant
        if (p instanceof Peao) {
            if (origem.getColuna() != fim.getColuna() && pecaCapturada == null) {
                Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(fim.getLinha() + 1, fim.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(fim.getLinha() - 1, fim.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeça(posicaoPeao);
                peçasCapturadas.add(pecaCapturada);
                peçasTabuleiro.remove(pecaCapturada);
            }
        }

        return pecaCapturada;
    }


    private void colocarNovaPeça(char coluna, int linha, PecaXad peca) {
        tabuleiro.colocarPeça(peca, new PosicaoXad(coluna, linha).converterPosicao());
        peçasTabuleiro.add(peca);
    }

    private void proximoTurno() {
        turno++;
        if (jogadorAtual == Cor.BRANCO) {
            jogadorAtual = Cor.PRETO;
        }
        else {
            jogadorAtual = Cor.BRANCO;
        }
    }

    private Cor oponente(Cor cor) {
        if (cor == Cor.BRANCO) {
            return Cor.PRETO;
        }
        else {
            return Cor.BRANCO;
        }
    }

    private PecaXad rei(Cor cor) {
        List<Peca> lista = peçasTabuleiro.stream().filter(x -> ((PecaXad)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p: lista) {
            if (p instanceof Rei) {
                return (PecaXad)p;
            }
        }
        throw new IllegalStateException("Não existe rei " + cor + " no tabuleiro.");
    }

    private boolean testeCheck(Cor cor) {
        Posicao posicaoRei = rei(cor).getPosiçaoXadrez().converterPosicao();
        List<Peca> peçasOponentes = peçasTabuleiro.stream().filter(x -> ((PecaXad)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca p: peçasOponentes) {
            boolean[][] mat = p.movimentosPossiveis();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckmate(Cor cor) {
        if (!testeCheck(cor)) {
            return false;
        }
        List<Peca> lista = peçasTabuleiro.stream().filter(x -> ((PecaXad)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p: lista) {
            boolean[][] mat = p.movimentosPossiveis();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((PecaXad)p).getPosiçaoXadrez().converterPosicao();
                        Posicao fim = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(origem, fim);
                        boolean testeCheck = testeCheck(cor);
                        desfazerMovimento(origem, fim, pecaCapturada);
                        if(!testeCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private void setupInicial(){

        colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 1, new Rainha(tabuleiro, Cor.BRANCO));

        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 8, new Rainha(tabuleiro, Cor.PRETO));
    }


}