import Setor.Setor;
import Setor.GestorSetor;
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
        int nif, dep_familia = 0;
        int[] nif_familia = {};
        double coe_fiscal = 0;
        String email, nome, password;
        Morada morada;
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
        System.out.println("Código Postal: ");
        String cod_postal = sc.next();
        // FIXME: 18/05/2018 Distrito

        morada = new Morada(rua, cod_postal, null);

        // FIXME: 16/05/2018 fazer o resto


        Contribuinte c = new Contribuinte(nif, email, nome, morada, password, setores, dep_familia, nif_familia, coe_fiscal, faturas, faturas_pendentes);

        sys.registaEntidade(c);

    }

    public static void registaEmpresa(Sistema sys){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        int nif;
        String email, nome, password;
        Morada morada;
        ArrayList<Setor> setores = new ArrayList<Setor>(); // FIXME: 16/05/2018 como vamos criar novos setores ao ler uma string do scanner?
        ArrayList<Fatura> faturas_emitidas = new ArrayList<Fatura>();

        System.out.println("NIF: ");
        nif = sc.nextInt();

        System.out.println("Nome: ");
        nome = sc.nextLine();

        System.out.println("E-mail: ");
        email = sc.nextLine();

        System.out.println("Password: ");
        password = sc.nextLine();

        System.out.println("Rua: ");
        String rua = sc.nextLine();
        System.out.println("Código Postal: ");
        String cod_postal = sc.nextLine();
        // FIXME: 18/05/2018 Distrito

        morada = new Morada(rua, cod_postal, null);


        Empresa e = new Empresa(nif, email, nome, morada, password, setores, faturas_emitidas);

        sys.registaEntidade(e);

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
        
        Fatura f = new Fatura(emp.getNome(), emp.getNif(), data, nif_cliente, descricao, new GestorSetor(), valor, 0);

        try{
            sys.addFaturaSistema(f);
        }
        catch(NIFNaoRegistadoException e){
            System.out.println(e.getMessage());
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
        
        if(c.getNIFFamilia().length == 0)
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
                case 1: do{ // ESTADO = 1 -> Página do Contribuinte
                            System.out.println("BEM-VINDO(A) "+((Contribuinte)entidade_ativa).getNome()+"!\n");
                            menu.showOps(1);

                            switch (menu.getOp()){
                                
                                case 1: printFaturas((Contribuinte)entidade_ativa); // tirar o cast depois de resolver tudo
                                        break;
                                        
                                case 2: printDeducoes((Contribuinte)entidade_ativa);
                                        break;
                                        
                                case 3: printDeducoesFamilia((Contribuinte)entidade_ativa, sys);
                                        break;
                                        
                                case 0: estado = 0;
                                        entidade_ativa = null;
                                        break;
                            }
                        } while(estado == 1);
                        break;
                case 2: do{ // ESTADO = 2 -> Página da Empresa
                            System.out.println("BEM-VINDO(A) "+((Empresa)entidade_ativa).getNome()+"!\n");
                            menu.showOps(2);

                            switch (menu.getOp()){
                                
                                case 1: criaFatura((Empresa) entidade_ativa, sys);
                                        break;

                                case 2 : printFaturas((Empresa) entidade_ativa,0);
                                         break;

                                case 3 : printFaturas((Empresa) entidade_ativa,1);
                                         break;

                                case 4 : consultaFaturasNIF((Empresa)entidade_ativa,0);
                                         break;

                                case 5 : consultaFaturasNIF((Empresa)entidade_ativa,1);
                                         break;

                                case 6 : printValorFaturado((Empresa)entidade_ativa);
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
