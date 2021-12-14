/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Joao
 */
public class Matriz {
    private int linha;
    private int coluna;
    private Estrada[][] matriz;
    private static Matriz instance;
    
    private Matriz() {
    }

    public synchronized static Matriz getInstance() {
        if (instance == null) {
            instance = new Matriz();
        }
        return instance;
    }

    public Estrada[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Estrada[][] matriz) {
        this.matriz = matriz;
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

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public Estrada getValorMatriz(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setValorMatriz(int linha, int coluna, Estrada estrada) {
        this.matriz[linha][coluna] = estrada;
    }

    public void criarMatriz(int linha, int coluna) {
        this.matriz = new Estrada[linha][coluna];
    }
}

