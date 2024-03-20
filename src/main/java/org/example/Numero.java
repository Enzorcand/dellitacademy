package org.example;

import lombok.Data;

@Data
public class Numero implements Comparable<Numero>{
    private int anInt;
    private int nVezesEscolhido;
    private boolean escolhido;

    public Numero(int valor){
        this.anInt = valor;
    }

    @Override
    public int compareTo(Numero a) {
        return Integer.compare(a.getNVezesEscolhido(), nVezesEscolhido);
    }
}
