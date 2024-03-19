package org.example;

import lombok.Data;

@Data
public class Numero {
    private int anInt;
    private boolean escolhido;

    public Numero(int valor){
        this.anInt = valor;
    }
}
