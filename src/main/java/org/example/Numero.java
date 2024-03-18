package org.example;

import lombok.Data;

@Data
public class Numero {
    private int valor;
    private boolean escolhido;

    public Numero(int valor){
        this.valor = valor;
    }
}
