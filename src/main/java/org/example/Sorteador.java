package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Sorteador {
    private ArrayList<Integer> nSorteados;
    private int[] nRestantes;


    public void sorteiaNumero(int[] nRestantes){
        Random rand = new Random();

    }

    public int[] geraNumeros(){
        int[] array = new int[50];
        for (int i = 1; i <= 50 ; i++) {
            array[i-1] = i;
        }
        return array;
    }
}
