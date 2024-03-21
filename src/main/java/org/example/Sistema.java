package org.example;

import lombok.Data;

import java.util.*;

/** Classe da aplicação principal, onde toda a interação com o usuário é feita
 */
@Data
public class Sistema {
    private Scanner scan;
    private double arrecadacaoTotal;
    private int lastAposta = 998;
    private int qtdApostasVencedoras;
    private HashMap<String, Apostador> participantes;
    private ArrayList<Apostador> vencedores;
    private ArrayList<Integer> nSorteados;
    private Sorteador sorteio;
    public Sistema(Scanner scan){
        this.scan = scan;
        this.qtdApostasVencedoras = 0;
        this.arrecadacaoTotal = 0;
        this.sorteio = new Sorteador();
        this.vencedores = new ArrayList<>();
        this.participantes = new HashMap<>();
        this.nSorteados = new ArrayList<>();
    }

    /** Metodo que recebe um array de objetos do tipo Numero
     * e verifica quantas vezes cada numero foi apostado
     * @param array ArrayList de Numeros
     */
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

    /**Metodo que gera aposta nova. Recebe input de um cpf.
     * Caso exista participante com este cpf, verifica se o mesmo tem saldo.
     * Após isso chama o método createAposta.
     * Caso não exista participante com este cpf,
     * recebe entrada até encontrar um cpf válido para cadastrar um novo apostador
     */
    public void registerAposta(){
        System.out.println("Insira o cpf do apostador:");
        String cpf = scan.nextLine();
        if(!participantes.containsKey(cpf)){
            while (!registerApostador(cpf)) {
                cpf = scan.nextLine();
            }
        }
        if(!saldoSuficiente(participantes.get(cpf))){
            System.out.println("Saldo insuficiente!");
            return;
        }
        arrecadacaoTotal += 5;
        participantes.get(cpf).createAposta(lastAposta, selectApostaType());
        lastAposta++;

    }

    /**
     * Metodo que verifica se o participante possui saldo para realziar uma nova aposta
     * @return retorna (saldo > 5.0)
     * @param apostador objeto do tipo apostador
     */
    private boolean saldoSuficiente(Apostador apostador) {
        return apostador.getSaldo() >= 5;
    }

    /** Metodo que seleciona o tipo de aposta que sera realizada
     */
    private String selectApostaType(){
        System.out.println("1 - Aposta manual");
        System.out.println("2 - Aposta \"surpresinha\"");
        return scan.nextLine();
    }

    /** Metodo que recebe um cpf como paramero
     */
    public boolean registerApostador(String cpf){
        if (!cpfIsValid(cpf)) return false;
        System.out.println("Insira o nome do apostador:");
        String nome = scan.nextLine();
        Apostador apostador = new Apostador(cpf, nome);
        participantes.put(cpf, apostador);

        return true;
    }

    /** Metodo privado que recebe um cpf e verifica se ele é valido,
     * retornando o resultado.
     * */
    private boolean cpfIsValid(String cpf) {
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
        return true;
    }

    /** Metodo que sorteio os primeiros 5 numeros
     * */
    public void startSorteio(){
        for (int i = 0; i < 5; i++) {
            sorteio.sorteiaNumero(nSorteados);
            checkVencedor(i);
        }

    }

    /** Metodo que começa a apuração dos resultados
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
    }

    /** Metodo que recebe um inteiro como index atual do sorteio
     * e retorna se ouve vencedores ou se o sorteio chegou no index 25
     * */
    public boolean checkVencedor(int index){
        Set<String> chaves = participantes.keySet();
        for(String chave: chaves){
            Apostador p = participantes.get(chave);
            for(Aposta a: p.getApostas().values()){
                checkEscolhido(a,index);
                if(index >= 5) {
                    boolean isWinner = checkPremiada(a);
                    if (isWinner && !p.getAPremiadas().contains(a)) {
                        a.setVencedora(true);
                        qtdApostasVencedoras++;
                        p.getAPremiadas().add(a);
                        if (!vencedores.contains(p)) {
                            vencedores.add(p);
                        }
                    }
                }
            }
        }
        return vencedores.isEmpty() &&
                !(nSorteados.size() >= 40);
    }

    private boolean checkPremiada(Aposta a) {
        for (Numero n : a.getNumeros()) {
            if(!n.isEscolhido()){
                return false;
            }
        }
        return true;
    }

    /** Recebe uma aposta e o index atual do sorteio.
     * Confere se cada um dos numeros é o sorteado.
     * Caso seja, registra o numero como escolhido.
     * Caso todos numeros já tenham sido escolhidos,
     *  retorna true, caso contrario, false*/
    private void checkEscolhido(Aposta a, int index) {
        for(Numero n: a.getNumeros()) {
            if (nSorteados.get(index) == n.getAnInt()) {
                n.setEscolhido(true);
            }
        }
    }

    /** Metodo que inicia a fase de apostas
     * */
    public void startApostas(){
        boolean canContinue = true;
        do{
            canContinue = selectMenu();

        }while(canContinue);
    }

    /** Metodo privado que seleciona qual sera a próxima ação do aplicativo retornando um inteiro
     * */
    private boolean selectMenu(){
        boolean canContinue = true;
        printMenu();
        int i = 0;
        try {
            String entrada = scan.nextLine();
            i = Integer.parseInt(entrada);
        } catch (NumberFormatException ignored){}
        switch (i) {
            case 1 -> registerAposta();
            case 2 -> listarApostas();
            case 3 -> adicionarSaldo();
            case 4 -> {
                executarSorteio();
                canContinue = false;
            }
            default -> System.out.println("Entrada inválida.");
        }
        return canContinue;
    }

    /** Método que realiza todas fazes do aplicativo
     */
    private void executarSorteio() {
        startSorteio();
        startApuracao();
        startResultados();
        startPremiacao();
    }

    private void printMenu() {
        System.out.println("Opção 1: Registrar nova aposta");
        System.out.println("Opção 2: Listar todas apostas");
        System.out.println("Opção 3: Adicionar saldo para apostador");
        System.out.println("Opção 4: Iniciar sorteio");
    }

    /**
     * Metodo privado que permite o programa passar de fase com input do usuario
     */
    private void confirmContinue() {
        System.out.println("Pressione enter para continuar:");
        scan.next();

    }

    /** Método privado que garante que o input digitado sera um inteiro
     * @return retorna o inteiro inputado
     */
    private int garantsInt() {
        return 0;
    }

    /** Métoco que lista todas apostas realizadas até o momento
     * */
    public void listarApostas() {
        for (Apostador apostador : participantes.values()) {
            System.out.print("Apostador: " + apostador.getNome());
            for (Aposta aposta : apostador.getApostas().values()) {
                System.out.printf("           Aposta %s: ", aposta.nSequencial );
                List<Numero> a = aposta.getNumeros();
                for (Numero numero : a) {
                    System.out.print(" " + numero.getAnInt());
                }
                System.out.println();
            }
        }
    }

    /** Método recebe um cpf, verifica se o mesmo é valido e se pertence a um participante.
     * Caso esteja, adiciona saldo ao mesmo
     * */
    public void adicionarSaldo() {
        System.out.println("Insira o cpf do apostador");
        String cpf = scan.nextLine();
        if (cpfIsValid(cpf) && participantes.containsKey(cpf)){
            Apostador a = participantes.get(cpf);
            double d = 0;
            try {
                System.out.println("Insira o saldo desejado");
                String entrada = scan.nextLine();
                d = Double.parseDouble(entrada);
            }catch (NumberFormatException e){
                System.out.println("Formato de número inválido!");
            }
            if (d > 0) {
                a.setSaldo(a.getSaldo() + d);
            } else{
                System.out.println("Valor inválido!");
            }
        } else if (!participantes.containsKey(cpf)) {
            System.out.println("Participante não encontrado");
        } else {
            System.out.println("CPF inválido.");
        }

    }

    /** Metodo que inicia a fase que mostra os resultados do sorteio
     * */
    public void startResultados(){
        printSorteados();
        System.out.println("Número de apostas premiadas: " + qtdApostasVencedoras);
        printVencedores();
        printEstatistica();
    }

    /** Metodo que printa no console as estaticas dos numeros mais selecionados
     * */
    private void printEstatistica() {
        ArrayList<Numero> apostados = ordenaNumeros();
        System.out.println("Nro apostado | Qtd de apostas");
        for (Numero n: apostados) {
            System.out.println(n.getAnInt() + " " + n.getNVezesEscolhido());
        }
    }

    /** Metodo que printa no console todos numeros sorteados
     * */
    private void printSorteados(){
        Collections.sort(nSorteados);
        System.out.println("Números sorteados:");
        for (int i: nSorteados) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /** Metodo que printa os vencedores da mega sena
     * */
    private void printVencedores(){
        if(vencedores.isEmpty()){
            System.out.println("Não existem vencedores.");
            return;
        }
        System.out.println("Vencedores:");
        for (Apostador apostador : vencedores) {
            System.out.println(apostador.getNome() + ": ");
            for(Aposta a : apostador.getAPremiadas()){
                System.out.println("Aposta: " + a.nSequencial);
            }
            System.out.println();
        }
    }

    /** Método que ordena as estatisticas dos números em
     * ordem decrescente dos mais escolhidos.
     * @return Array ordenado.
     */
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

    /** Metodo que inicia a fase de premiação
     * */
    public void startPremiacao(){
        if(vencedores.isEmpty()){
            return;
        }
        if(vencedores.size() == 1){
            givePremio();
            return;
        }
        int i = choosePremiacaoType();
        if(i == 1){
            doLastSorteio();
            givePremio();
        } else {
            givePremio();
        }
    }

    /** Metodo que decide qual vai ser o modo de premiação
     * @return retorna 1 pra sorteio novo, 2 pra não sortear
     */
    private int choosePremiacaoType() {
        System.out.println("Sorteio extra!");
        System.out.println("Deseja ressortear para ter a possibilidade de ser o único ganhador? y/n");
        System.out.println("Obs: 50% + 1 dos vencedores devem aceitar.");
        int y = 0;
        int n = 0;
        for (int i = 0; i < vencedores.size(); i++) {
            if (scan.nextLine().equals("y")){
                y++;
            } else {
                n++;
            }
        }

        if(y > n){
            return 1;
        }else {
            return 2;
        }
    }

    /** Metodo que realiza um último sorteio para decidir quem sera o unico ganhador
     */
    private void doLastSorteio() {
        Random random = new Random();
        int i = random.nextInt(0, vencedores.size() + 1);
        Apostador ganhador = vencedores.get(i);
        vencedores.clear();
        vencedores.add(ganhador);
    }

    /** Metodo que divide igualmente o premio entre todos os vencedores
     */
    private void givePremio() {
        for (Apostador a: vencedores) {
            System.out.println("Parabens " + a.getNome() + ", você ganhou a mega sena!");
            a.setSaldo(arrecadacaoTotal/vencedores.size() + a.getSaldo());
            System.out.println("Saldo atual: " + a.getSaldo());
        }
        arrecadacaoTotal = 0;
    }


    /**
     * Metodo que limpa as apostas dos usuários para realizar um novo sorteio
     */
    public void clearApostas() {
        nSorteados.clear();
        sorteio.geraNumeros();
        for (Apostador apostador : participantes.values()) {
            apostador.getApostas().clear();
            apostador.getAPremiadas().clear();
        }
        lastAposta = 1000;
    }
}

