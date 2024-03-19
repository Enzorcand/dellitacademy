package org.example;

import lombok.Data;

import java.util.*;

@Data
public class Sistema {
    private Aposta lastAposta;
    private HashMap<Integer, Apostador> participantes;
    private SortedSet<Apostador> vencedores;
    private ArrayList<Integer> nSorteados;
    private Sorteador sorteio;
    public Sistema(){
        this.sorteio = new Sorteador();
        this.vencedores = new SortedSet<Apostador>() {
            @Override
            public Comparator<? super Apostador> comparator() {
                return null;
            }

            @Override
            public SortedSet<Apostador> subSet(Apostador fromElement, Apostador toElement) {
                return null;
            }

            @Override
            public SortedSet<Apostador> headSet(Apostador toElement) {
                return null;
            }

            @Override
            public SortedSet<Apostador> tailSet(Apostador fromElement) {
                return null;
            }

            @Override
            public Apostador first() {
                return null;
            }

            @Override
            public Apostador last() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Apostador> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Apostador apostador) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Apostador> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
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
        lastAposta = participantes.get(cpf).createAposta(lastAposta.getNSequencial());
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
        int i = 0;
        boolean canContinue = true;
        while(canContinue){
            canContinue = sorteio.sorteiaNumero(nSorteados);
            canContinue = checkVencedores(i);
            i++;
        }
        System.out.println("Sorteio finalizado!");
    }


    //To do: fazer uma versão do método sem parametros para os 4 primeiros numeros
    public boolean checkVencedores(int index){
        Set<Integer> chaves = participantes.keySet();
        for(int chave: chaves){
            Apostador p = participantes.get(chave);
            for(Aposta a: p.getApostas()){
                boolean isWinner = true;
                for(Numero n: a.getNumeros()){
                    if(!n.isEscolhido()){
                        isWinner = false;
                    } else if (nSorteados.get(index) == n.getValor()) {
                        n.setEscolhido(true);
                    }
                }
                if(isWinner){
                    a.setVencedora(true);
                }
            }
        }
        return !vencedores.isEmpty() ||
                !(nSorteados.size() >= 25);
    }
}
