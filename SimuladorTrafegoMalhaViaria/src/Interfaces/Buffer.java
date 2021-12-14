/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Model.Veiculo;

/**
 *
 * @author Joao
 */
public interface Buffer {

    public void addVeiculo(Veiculo veiculo) throws Exception;

    public Veiculo removerVeiculo() throws Exception;

}