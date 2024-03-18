package org.example;
import lombok.Data;

import java.util.InputMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class Aposta {
    final int nSequencial;
    private ArrayList<Numero> numeros;
    private boolean isVencedora;
    private int acertos;

    public Aposta(int lastNumber){
        this.isVencedora = false;
        this.acertos = 0;
        this.nSequencial = lastNumber + 1;
    }

    public void setNumeros(ArrayList<Integer> array) {

    }
    
    public void checkVenceu(List<Integer> sorteio){
        for (int n:
             sorteio) {
            for (int i = 0; i < numeros.size(); i++) {

            }
        }
    }
}
