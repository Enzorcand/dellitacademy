package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        System.out.println("Bem vindo a Mega Sena Online!");
        boolean newSorteio = true;
        do{
            sistema.startApostas();
            System.out.println("Deseja realizar outro sorteio? y/n");
            newSorteio = continueApp();
        }while (newSorteio);
    }
    private static boolean continueApp(){
        Scanner scan = new Scanner(System.in);
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
        scan.close();
        return bool;
    }
}