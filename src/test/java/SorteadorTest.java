import org.example.Sistema;
import org.example.Sorteador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SorteadorTest {

    @Test
    void numberIsRemoved(){
        Sistema sistema = new Sistema();
        Sorteador sorteador = new Sorteador();
        Assertions.assertTrue(sorteador.sorteiaNumero(sistema.getNSorteados()));
    }

    @Test
    void nSorteadosLimitIs25(){
        Sistema sistema = new Sistema();
        Sorteador sorteador = new Sorteador();
        for (int i = 0; i < 25; i++) {
            sorteador.sorteiaNumero(sistema.getNSorteados());
        }
        Assertions.assertFalse(sorteador.sorteiaNumero(sistema.getNSorteados()));
    }

    @Test
    void allNumbersArePossible(){
        Sistema sistema = new Sistema();
        Sistema sistema2 = new Sistema();
        Sorteador sorteador = new Sorteador();
        for (int i = 0; i < 25; i++) {
            sorteador.sorteiaNumero(sistema.getNSorteados());
        }

        ArrayList<Integer> a = sorteador.getNRestantes();
        Assertions.assertNotEquals(1, a.get(0));
        Assertions.assertNotEquals(50, a.get(a.size()-1));
    }
}
