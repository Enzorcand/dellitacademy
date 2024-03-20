package org.example;

import lombok.Data;

import java.util.*;

@Data
public class Apostador implements Comparable<Apostador> {
    private String cpf;
    private double saldo;
    private String nome;
    private HashMap<Integer, Aposta> apostas;
    private ArrayList<Aposta> aPremiadas;

    public Apostador(String cpf, String nome){
        this.saldo = 0;
        this.cpf = cpf;
        this.nome = nome;
        apostas = new HashMap<>();
        aPremiadas = new ArrayList<>();
    }
    public void createAposta(int lastSequencial, String entrada){
        if (!entrada.equalsIgnoreCase("1") &&
                !entrada.equalsIgnoreCase("2")){
            throw new IllegalArgumentException("Ação invalida!");
        }
        Aposta a = new Aposta(lastSequencial + 1);
        if(entrada.equals("1")){
            a.setNumeros(setManualNumbers());
        }
        if(entrada.equals("2")){
            a.setNumeros(setRandomNumbers());
        }
        apostas.put(a.getNSequencial(), a);
    }

    private ArrayList<Integer> setRandomNumbers() {
        Random rand = new Random();
        ArrayList<Integer> a = new ArrayList<>();
        int n;
        for (int i = 0; i < 5;) {
            n = rand.nextInt(1,51);
            if(checkIfExists(a, n)){
                i++;
                a.add(n);
            }
        }
        return a;
    }

    private ArrayList<Integer> setManualNumbers() {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> a = new ArrayList<>();
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

    @Override
    public int compareTo(Apostador a) {
        return this.nome.compareTo(a.getNome());
    }
}
