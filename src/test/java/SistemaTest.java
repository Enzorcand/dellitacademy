import org.example.Aposta;
import org.example.Apostador;
import org.example.Sistema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

class SistemaTest {

    @Test
    void zeroWinners(){
        Sistema sistema = new Sistema();
        sistema.startSorteio();
        Assertions.assertTrue(sistema.getVencedores().isEmpty());
    }

    @Test
    void vencedoresAreSorted(){
        Sistema sistema = new Sistema();
        char x = 'z';
        boolean isSorted = true;
        for (int i = 0; i < 10; i++) {
            x--;
            Apostador a = new Apostador(i, x + "");
            sistema.getVencedores().add(a);
        }
        ArrayList<Apostador> array = sistema.getVencedores();
        for(int i = 0; i < array.size() - 1; i++) {
            int n = array.get(i).compareTo(array.get(i + 1));
            if (n < 0 ) {
                isSorted = false;
            }
        }
        sistema.startApuracao();
        Assertions.assertTrue(isSorted);
    }

    @Test
    void sorteioIsWinnable(){
        Sistema sistema = new Sistema();
        char c = 'a';
        for (int i = 0; i < 100; i++) {

            Apostador a = new Apostador(i, c + "");
            a.createAposta(i, 2);
            c++;
        }
        sistema.startSorteio();

        Assertions.assertTrue(sistema.getVencedores().isEmpty());
    }


}
