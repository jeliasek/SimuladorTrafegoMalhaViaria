/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.ImageIcon;

/**
 *
 * @author Joao
 */
public class EstradaMonitor extends Estrada {

    private String imagemBase;
    private int linha;
    private int coluna;
    private int item;
    private boolean ehCruzamento;
    private Veiculo veiculo;
    private ImageIcon imagem;
    private boolean reservou;

    public EstradaMonitor(int linha, int coluna, int item, Veiculo veiculo, boolean ehCruzamento, String imagem) {
        this.imagem = new ImageIcon(imagem);
        this.linha = linha;
        this.coluna = coluna;
        this.item = item;
        this.ehCruzamento = ehCruzamento;
        this.veiculo = veiculo;
        this.reservou = false;
        this.imagemBase = imagem;

    }

    @Override
    public synchronized boolean addVeiculoEstrada(Veiculo veiculo) {
        boolean adicionou = false;
        try {
            if (veiculo != null) {
                if (!estaOcupado()) {
                    this.imagem = new ImageIcon("assets/veiculo.png");
                    veiculo.setColuna(this.coluna);
                    veiculo.setLinha(this.linha);
                    veiculo.setItemPosicao(this.item);
                    this.veiculo = veiculo;
                    adicionou = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adicionou;
    }

    @Override
    public synchronized boolean addVeiculoCruzamento(Veiculo veiculo) {
        boolean adicionou = false;
        try {
            if (veiculo != null) {
                this.imagem = new ImageIcon("assets/veiculo.png");
                veiculo.setColuna(this.coluna);
                veiculo.setLinha(this.linha);
                veiculo.setItemPosicao(this.item);
                this.veiculo = veiculo;
                adicionou = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adicionou;
    }

    public boolean gerarVeiculoEstrada(Veiculo veiculo) {
        boolean adicionou = false;
        try {
            if (!estaOcupado()) {
                this.imagem = new ImageIcon("assets/veiculoCruzamento.png");
                veiculo.setColuna(this.coluna);
                veiculo.setLinha(this.linha);
                veiculo.setItemPosicao(this.item);
                this.veiculo = veiculo;
                adicionou = true;
            } else {
                Thread.sleep(200);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adicionou;
    }

    @Override
    public synchronized Veiculo retirarVeiculoEstrada() {
        Veiculo aux = null;
        try {
            aux = veiculo;
            this.veiculo = null;
            this.imagem = new ImageIcon(imagemBase);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return aux;
    }

    @Override
    public int getLinha() {
        return linha;
    }

    @Override
    public int getColuna() {
        return coluna;
    }

    @Override
    public int getItem() {
        return item;
    }

    @Override
    public ImageIcon getImagem() {
        return imagem;
    }

    @Override
    public void setImagem(ImageIcon imagem) {
        this.imagem = imagem;
    }

    @Override
    public Veiculo getVeiculo() {
        return veiculo;
    }

    @Override
    public boolean estaOcupado() {
        return veiculo != null;
    }

    @Override
    public boolean isEhCruzamento() {
        return ehCruzamento;
    }

    @Override
    public void setEhCruzamento(boolean ehCruzamento) {
        this.ehCruzamento = ehCruzamento;
    }

    @Override
    public void setImagemBase(String imagemBase) {
        this.imagemBase = imagemBase;
    }

    @Override
    public String toString() {
        return "L=" + linha + "C=" + coluna + "Ca=" + veiculo + "I=" + item;
    }

    @Override
    public synchronized boolean reservar() {
        if (!estaOcupado()) {
            this.veiculo = new Veiculo(linha, coluna, item, linha, coluna, linha);
            return true;
        }
        return false;
    }

    @Override
    public synchronized void liberar() {
        veiculo = null;
    }
}
