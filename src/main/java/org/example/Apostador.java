package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Data
public class Apostador {
    private int cpf;
    private String nome;
    private ArrayList<Aposta> apostas;

    public Apostador(int cpf, String nome){
        this.cpf = cpf;
        this.nome = nome;
    }

    public void createAposta(int lastSequencial){
        Scanner scan = new Scanner(System.in);
        System.out.println("0 - Voltar");
        System.out.println("1 - Aposta manual");
        System.out.println("2 - Aposta \"surpresinha\"");

        String entrada = scan.nextLine();
        if (!entrada.equalsIgnoreCase("0") ||
                !entrada.equalsIgnoreCase("1") ||
                !entrada.equalsIgnoreCase("2")){
            throw new IllegalArgumentException("Ação invalida!");
        }
        Aposta a = new Aposta(lastSequencial);
        if(entrada.equals("1")){
            a.setNumeros(setManualNumbers());
        }
        if(entrada.equals("2")){
            a.setNumeros(setRandomNumbers());
        }
    }

    private ArrayList<Integer> setRandomNumbers() {
        Random rand = new Random();
        ArrayList<Integer> a = new ArrayList<Integer>();
        int n;
        for (int i = 0; i < 5;) {
            n = rand.nextInt(1,50);
            if(checkIfExists(a, n)){
                i++;
                a.add(n);
            }
        }
        return a;
    }

    private ArrayList<Integer> setManualNumbers() {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> a = new ArrayList<Integer>();
        int n;

        System.out.println("Insira 5 valores diferentes para a aposta de 1 a 50:");
        for (int i = 0; i < 5;) {
            n = scan.nextInt();
            if(!checkIfExists(a, n) || (n <= 50 && n > 0)){
                System.out.println("Número inválido!");
            } else if(checkIfExists(a, n)){
                i++;
                a.add(n);
            }
        }
        return a;
    }

    private boolean checkIfExists(ArrayList<Integer> a, int n){
        boolean bool = true;
        for (int m:
                a) {
            if (n == m) {
                bool = false;
                break;
            }
        }
        return bool;
    }
}
