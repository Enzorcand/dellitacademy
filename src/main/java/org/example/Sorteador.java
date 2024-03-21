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
        int n = rand.nextInt(0, nRestantes.size());
        nSorteados.add(nRestantes.get(n));
        nRestantes.remove(n);
    }

    public void geraNumeros(){
        nRestantes = new ArrayList<>(50);
        for (int i = 1; i <= 50 ; i++) {
            nRestantes.add(i-1, i);
        }
    }
}
