import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import static java.lang.System.exit;

public class Programa implements Serializable {

    public static Entidade loginEntidade(Sistema sys){
        Scanner sc = new Scanner(System.in);
        boolean dadosOk = false;
        Entidade e = null;
        int nif;
        String pw;

        do{
            System.out.println("NIF: ");
            nif = sc.nextInt(); 

            System.out.println("Password: ");
            pw = sc.next();
            
            dadosOk = sys.logIn(nif, pw);
            
            if(!dadosOk)
                System.out.println("NIF ou password incorretos. Tente novamente.");
                
        }while(!dadosOk);
        
        try{
            e = sys.getEntidade(nif);
        }
        catch (NIFNaoRegistadoException execp){
            System.out.println(execp.getMessage());
        }
        
        return e;
    }

    public static void registaContribuinte(Sistema sys){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        int nif, dep_familia = 0, n_distrito, nif_filho, n_filhos, n_nifs_inseridos=0;
        double coe_fiscal = 0;
        String email, nome, password;
        Morada morada;
        Distrito[] distritos = Distrito.values();
        Distrito distrito;
        GestorSetor gestor = new GestorSetor();
        ArrayList<Integer> nif_familia = new ArrayList<>();
        ArrayList<Setor> setores = new ArrayList<Setor>(); // FIXME: 16/05/2018 como vamos criar novos setores ao ler uma string do scanner?
        ArrayList<Fatura> faturas = new ArrayList<Fatura>(), faturas_pendentes = new ArrayList<Fatura>();

        System.out.println("NIF: ");
        nif = sc.nextInt();

        System.out.println("Nome: ");
        nome = sc.next();

        System.out.println("E-mail: ");
        email = sc.next();

        System.out.println("Password: ");
        password = sc.next();

        System.out.println("Rua: ");
        String rua = sc.next();

        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal = sc.next();

        System.out.println("Distrito:");
        for(Distrito d : distritos)
            System.out.printf("%d - %s\n", d.ordinal()+1, d.getNome());
            
        do{
            n_distrito = sc.nextInt();            
        }
        while(n_distrito < 1 || n_distrito > distritos.length);
        
        distrito = distritos[n_distrito-1];
        
        morada = new Morada(rua, cod_postal,distrito);

        System.out.println("Número de filhos:");
        n_filhos = sc.nextInt();
        
        System.out.println("Insira o(s) seu(s) NIF(s)");
        while(n_nifs_inseridos != n_filhos){
            nif_filho = sc.nextInt();
            if(!nif_familia.contains(nif_filho)){
                nif_familia.add(nif_filho);
                n_nifs_inseridos++;
            }
            else 
                System.out.println("Já inseriu esse NIF");
        }

        Contribuinte c = new Contribuinte(nif, email, nome, morada, password, dep_familia, nif_familia, gestor, faturas, faturas_pendentes);

        sys.registaEntidade(c);

        System.out.println("Registo efetuado com sucesso!");

    }

    public static void registaEmpresa(Sistema sys){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        int nif, n_distrito, n_setores, n_escolhidos=0;
        String email, nome, password, setor_escolhido;
        Morada morada;
        GestorSetor setores_aux = new GestorSetor();
        List<Setor> setores = setores_aux.getSetores();
        Distrito[] distritos = Distrito.values();
        Distrito distrito;
        ArrayList<String> set_emp = new ArrayList<>(); // FIXME: 16/05/2018 como vamos criar novos setores ao ler uma string do scanner?
        ArrayList<Fatura> faturas_emitidas = new ArrayList<>();

        System.out.println("NIF: ");
        nif = sc.nextInt();

        System.out.println("Nome: ");
        nome = sc.next();

        System.out.println("E-mail: ");
        email = sc.next();

        System.out.println("Password: ");
        password = sc.next();

        System.out.println("Rua: ");
        String rua = sc.next();
        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal = sc.next();
        
        System.out.println("Distrito:");
        for(Distrito d : distritos)
            System.out.printf("%d - %s\n", d.ordinal()+1, d.getNome());     
        
        do{
            n_distrito = sc.nextInt();            
        }
        while(n_distrito < 1 || n_distrito > distritos.length);

        distrito = distritos[n_distrito-1];
        
        System.out.println("Número de setores económicos em que a empresa participa:"); 
        do{
            n_setores = sc.nextInt();
        }while(n_setores < 1 || n_setores > setores.size());    
        
        do{
            setor_escolhido = escolheSetor();
            if(!set_emp.contains(setor_escolhido)){
                set_emp.add(setor_escolhido);
                n_escolhidos++;
            }
            else
                System.out.println("Já escolheu esse setor.");
        }while(n_escolhidos != n_setores);
        
        morada = new Morada(rua, cod_postal,distrito);

        Empresa e = new Empresa(nif, email, nome, morada, password, set_emp, faturas_emitidas);

        sys.registaEntidade(e);

        System.out.println("Registo efetuado com sucesso!");

    }
   
    // FIXME: 20/05/2018 Ver taxas e setores
    public static void criaFatura(Empresa emp, Sistema sys){
        Scanner sc = new Scanner(System.in);
        LocalDate data;
        int nif_cliente;
        String descricao;
        double valor;

        System.out.println("NIF Cliente: ");
        nif_cliente = sc.nextInt();

        System.out.println("Valor: ");
        valor = sc.nextDouble();

        System.out.println("Descrição: ");
        descricao = sc.next();
        
        data = LocalDate.now();
        
        Fatura f = new Fatura(emp.getNome(), emp.getNif(), data, nif_cliente, descricao, new LogSetor(), valor, 0, emp.getBonusDeducao());

        try{
            sys.addFaturaSistema(f);
        }
        catch(NIFNaoRegistadoException e){
            System.out.println(e.getMessage());
        }

    }
    
    public static Fatura escolheFatura(List<Fatura> faturas){
        Scanner sc = new Scanner(System.in);
        Fatura f_escolhida = null;
        int i = 1, opcao;
        
        System.out.println("Escolha uma Fatura:");
        for(Fatura f : faturas)
            System.out.printf("%d - %s %s %f\n", i++, f.getData().toString(), f.getEmpresa(), f.getValor());
       
        System.out.printf("\n\n0 - Retroceder");    
        
        do{
            opcao = sc.nextInt();              
        }
        while(opcao < 0 || opcao > faturas.size());
        
        if(opcao != 0)
            f_escolhida = faturas.get(opcao-1);
        
        return f_escolhida;        
    }
    
    public static String escolheSetor(){
        Scanner sc = new Scanner(System.in);
        GestorSetor aux = new GestorSetor();
        List <Setor> setores = aux.getSetores();
        String setor_escolhido = null;
        int i = 1, opcao;
        
        System.out.println("Escolha um Setor de Atividade Económica:");
        for(Setor setor : setores)
            System.out.printf("%d - %s\n", i++, setor.getNome());
       
        System.out.printf("\n\n0 - Retroceder");    
        
        do{
            opcao = sc.nextInt();              
        }
        while(opcao < 0 || opcao > setores.size());
        
        if(opcao != 0)
            setor_escolhido = setores.get(opcao-1).getNome();
        
        return setor_escolhido;    
    }
    

 
    // Metodo que permite atribuir sector a uma fatura
    // recebe como argumentos uma lista de faturas para poder ser aplicada tanto a pendentes como em casos de alteracao de setor
    public static void atribuiSetor(Sistema sys, Contribuinte c, List<Fatura> faturas){
        Fatura a_alterar;
        String setor;
        
        if(faturas.size() == 0)
            System.out.println("Não tem faturas nesta categoria");
            
        else{   
            
            do{
                a_alterar = escolheFatura(faturas);
                setor = escolheSetor();
            
                if(a_alterar == null)
                    break;
            }while(setor == null);
        
            if(a_alterar != null)
                sys.alteraSetorFatura(c, a_alterar, setor);                
        }
    }
    
    
    
    
    
    
    // Este metodo pode ser generalizado para contribuinte e empresas (inclusao arrayList faturas na entidade)
    // Acrescentando um parametro pode ser usado para atribuir ordenacao á ordem de impressao
    public static void printFaturas(Contribuinte c){
        List <String> toPrint =  c.getFaturas().stream().map(Fatura::toString).collect(Collectors.toList());
        
        System.out.println(toPrint);
    }

    // Método que imprime e ordena as faturas de uma empresa segundo uma ordem especifica (passada como arg: 0 pela data, 1 pelo valor)
    public static void printFaturas(Empresa e, int ord){
        List <String> toPrint;

        if(ord==0)
            toPrint =  e.faturasEmitidasData().stream().map(Fatura::toString).collect(Collectors.toList());
        else
            toPrint =  e.faturasEmitidasValor().stream().map(Fatura::toString).collect(Collectors.toList());

        System.out.println(toPrint);
    }

    // Método que imprime e ordena as faturas de uma empresa de um determinado contribuinte
    // segundo uma ordem especifica (passada como arg: 0 pela data, 1 pelo valor)
    public static void printFaturas(Empresa e, int nif, LocalDate inicio, LocalDate fim, int ord){
        List <String> toPrint;

        if(ord==0)
            toPrint =  e.faturasFromNIF(nif,inicio,fim).stream().map(Fatura::toString).collect(Collectors.toList());
        else
            toPrint =  e.faturasFromNIF(nif).stream().map(Fatura::toString).collect(Collectors.toList());

        System.out.println(toPrint);
    }

    public static void consultaFaturasNIF(Empresa e, int ord) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduza o NIF pretendido: ");
        int nif = sc.nextInt();
        if (ord==0) {
            System.out.println("Introduza a data de início do intervalo a consultar (AAAA-MM-DD): ");
            String str_inicio = sc.next();
            LocalDate inicio = LocalDate.parse(str_inicio);
            System.out.println("Introduza a data de fim do intervalo a consultar (AAAA-MM-DD): ");
            String str_fim = sc.next();
            LocalDate fim = LocalDate.parse(str_fim);
            printFaturas(e, nif, inicio, fim, ord);
        }
        else printFaturas(e, nif, null, null, ord);
    }

        
    // Metodo para fazer display dos valores deduzidos pelo contribuinte até então
    public static void printDeducoes(Contribuinte c){
        for(Setor s : c.getSetores()){
            System.out.println(s.getClass().getSimpleName() + ": " + s.getMontDeduzido());
        }

        System.out.println("Total Deduzido: " + c.totalDeduzido());
    }

    // Metodo para fazer display do valor total faturado por uma empresa
    public static void printValorFaturado(Empresa e){
        System.out.println("Total Faturado: " + e.totalFaturado());
    }
    
    // Metodo para consultar o valor deduzido pelos familiares
    public static void printDeducoesFamilia(Contribuinte c, Sistema sys){
        Contribuinte cf;
        
        for(int nif : c.getNIFFamilia()){
            try{
                cf = (Contribuinte) sys.getEntidade(nif);
                System.out.println(cf.getNome());
                printDeducoes(cf);
            }
            catch (NIFNaoRegistadoException e){
                System.out.println(e.getMessage());
            }
        }
        
        if(c.getNIFFamilia().size() == 0)
            System.out.println("Não possui elementos no seu agregado familiar");
    }
    
    
    public static void main(String args[]){
        Sistema sys = new Sistema();
        Menu menu = new Menu();
        int estado = 0; 
        Entidade entidade_ativa = null;


        try {
            sys = Sistema.loadSystem("data");
        }
        catch (IOException e){
            System.out.println("Não conseguiu ler ficheiro de dados"); // FIXME: 18/05/2018
        }
        catch (ClassNotFoundException e){
            System.out.println("Não conseguiu ler ficheiro de dados 2"); // FIXME: 18/05/2018
        }
        /*
        try{
            menu = Menu.loadMenu("menu");
        }
        catch (IOException e){
            System.out.println("Não conseguiu ler ficheiro de menu"); // FIXME: 18/05/2018
        }
        catch (ClassNotFoundException e){
            System.out.println("Não conseguiu ler ficheiro de menu 2");
        }
        */

        while(estado != -1){
            switch(estado){
                case 0: do{ // ESTADO = 0 -> LOGIN PAGE
                            System.out.println("JAVA FATURA - INÍCIO\n");
                            menu.showOps(0);

                            switch (menu.getOp()){
                                case 1: entidade_ativa = loginEntidade(sys);
                                
                                        if(entidade_ativa.getClass().getSimpleName().equals("Contribuinte"))
                                            estado = 1;
                                        else
                                            if(entidade_ativa.getClass().getSimpleName().equals("Empresa"))
                                                estado = 2;
                                            else
                                                estado = 3;
                                        break;

                                case 2: registaContribuinte(sys);
                                        break;

                                case 3: registaEmpresa(sys);
                                        break;

                                case 0: estado = -1;
                                        break;
                            }
                        } while(estado == 0);
                        break;
                case 1: Contribuinte contribuinte = (Contribuinte) entidade_ativa;
                        do{ // ESTADO = 1 -> Página do Contribuinte
                            System.out.println("BEM-VINDO(A) "+(contribuinte).getNome()+"!\n");
                            menu.showOps(1);

                            switch (menu.getOp()){
                                
                                case 1: printFaturas(contribuinte); 
                                        break;
                                        
                                case 2: printDeducoes(contribuinte);
                                        break;
                                        
                                case 3: printDeducoesFamilia(contribuinte, sys);
                                        break;
                                  
                                case 4: atribuiSetor(sys, contribuinte, contribuinte.getFaturasPendentes());
                                        break;
                                         
                                case 5: atribuiSetor(sys, contribuinte, contribuinte.getFaturas());
                                        break;
                                        
                                case 0: estado = 0;
                                        entidade_ativa = null;
                                        break;
                            }
                        } while(estado == 1);
                        break;
                case 2: Empresa empresa = (Empresa) entidade_ativa;
                        do{ // ESTADO = 2 -> Página da Empresa
                            System.out.println("BEM-VINDO(A) "+empresa.getNome()+"!\n");
                            menu.showOps(2);

                            switch (menu.getOp()){
                                
                                case 1: criaFatura(empresa, sys);
                                        break;

                                case 2 : printFaturas(empresa,0);
                                         break;

                                case 3 : printFaturas(empresa,1);
                                         break;

                                case 4 : consultaFaturasNIF(empresa,0);
                                         break;

                                case 5 : consultaFaturasNIF(empresa,1);
                                         break;

                                case 6 : printValorFaturado(empresa);
                                         break;

                                case 0: estado = 0;
                                        entidade_ativa = null;
                                        break;
                            }
                        } while(estado == 2);
                        break;
            }

        }

        try {
            sys.saveSystem("data");
        }
        catch (IOException e){
            System.out.println("Erro ao gravar dados");
        }
        /*
        try {
            menu.saveMenu("menu");
        }
        catch (IOException e){
            System.out.println("Erro ao gravar menu");
        }
        */
        System.out.println("Obrigado!");

        exit(0);
    }
}
