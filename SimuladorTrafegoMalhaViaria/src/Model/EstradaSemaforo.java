/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Joao
 */
public class EstradaSemaforo extends Estrada {

    private String imagemBase;
    private int linha;
    private int coluna;
    private int item;
    private boolean ehCruzamento;
    private Veiculo veiculo;
    private ImageIcon imagem;
    private Semaphore mutex;

    public EstradaSemaforo(int linha, int coluna, int item, Veiculo veiculo, boolean ehCruzamento, String imagem) {
        this.linha = linha;
        this.coluna = coluna;
        this.item = item;
        this.ehCruzamento = ehCruzamento;
        this.imagem = new ImageIcon(imagem);
        this.imagemBase = imagem;
        mutex = new Semaphore(1);

    }

    @Override
    public boolean addVeiculoEstrada(Veiculo veiculo) {
        boolean adicionou = false;
        try {

            mutex.acquire();
            if (veiculo != null) {
                if (!estaOcupado()) {
                    if (veiculo.isVivo()) {
                        this.imagem = new ImageIcon("assets/veiculo.png");
                        veiculo.setColuna(this.coluna);
                        veiculo.setLinha(this.linha);
                        veiculo.setItemPosicao(this.item);
                        this.veiculo = veiculo;
                        adicionou = true;
                    } else {
                        veiculo.interrupt();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();

        }
        return adicionou;
    }

    @Override
    public boolean addVeiculoCruzamento(Veiculo veiculo) {
        boolean adicionou = false;
        try {
            mutex.acquire();
            if (veiculo != null) {
                if (veiculo.isVivo()) {
                    this.imagem = new ImageIcon("assets/veiculoCruzamento.png");
                    veiculo.setColuna(this.coluna);
                    veiculo.setLinha(this.linha);
                    veiculo.setItemPosicao(this.item);
                    this.veiculo = veiculo;
                    adicionou = true;
                } else {
                    veiculo.interrupt();
                    mutex.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();

        }
        return adicionou;
    }

    @Override
    public boolean gerarVeiculoEstrada(Veiculo veiculo) {
        boolean adicionou = false;
        try {

            mutex.acquire();
            if (!estaOcupado()) {
                this.imagem = new ImageIcon("assets/veiculo.png");
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
        } finally {
            mutex.release();

        }
        return adicionou;
    }

    @Override
    public Veiculo retirarVeiculoEstrada() {
        Veiculo aux = null;
        try {

            mutex.acquire();
            aux = veiculo;
            this.veiculo = null;
            this.imagem = new ImageIcon(imagemBase);
        } catch (InterruptedException e) {
            System.out.println("Semaforo mutex interrompidos, abortado");
            e.printStackTrace();
            return null;
        } finally {
            mutex.release();

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
    public boolean reservar() {
        if (!estaOcupado()) {
            this.veiculo = new Veiculo(linha, coluna, item, linha, coluna, linha);
            return true;
        }
        return false;
    }

    @Override
    public void liberar() {
        veiculo = null;
    }
}
