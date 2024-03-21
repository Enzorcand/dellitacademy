import org.example.Aposta;
import org.example.Apostador;
import org.example.Numero;
import org.example.Sistema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class SistemaTest {

    @Test
    void zeroWinners(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        sistema.startSorteio();
        Assertions.assertTrue(sistema.getVencedores().isEmpty());
    }

    @Test
    void vencedoresAreSorted(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        char x = 'z';
        boolean isSorted = true;
        for (int i = 0; i < 10; i++) {
            x--;
            Apostador a = new Apostador(i + "", x + "");
            sistema.getVencedores().add(a);
        }
        ArrayList<Apostador> array = sistema.getVencedores();
        for(int i = 0; i < array.size() - 1; i++) {
            int n = array.get(i).compareTo(array.get(i + 1));
            if (n < 0) {
                isSorted = false;
                break;
            }
        }
        sistema.startApuracao();
        Assertions.assertTrue(isSorted);
    }

    @Test
    void sorteioIsWinnable(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        char c = 'a';
        for (int i = 0; i < 10000; i++) {
            String cpf = randomCpf();
            Apostador a = new Apostador(cpf, c + "");
            sistema.getParticipantes().put(cpf, a);
            a.createAposta(i, "2");
            c++;
        }
        sistema.startSorteio();
        Assertions.assertFalse(sistema.getVencedores().isEmpty());
    }

    @Test
    void invalidCpfNotAcepted(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        Assertions.assertFalse(sistema.registerApostador("12"));
        Assertions.assertFalse(sistema.registerApostador("abc12345678"));
    }

    @Test
    void escolhidosIsSorted(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        char c = 'a';
        for (int i = 0; i < 300; i++) {
            String cpf = randomCpf();
            Apostador a = new Apostador(cpf, c + "");
            sistema.getParticipantes().put(cpf, a);
            a.createAposta(i, "2");
            c++;
        }
        ArrayList<Numero> array = sistema.ordenaNumeros();
        boolean isSorted = true;
        for(int i = 0; i < array.size() - 1; i++) {
            int n = array.get(i).compareTo(array.get(i + 1));
            if (n > 0) {
                isSorted = false;
                break;
            }
        }
        Assertions.assertTrue(isSorted);
    }
    @Test
    void oneWinnerCanWinTwoApostas(){
        Sistema sistema = new Sistema(new Scanner(System.in));
        char c = 'a';
        String cpf = randomCpf();
        Apostador a = new Apostador(cpf, c + "");
        sistema.getParticipantes().put(cpf, a);
        for (int i = 0; i < 300; i++) {
            a.createAposta(i, "2");
            c++;
        }
        sistema.startSorteio();
        sistema.startApuracao();

        Assertions.assertEquals(1, sistema.getVencedores().size());
    }
    private String randomCpf() {
        String cpf = "";
        Random rand = new Random();
        for (int i = 0; i < 11; i++) {
            cpf = cpf + rand.nextInt(0, 9);
        }
        return cpf;
    }
}
