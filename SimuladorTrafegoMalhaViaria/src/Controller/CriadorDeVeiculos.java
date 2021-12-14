/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.Buffer;
import Model.Matriz;
import Model.Veiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Joao
 */
public class CriadorDeVeiculos implements Buffer {

    Matriz matriz;
    private Veiculo[] veiculos;
    private int quantidade;
    private int inicio;
    private int fim;
    private Random rand;

    private Semaphore mutex;
    private Semaphore cheio;
    private Semaphore livre;

    public CriadorDeVeiculos(int capacidade) {
        this.veiculos = new Veiculo[capacidade];
        cheio = new Semaphore(0);
        livre = new Semaphore(capacidade);
        mutex = new Semaphore(1);
        quantidade = 0;
        inicio = 0;
        fim = 0;
        rand = new Random();
        matriz = Matriz.getInstance();
    }

    @Override
    public void addVeiculo(Veiculo veiculo) throws Exception {
        try {
            livre.acquire();
            mutex.acquire();
            if (quantidade == veiculos.length) {
                throw new Exception("Buffer cheio");
            }

            veiculos[fim] = veiculo;
            fim = (fim + 1) % veiculos.length;
            quantidade++;
        } catch (InterruptedException e) {
            System.out.println("interrompido");
            e.printStackTrace();
        } finally {
            mutex.release();
            cheio.release();
        }
    }

    @Override
    public Veiculo removerVeiculo() throws Exception {
        Veiculo veiculo = null;
        try {
            cheio.acquire();
            mutex.acquire();
            if (quantidade == 0) {
                throw new Exception("Buffer vazio");
            }
            veiculo = veiculos[inicio];
            veiculos[inicio] = null;
            inicio = (inicio + 1) % veiculos.length;
            quantidade--;
        } catch (InterruptedException e) {
            System.out.println("interrompido");
            e.printStackTrace();
            return null;
        } finally {
            mutex.release();
            livre.release();
        }
        return veiculo;
    }

    public boolean gerar(Veiculo veiculo) throws Exception {
        int orientacao = rand.nextInt(4);
        boolean adicionou = false;
        switch (orientacao) {
            case 0:
                adicionou = nascerSul(veiculo);
                break;
            case 1:
                 adicionou = nascerOeste(veiculo);
                break;
            case 2:
                adicionou = nascerNorte(veiculo);
                break;
            case 3:
                adicionou = nascerLeste(veiculo);
                break;
        }
        Gerenciador ger = Gerenciador.getInstance();
        ger.notificarEstradaAlterada();
        return adicionou;
    }

    private boolean nascerSul(Veiculo veiculo) throws Exception {
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < matriz.getColuna(); i++) {
            if (matriz.getValorMatriz(matriz.getLinha() - 1, i).getItem() == 1) {
                posicoes.add(i);
            }
        }
        int colunaNascer = rand.nextInt(posicoes.size());
        boolean add = matriz.getValorMatriz(matriz.getLinha() - 1, posicoes.get(colunaNascer)).addVeiculoEstrada(veiculo);
        if(add){
            veiculo.setVelocidade(geradorVelocidade());
            addVeiculo(veiculo); 
        }
        return add;
    }

    private boolean nascerOeste(Veiculo veiculo) throws Exception {
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < matriz.getLinha(); i++) {
            if (matriz.getValorMatriz(i, 0).getItem() == 2) {
                posicoes.add(i);
            }
        }
        int linhaNascer = rand.nextInt(posicoes.size());
        boolean add = matriz.getValorMatriz(posicoes.get(linhaNascer), 0).addVeiculoEstrada(veiculo);
        if(add){
            veiculo.setVelocidade(geradorVelocidade());
            addVeiculo(veiculo); 
        }
        return add;
    }

    private boolean nascerNorte(Veiculo veiculo) throws Exception {
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < matriz.getColuna(); i++) {
            if (matriz.getValorMatriz(0, i).getItem() == 3) {
                posicoes.add(i);
            }
        }
        int colunaNascer = rand.nextInt(posicoes.size());

        boolean add = matriz.getValorMatriz(0, posicoes.get(colunaNascer)).addVeiculoEstrada(veiculo);
        if(add){
            veiculo.setVelocidade(geradorVelocidade());
            addVeiculo(veiculo); 
        }
        return add;
    }

    private boolean nascerLeste(Veiculo veiculo) throws Exception {
        List<Integer> posicoes = new ArrayList<>();
        for (int i = 0; i < matriz.getLinha(); i++) {
            if (matriz.getValorMatriz(i, matriz.getColuna() - 1).getItem() == 4) {
                posicoes.add(i);
            }
        }
        int linhaNascer = rand.nextInt(posicoes.size());
        boolean add = matriz.getValorMatriz(posicoes.get(linhaNascer), matriz.getColuna() - 1).addVeiculoEstrada(veiculo);
        if(add){
            veiculo.setVelocidade(geradorVelocidade());
            addVeiculo(veiculo); 
        }
        return add;
    }

    private double geradorVelocidade() {
        return 50 + (rand.nextDouble() * (250 - 50));
    }
}