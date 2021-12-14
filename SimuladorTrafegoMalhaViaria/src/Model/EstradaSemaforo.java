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
public class EstradaSemaforo extends Estrada  {
    private String imagemBase;
    private int linha;
    private int coluna;
    private int item;
    private boolean ehCruzamento;
    private Veiculo veiculo;
    private ImageIcon imagem;
    private Semaphore mutex;
    private Semaphore ocupado;
    private Semaphore livre;

    public EstradaSemaforo(int linha, int coluna, int item,Veiculo veiculo, boolean ehCruzamento, String imagem) {
        this.linha = linha;
        this.coluna = coluna;
        this.item = item;
        this.ehCruzamento = ehCruzamento;
        this.imagem = new ImageIcon(imagem);
        this.imagemBase = imagem;
        mutex = new Semaphore(1);
        livre = new Semaphore(1);
        ocupado = new Semaphore(0);
    }

    @Override
    public boolean addVeiculoEstrada(Veiculo veiculo) {
        boolean adicionou  = false;
        try {            
            livre.acquire();
            mutex.acquire();
            this.imagem = new ImageIcon("assets/veiculo.png");          
            veiculo.setColuna(this.coluna);
            veiculo.setLinha(this.linha);
            veiculo.setItemPosicao(this.item);
            this.veiculo = veiculo;
            adicionou = true;           
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            ocupado.release();
        }
        return adicionou;
    }
    
    @Override
    public boolean addVeiculoCruzamento(Veiculo veiculo) {
        boolean adicionou  = false;
        try {
            mutex.acquire();
            this.imagem = new ImageIcon("assets/veiculoCruzamento.png");
            veiculo.setColuna(this.coluna);
            veiculo.setLinha(this.linha);
            veiculo.setItemPosicao(this.item);
            this.veiculo = veiculo;
            adicionou = true;           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            ocupado.release();
        }
        return adicionou;
    }
    
    @Override
    public boolean gerarVeiculoEstrada(Veiculo veiculo){
        boolean adicionou  = false;        
            try {
                livre.acquire();
                mutex.acquire();
                if (!estaOcupado()) {
                    this.imagem = new ImageIcon("assets/veiculo.png");
                    veiculo.setColuna(this.coluna);
                    veiculo.setLinha(this.linha);
                    veiculo.setItemPosicao(this.item);
                    this.veiculo = veiculo;
                    adicionou = true;
            }else{
                   Thread.sleep(200);
                }          
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            ocupado.release();
        }
            return adicionou;
        }

    @Override
    public Veiculo retirarVeiculoEstrada() {
        Veiculo aux = null;
        try {
            ocupado.acquire();
            mutex.acquire();
            aux = veiculo;
            this.veiculo = null;
            this.imagem = new ImageIcon(imagemBase);
        } catch (InterruptedException e) {
            System.out.println("Semaforo mutex ou livre interrompidos, abortado");
            e.printStackTrace();
            return null;
        } finally {
            mutex.release();
            livre.release();
        }
        return aux;
    }

    @Override
    public  int getLinha(){
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
    public boolean reservar(){
        try {
            return livre.tryAcquire(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(EstradaSemaforo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; //Caso seja interrempido       
    }
    @Override
    public void liberar(){
        livre.release();
    }
}

