package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Sorteador {

    private ArrayList<Integer> nRestantes;

    public Sorteador(){
        geraNumeros();
    }

    public void sorteiaNumero(ArrayList<Integer> nSorteados) {
        Random rand = new Random();
        int n = rand.nextInt(1, nRestantes.size() + 1);
        nSorteados.add(n);
        nRestantes.remove(n - 1);
    }

    public void geraNumeros(){
        nRestantes = new ArrayList<>(50);
        for (int i = 1; i <= 50 ; i++) {
            nRestantes.add(i-1, i);
        }
    }
}
