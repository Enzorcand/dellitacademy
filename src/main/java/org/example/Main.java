package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Sistema sistema = new Sistema(scan);
        System.out.println("Bem vindo a Mega Sena Online!");
        boolean newSorteio;
        do{
            sistema.startApostas();
            System.out.println("Deseja realizar outro sorteio? y/n");
            newSorteio = continueApp(scan);
            sistema.clearApostas();
        }while (newSorteio);
        scan.close();
    }

    /**
     * Método que define se será realizado outro sorteio ou não
     * @return boolean
     */
    private static boolean continueApp(Scanner scan){
        boolean bool = false;
        boolean bool2 = true;
        do {
            String entrada = scan.nextLine();
            if(entrada.equals("y") || entrada.equals("Y")){
                bool = true;
                bool2 = false;
            } else if (entrada.equals("n") || entrada.equals("N")) {
                bool = false;
                bool2 = false;
            } else {
                System.out.println("Entrada inválida!");
            }

        }while (bool2);
        return bool;
    }
}