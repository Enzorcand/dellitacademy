package org.example;

import lombok.Data;

import java.util.*;

@Data
public class Sistema {
    private double arrecadacaoTotal;
    private int lastAposta = 1000;
    private HashMap<Integer, Apostador> participantes;
    private ArrayList<Apostador> vencedores;
    private ArrayList<Integer> nSorteados;
    private Sorteador sorteio;
    public Sistema(){
        this.arrecadacaoTotal = 0;
        this.sorteio = new Sorteador();
        this.vencedores = new ArrayList<>();
        this.participantes = new HashMap<>();
        this.nSorteados = new ArrayList<>();
    }

    public int nVezesEscolhido(int numero){
        return 0;
    }
    public void registerAposta(){
        System.out.println("Insira o cpf do apostador:");
        Scanner scan = new Scanner(System.in);
        int cpf = scan.nextInt();
        if(participantes.get(cpf) == null){
            System.out.println("Apostador não cadastrado. Deseja cadastra-lo? (y/n)");
            String entrada = scan.nextLine();
            if (entrada.equalsIgnoreCase("Y")||
                    entrada.equalsIgnoreCase("y")){
                registerApostador(cpf);
                return;
            }
        }
        arrecadacaoTotal += 5;
        participantes.get(cpf).createAposta(lastAposta);
        lastAposta++;
    }
    public void registerApostador(int cpf){
        Scanner scan = new Scanner(System.in);
        System.out.println("Insira o nome do apostador:");
        String nome = scan.nextLine();
        Apostador apostador = new Apostador(cpf, nome);
        participantes.put(cpf, apostador);
    }
    public void registerApostador(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Insira o cpf do apostador:");
        int cpf = Integer.parseInt(scan.nextLine());
        registerApostador(cpf);
    }

    public void startSorteio(){
        for (int i = 0; i < 5; i++) {
            sorteio.sorteiaNumero(nSorteados);
            checkVencedor(i);
        }
        startApuracao();
    }
    public void startApuracao(){
        boolean canContinue = true;
        int i = 5;
        if(!vencedores.isEmpty()){
            canContinue = false;
        }
        while (canContinue){
            sorteio.sorteiaNumero(nSorteados);
            canContinue = checkVencedor(i);
            i++;
        }
        if(vencedores.isEmpty()){
          return;
        }
        Collections.sort(vencedores);
    }
    private boolean checkEscolhido(Aposta a, int index) {
        boolean isWinner = true;
        for(Numero n: a.getNumeros()){
            if(!n.isEscolhido()){
                isWinner = false;
            }
            if (nSorteados.get(index) == n.getAnInt()) {
                n.setEscolhido(true);
            }
        }
        return isWinner;
    }

    public boolean checkVencedor(int index){
        Set<Integer> chaves = participantes.keySet();
        for(int chave: chaves){
            Apostador p = participantes.get(chave);
            for(Aposta a: p.getApostas().values()){
                boolean isWinner = checkEscolhido(a,index);
                if(isWinner){
                    a.setVencedora(true);
                }
                p.getAPremiadas().add(a);
                if(!vencedores.contains(p)){
                    vencedores.add(p);
                }
            }
        }
        return !vencedores.isEmpty() ||
                !(nSorteados.size() >= 25);
    }
    public void printVencedores(){

    }

    public void listarApostas() {
    }
}
