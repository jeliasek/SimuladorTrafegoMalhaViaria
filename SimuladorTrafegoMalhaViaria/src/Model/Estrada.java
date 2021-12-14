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
public class Estrada {

    public boolean addVeiculoEstrada(Veiculo veiculo) {
        return false;
    }

    public boolean gerarVeiculoEstrada(Veiculo veiculo) {
        return false;
    }

    public boolean addVeiculoCruzamento(Veiculo veiculo) {
        return false;
    }

    public Veiculo retirarVeiculoEstrada() {
        return null;
    }

    public int getLinha() {
        return 0;
    }

    public int getColuna() {
        return 0;
    }

    public int getItem() {
        return 0;
    }

    public ImageIcon getImagem() {
        return null;
    }

    public void setImagem(ImageIcon imagem) {
    }

    public Veiculo getVeiculo() {
        return null;
    }

    public boolean estaOcupado() {
        return false;
    }

    public boolean isEhCruzamento() {
        return false;
    }

    public void setImagemBase(String imagem) {
    }

    public void setEhCruzamento(boolean ehCruzamento) {
    }

    public boolean reservar() {
        return false;
    }

    public void liberar() {
    }

    public Estrada estradaFactory(int modo, int linha, int coluna, int item, Veiculo veiculo, boolean ehCruzamento, String imagem) {
        if (modo == 1) {
            return new EstradaSemaforo(linha, coluna, item, veiculo, ehCruzamento, imagem);
        } else {
            return new EstradaMonitor(linha, coluna, item, veiculo, ehCruzamento, imagem);
        }
    }
}
