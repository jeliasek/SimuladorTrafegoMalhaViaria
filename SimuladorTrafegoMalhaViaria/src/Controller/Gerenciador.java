/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Matriz;
import Model.Veiculo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;

/**
 *
 * @author Joao
 */
public class Gerenciador {

    private Matriz matriz = Matriz.getInstance();
    private static Gerenciador instance;
    private Leitor leitor;
    private File arquivo;
    private boolean emAndamento;
    private int VeiculosGerados;
    private int quantidadeDeVeiculos;
    private boolean terminou;
    private double intervaloInsercao;
    private int modo;
    private int VeiculosMortos;
    private int veiculosNoMundo;
    private List<Observer.Observer> observadores;
    private ExecutorService executor;


    private Gerenciador() {
         executor = Executors.newCachedThreadPool();
         terminou = false;
         emAndamento = true;
         VeiculosGerados = 0;
         veiculosNoMundo = 0;
         observadores = new ArrayList<>();        
    }

    public void addObservador(Observer.Observer obs) {
        observadores.add(obs);
    }

    public void lerMatriz(int modo) {
        try {
            leitor = new Leitor();
            leitor.lerMatriz(arquivo,modo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gerenciador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void escolherMatriz(File arquivo) {
        this.arquivo = arquivo;
    }

    public synchronized static Gerenciador getInstance() {
        if (instance == null) {
            instance = new Gerenciador();
        }
        return instance;
    }

    public Icon getImageMatriz(int col, int row) {
        return matriz.getValorMatriz(row, col).getImagem();
    }

    public int getLinhaCount() {
        return matriz.getLinha();
    }

    public int getColunaCount() {
        return matriz.getColuna();
    }
    
    public void encerrarSimulacao(){
        emAndamento = false;
        for (Observer.Observer obs : observadores) {
            obs.notificarSimulacaoEncerrada();
        }
    }
    
    public void encerrarSimulacaoImediatamente(){
        emAndamento = false;
        
        lerMatriz(modo);
        notificarEstradaAlterada();
        for (Observer.Observer obs : observadores) {
            obs.notificarSimulacaoEncerrada();
        }
    }

    public void iniciarSimulacao(int qtdVeiculos, double intervalo, int modo) {
        this.quantidadeDeVeiculos = qtdVeiculos;
        this.intervaloInsercao = intervalo;
        this.modo = modo;
        this.emAndamento = true;
        matriz.setMatriz(null);
        lerMatriz(modo);

        notificarEstradaAlterada();
       
        gerarVeiculos();
    }
    
    public void gerarVeiculos() {
        if ((veiculosNoMundo < quantidadeDeVeiculos) && emAndamento) {
            Veiculo Veiculo = new Veiculo(0, 0, 0, 0, quantidadeDeVeiculos, intervaloInsercao);           
            executor.execute(Veiculo);
            VeiculosGerados++;
            veiculosNoMundo++;
        }
    }
    
    public void reset(){
        VeiculosGerados = 0;
        VeiculosMortos = 0;
        veiculosNoMundo = 0;
        emAndamento = true;
    }
    
    public void verificarFim() {
        veiculosNoMundo --;
//        VeiculosMortos++;
//        if ((VeiculosMortos == VeiculosGerados)) {
//            for (Observer.Observer obs : observadores) {
//                obs.notificarSimulacaoEncerrada();
//            }
//        }
    }

    public void notificarEstradaAlterada() {
        for (Observer.Observer obs : observadores) {
            obs.notificarEstradaAlterada();
        }
    }

    public int getVeiculosGerados() {
        return VeiculosGerados;
    }

    public int getQuantidadeDeVeiculos() {
        return quantidadeDeVeiculos;
    }
}