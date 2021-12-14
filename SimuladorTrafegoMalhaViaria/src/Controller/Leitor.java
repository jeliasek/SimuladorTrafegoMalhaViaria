/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Estrada;
import Model.Matriz;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 *
 * @author Joao
 */
public class Leitor {
    
    //Lê a matriz e cria o mapa que a JTable irá ler
    public void lerMatriz(File arquivo, int modo) throws FileNotFoundException {
        Scanner in = new Scanner(arquivo);
        Estrada estrada = new Estrada();
        int linha = Integer.parseInt(in.next().trim());
        int coluna = Integer.parseInt(in.next().trim());
        System.out.println("Linha: " + linha + " Coluna: " + coluna);
        Matriz matriz = Matriz.getInstance();
        matriz.criarMatriz(linha, coluna);
        matriz.setLinha(linha);
        matriz.setColuna(coluna);
//        if (modo == 1) {
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    int valor = Integer.parseInt(in.next().trim());
                    if (valor == 5 || valor == 6 || valor == 7 || valor == 8 || valor == 9 || valor == 10 || valor == 11 || valor == 12) {
                        matriz.setValorMatriz(i, j, estrada.estradaFactory(modo,i, j, valor, null, true, "assets/cruzamento.png"));
                    } else if (valor != 0) {
                        matriz.setValorMatriz(i, j, estrada.estradaFactory(modo,i, j, valor, null, false, "assets/asfalto.png"));
                    } else if(modo ==1){
                        matriz.setValorMatriz(i, j, estrada.estradaFactory(modo,i, j, valor, null, false, "assets/grama.png"));
                    }else{
                        matriz.setValorMatriz(i, j, estrada.estradaFactory(modo,i, j, valor, null, false, "assets/grama.png"));
                    }
                    try {
                        if (matriz.getValorMatriz(i, j).getItem() == 1 && matriz.getValorMatriz(i, j - 1).getItem() == 2 || matriz.getValorMatriz(i, j).getItem() == 2 && matriz.getValorMatriz(i - 1, j).getItem() == 3) {
                            matriz.getValorMatriz(i, j).setImagem(new ImageIcon("assets/asfalto.png"));
                            matriz.getValorMatriz(i, j).setImagemBase("assets/asfalto.png");

                        } else if (matriz.getValorMatriz(i, j).getItem() == 4 && matriz.getValorMatriz(i, j - 1).getItem() == 3) {
                            matriz.getValorMatriz(i, j - 1).setImagem(new ImageIcon("assets/asfalto.png"));
                            matriz.getValorMatriz(i, j - 1).setImagemBase("assets/asfalto.png");
                        } else if (matriz.getValorMatriz(i, j).getItem() == 1 && matriz.getValorMatriz(i - 1, j).getItem() == 4) {
                            matriz.getValorMatriz(i - 1, j).setImagem(new ImageIcon("assets/asfalto.png"));
                            matriz.getValorMatriz(i - 1, j).setImagemBase("assets/asfalto.png");
                        }
                    } catch (Exception e) {

                    }
                }
            }
    }
}

