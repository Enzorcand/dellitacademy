package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        System.out.println("Bem vindo a Mega Sena Online!");
        boolean canContinue = true;
        do {
            int entrada = selectMenu();
            switch (entrada) {
                case 1 -> sistema.registerAposta();
                case 2 -> sistema.listarApostas();
                case 3 -> {
                    sistema.startSorteio();
                    canContinue = false;
                }
            }
        }while(canContinue);
    }

    public static int selectMenu(){
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }
}