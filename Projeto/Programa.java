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
    
 



    
   
   

 

 
    // Metodo que permite atribuir sector a uma fatura
    // recebe como argumentos uma lista de faturas para poder ser aplicada tanto a pendentes como em casos de alteracao de setor

    
    
    
    
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

        


    // Metodo para fazer display do valor total faturado por uma empresa
    public static void printValorFaturado(Empresa e){
        System.out.println("Total Faturado: " + e.totalFaturado());
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
/*
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

