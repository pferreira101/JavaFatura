import Setor.Setor;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

public class Programa {

    public static Entidade loginEntidade(Sistema sys){
        Scanner sc = new Scanner(System.in);
        int nif;
        String pw;

        do{
            System.out.println("NIF: ");
            nif = sc.nextInt();

            System.out.println("Password: ");
            pw = sc.next();

        }while(!sys.logIn(nif, pw));

        return sys.getEntidade(nif);
    }

    public static void registaContribuinte(Sistema sys){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        int nif, dep_familia;
        int[] nif_familia;
        double coe_fiscal;
        String email, nome, password;
        Morada morada;
        ArrayList<Setor> setores; // FIXME: 16/05/2018 como vamos criar novos setores ao ler uma string do scanner?
        ArrayList<Fatura> faturas = new ArrayList(), faturas_pendentes = new ArrayList();

        System.out.println("NIF: ");
        nif = sc.nextInt();

        System.out.println("Nome: ");
        nome = sc.next();

        System.out.println("E-mail: ");
        email = sc.next();

        System.out.println("Password: ");
        password = sc.next();

        System.out.println("");

        // FIXME: 16/05/2018 fazer o resto


        Contribuinte c = new Contribuinte(nif, email, nome, morada, password, setores, dep_familia, nif_familia, coe_fiscal, faturas, faturas_pendentes);

        sys.registaEntidade(c);

    }

    public static void registaEmpresa(Sistema sys){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        int nif;
        String email, nome, password;
        Morada morada;
        ArrayList<Setor> setores; // FIXME: 16/05/2018 como vamos criar novos setores ao ler uma string do scanner?
        ArrayList<Fatura> faturas_emitidas = new ArrayList();

        System.out.println("NIF: ");
        nif = sc.nextInt();

        System.out.println("Nome: ");
        nome = sc.next();

        System.out.println("E-mail: ");
        email = sc.next();

        System.out.println("Password: ");
        password = sc.next();

        System.out.println("");

        // FIXME: 16/05/2018 fazer o resto


        Empresa e = new Empresa(nif, email, nome, morada, password, setores, faturas_emitidas);

        sys.registaEntidade(e);

    }

    public static void main(String args[]){
        Sistema sys = Sistema.loadSystem("data");
        Menu menu = Menu.loadMenu("menu");
        int estado = 0; // FIXME: 16/05/2018 um estado para cada tipo de entidade? contribuinte, empresa, admin = contribuinte + ...
        Entidade entidade_ativa;


        System.out.println("BEM-VINDO!");
        
        do{
            switch(estado){
                case 0: do{ // ESTADO = 0 -> LOGIN PAGE
                            menu.showOps(0);

                            switch (menu.getOp()){
                                case 1: entidade_ativa = loginEntidade(sys);
                                        estado = 1; // FIXME: 16/05/2018 determinar que tipo de entidade fez login
                                        break;

                                case 2: registaContribuinte(sys);
                                        break;

                                case 3: registaEmpresa(sys);
                                        break;

                                case 0: estado = -1;
                                        break;
                            }
                        }while(estado == 0);

                case 1: do{ // ESTADO = 1 -> MAIN ENTITY MENU
                            menu.showOps(1);

                            switch (menu.getOp()){

                                case 0: estado = 0;
                                        break;
                            }

                        }while(estado == 1);
            }



        }while(estado != -1);



    }
}
