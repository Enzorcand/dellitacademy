package org.example;

import lombok.Data;

import java.security.Key;
import java.util.*;

@Data
public class Sistema {
    private double arrecadacaoTotal;
    private int lastAposta = 1000;
    private int qtdApostasVencedoras;
    private HashMap<String, Apostador> participantes;
    private ArrayList<Apostador> vencedores;
    private ArrayList<Integer> nSorteados;
    private Sorteador sorteio;
    public Sistema(){
        this.qtdApostasVencedoras = 0;
        this.arrecadacaoTotal = 0;
        this.sorteio = new Sorteador();
        this.vencedores = new ArrayList<>();
        this.participantes = new HashMap<>();
        this.nSorteados = new ArrayList<>();
    }

    public ArrayList<Numero> ordenaNumeros(){
        ArrayList<Numero> array = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            Numero n = new Numero(i+1);
            array.add(n);
        }
        nVezesEscolhido(array);
        Collections.sort(array);
        array.removeIf(n -> n.getNVezesEscolhido() == 0);
        return array;
    }

    private void nVezesEscolhido(ArrayList<Numero> array) {
        for (Apostador apostador: participantes.values()) {
            for (Aposta aposta: apostador.getApostas().values()) {
                for (Numero n: aposta.getNumeros()) {
                    for (Numero m: array) {
                        if (m.getAnInt() == n.getAnInt()){
                            m.setNVezesEscolhido(m.getNVezesEscolhido() + 1);
                        }
                    }
                }
            }
        }
    }

    /** Metodo que gera aposta nova. Recebe input de um cpf.
    * Caso exista participante com este cpf, verifica se o mesmo tem saldo.
    * Após isso chama o método createAposta.
    * Caso não exista participante com este cpf,
    * recebe entrada até encontrar um cpf válido para cadastrar um novo apostador
     */
    public void registerAposta(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Insira o cpf do apostador:");
        String cpf = scan.nextLine();
        if(participantes.get(cpf) == null){
            while (!registerApostador(cpf)) {
                cpf = scan.nextLine();
            }
        }
        if(!saldoSuficiente()){
            System.out.println("Saldo insuficiente!");
            return;
        }
        arrecadacaoTotal += 5;
        participantes.get(cpf).createAposta(lastAposta, selectApostaType(scan));
        lastAposta++;
        scan.close();
    }

    /** Metodo que verifica se o participante possui saldo para realziar uma nova aposta
     */
    private boolean saldoSuficiente() {
        return true;
    }

    /** Metodo que seleciona o tipo de aposta que sera realizada
     */
    private String selectApostaType(Scanner scan){
        System.out.println("1 - Aposta manual");
        System.out.println("2 - Aposta \"surpresinha\"");
        return scan.nextLine();
    }

    /** Metodo que recebe um cpf como paramero
     */
    public boolean registerApostador(String cpf){
        Scanner scan = new Scanner(System.in);
        if (cpfIsValid(cpf)) return false;
        System.out.println("Insira o nome do apostador:");
        String nome = scan.nextLine();
        Apostador apostador = new Apostador(cpf, nome);
        participantes.put(cpf, apostador);
        scan.close();
        return true;
    }

    /**
     * */
    private static boolean cpfIsValid(String cpf) {
        if (cpf.length() != 11){
            System.out.println("Tamanho de cpf invalido!");
            return true;
        }
        char[] a = cpf.toCharArray();
        for (char c: a) {
            if(!(c >= '0' && c <= '9')){
                System.out.println("Formato de cpf inválido!");
                return true;
            }
        }
        return false;
    }

    public void startSorteio(){
        for (int i = 0; i < 5; i++) {
            sorteio.sorteiaNumero(nSorteados);
            checkVencedor(i);
        }

    }

    /**
     * */
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
//        System.out.println("Pressione enter para prosseguir:");
//        Scanner scan = new Scanner(System.in);
//        scan.next();
//        scan.close();
    }

    /**
     * */
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

    /** Recebe uma aposta e o index atual do sorteio.
     * Confere se cada um dos numeros é o sorteado.
     * Caso seja, registra o numero como escolhido.
     * Caso todos numeros já tenham sido escolhidos,
     *  retorna true, caso contrario, false*/
    private boolean checkEscolhido(Aposta a, int index) {
        boolean isWinner = true;
        for(Numero n: a.getNumeros()){
            if (nSorteados.get(index) == n.getAnInt()) {
                n.setEscolhido(true);
            }
            if(!n.isEscolhido()){
                isWinner = false;
            }
        }
        return isWinner;
    }

    /**
     * */
    public void startResultados(){
        printSorteados();
        System.out.println("Número de apostas premiadas: " + qtdApostasVencedoras);
        printVencedores();
        printEstatistica();
//        System.out.println("Pressione enter para prosseguir:");
//        Scanner scan = new Scanner(System.in);
//        scan.next();
//        scan.close();
    }

    /**
     * */
    private void printEstatistica() {
        ArrayList<Numero> apostados = ordenaNumeros();
        System.out.println("Nro apostado | Qtd de apostas");
        for (Numero n: apostados) {
            System.out.println(n.getAnInt() + " " + n.getNVezesEscolhido());
        }
    }

    /**
     * */
    private void printSorteados(){
        System.out.println("Números sorteados:");
        for (int i: nSorteados) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * */
    private void printVencedores(){
        if(vencedores.isEmpty()){
            System.out.println("Não existem vencedores.");
            return;
        }
        System.out.println("Vencedores:");
        for (Apostador apostador : vencedores) {
            System.out.print(apostador.getNome());
            for(Aposta a : apostador.getAPremiadas()){
                System.out.println("Aposta: " + a.nSequencial);
            }
        }
    }

    /**
     * */
    public void startPremiacao(){

    }

    /**
     * */
    public void listarApostas() {
        for (Apostador apostador : participantes.values()) {
                System.out.print("Apostador: " + apostador.getNome());
            for (Aposta aposta : apostador.getApostas().values()) {
                System.out.print("           Aposta %i: Nome: %s; ");
                List<Numero> a = aposta.getNumeros();
                for (Numero numero : a) {
                    System.out.println(" " + numero.getAnInt());
                }
                System.out.println();
            }
        }
    }

    /**
     * */
    public void startApostas(){
        boolean canContinue = true;
        do{
            int entrada = selectMenu();
            switch (entrada) {
                case 1 -> registerAposta();
                case 2 -> listarApostas();
                //Método recebe um cpf, caso valido, executa o metodo de adição de saldo
                case 3 -> {
                    Scanner scan = new Scanner(System.in);
                    String cpf = scan.nextLine();
                    if (cpfIsValid(cpf)){
                        adicionarSaldo(cpf);
                    } else {
                        System.out.println("CPF não encontrado ou inválido.");
                    }
                    scan.close();
                }
                case 4 -> {
                    startSorteio();
                    startApuracao();
                    startResultados();
                    startPremiacao();
                    canContinue = false;
                }
                default -> System.out.println("Entrada inválida.");
            }
        }while(canContinue);
    }

    /** Método recebe um cpf, verifica se o mesmo é valido e se pertence a um participante.
     * Caso esteja, adiciona saldo ao mesmo
     * */
    public void adicionarSaldo(String cpf) {
        
    }
    /** Metodo privado que seleciona qual sera a próxima ação do aplicativo retornando um inteiro
     * */
    private static int selectMenu(){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.close();
        return n;
    }
}
