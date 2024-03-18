package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Sorteador {
    private int indexSorteio = 0;
    private ArrayList<Integer> nRestantes;


    public int sorteiaNumero(ArrayList<Integer> nRestantes){
        Random rand = new Random();
        if(25 >= nRestantes.size()){
            return -1;
        }
        int i = rand.nextInt(1, nRestantes.size());
        nRestantes.remove(i);
        return i;
    }

    public ArrayList<Integer> geraNumeros(){
        ArrayList<Integer> array = new ArrayList<>(50);
        for (int i = 1; i <= 50 ; i++) {
            array.add(i-1, i);
        }
        return array;
    }
}
