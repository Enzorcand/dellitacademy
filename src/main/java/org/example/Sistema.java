package org.example;

import lombok.Data;

import java.util.*;

@Data
public class Sistema {
    private double arrecadacaoTotal;
    private int lastAposta = 1000;
    private HashMap<String, Apostador> participantes;
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

        Scanner scan = new Scanner(System.in);
        boolean incorrectInput = true;
        System.out.println("Insira o cpf do apostador:");
        String cpf = scan.nextLine();
        if(participantes.get(cpf) == null){
            do {
                cpf = scan.nextLine();
                incorrectInput = registerApostador(cpf);
            } while(incorrectInput);
        }
        arrecadacaoTotal += 5;
        participantes.get(cpf).createAposta(lastAposta, selectApostaType(scan));
        lastAposta++;
        scan.close();
    }

    private String selectApostaType(Scanner scan){
        System.out.println("1 - Aposta manual");
        System.out.println("2 - Aposta \"surpresinha\"");
        return scan.nextLine();
    }
    public boolean registerApostador(String cpf){
        Scanner scan = new Scanner(System.in);
        boolean allNumbers = true;
        if (cpf.length() != 11){
            System.out.println("Tamanho de cpf invalido!");
            return false;
        }
        char[] a = cpf.toCharArray();
        for (char c: a) {
            if(!(c >= '0' && c <= '9')){
                System.out.println("Formato de cpf inválido!");
                return false;
            }
        }
        System.out.println("Insira o nome do apostador:");
        String nome = scan.nextLine();
        Apostador apostador = new Apostador(cpf, nome);
        participantes.put(cpf, apostador);
        scan.close();
        return true;
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


    public boolean checkVencedor(int index){
        Set<String> chaves = participantes.keySet();
        for(String chave: chaves){
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
    public void printVencedores(){

    }

    public void listarApostas() {

    }
}
