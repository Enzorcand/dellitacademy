package org.example;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class Aposta {
    final int nSequencial;
    final double valor;
    private ArrayList<Numero> numeros;
    private boolean isVencedora;
    private int acertos;

    public Aposta(int lastNumber){
        this.isVencedora = false;
        this.acertos = 0;
        this.nSequencial = lastNumber + 1;
        this.valor = 5.0;
        this.numeros = new ArrayList<>();
    }

    public void setNumeros(List<Integer> array) {
        for (int i: array) {
            Numero n = new Numero(i);
            numeros.add(n);
        }
    }
}
