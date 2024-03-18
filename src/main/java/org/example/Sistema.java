package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Data
public class Sistema {
    private Aposta lastAposta;
    private HashMap<Integer, Apostador> participantes;
    private Sorteador sorteio;

    public int nVezesEscolhido(int numero){
        return 0;
    }

    public void registerAposta(){
        System.out.println("Insira o cpf do apostador:");
        Scanner scan = new Scanner(System.in);
        int cpf = scan.nextInt();
        if(participantes.get(cpf) == null){
            System.out.println("Apostador não cadastrado. Deseja cadastra-lo? (y/n)");
            String entrada = scan.nextLine();
            if (entrada.equalsIgnoreCase("Y")||
                    entrada.equalsIgnoreCase("y")){
                registerApostador(cpf);
            }
        }
        participantes.get(cpf).createAposta(lastAposta.getNSequencial());
    }

    public void registerApostador(int cpf){

    }

    public void registerApostador(){

    }
}
