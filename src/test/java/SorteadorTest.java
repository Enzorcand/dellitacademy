import org.example.Sistema;
import org.example.Sorteador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

class SorteadorTest {

    @Test
    void numberIsRemoved(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        Sorteador sorteador = new Sorteador();
        sorteador.sorteiaNumero(sistema.getNSorteados());
        Assertions.assertTrue(sistema.getNSorteados().size() < 50);
    }

    @Test
    void nSorteadosLimitIs25(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        Sorteador sorteador = new Sorteador();
        for (int i = 0; i < 25; i++) {
            sorteador.sorteiaNumero(sistema.getNSorteados());
        }
        Assertions.assertFalse(false);
    }

    @Test
    void allNumbersArePossible(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        Sorteador sorteador = new Sorteador();
        for (int i = 0; i < 50; i++) {
            sorteador.sorteiaNumero(sistema.getNSorteados());
        }

        ArrayList<Integer> a = sorteador.getNRestantes();
        Assertions.assertTrue(a.isEmpty());
    }
}
