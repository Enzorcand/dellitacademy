package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Sorteador {
    private ArrayList<Integer> nSorteados;
    private ArrayList<Integer> nRestantes;


    public boolean sorteiaNumero(ArrayList<Integer> nRestantes){
        Random rand = new Random();
        if(nRestantes.size() <= 25){
            return false;
        }
        
    }

    public ArrayList<Integer> geraNumeros(){
        ArrayList<Integer> array = new ArrayList<>(50);
        for (int i = 1; i <= 50 ; i++) {
            array.add(i-1, i);
        }
        return array;
    }
}
