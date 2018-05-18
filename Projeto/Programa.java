import Setor.Setor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Programa implements Serializable {

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
        int cod_postal = sc.nextInt();
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
        nome = sc.next();

        System.out.println("E-mail: ");
        email = sc.next();

        System.out.println("Password: ");
        password = sc.next();

        System.out.println("Rua: ");
        String rua = sc.next();
        System.out.println("Código Postal: ");
        int cod_postal = sc.nextInt();
        // FIXME: 18/05/2018 Distrito

        morada = new Morada(rua, cod_postal, null);


        Empresa e = new Empresa(nif, email, nome, morada, password, setores, faturas_emitidas);

        sys.registaEntidade(e);

    }

    public static void main(String args[]){
        Sistema sys = new Sistema();
        Menu menu = new Menu();
        int estado = 0; // FIXME: 16/05/2018 um estado para cada tipo de entidade? contribuinte, empresa, admin = contribuinte + ...
        Entidade entidade_ativa;


        try {
            sys = Sistema.loadSystem("data");
        }
        catch (IOException e){
            System.out.println("Não conseguiu ler ficheiro de dados"); // FIXME: 18/05/2018
        }
        catch (ClassNotFoundException e){
            System.out.println("Não conseguiu ler ficheiro de dados 2"); // FIXME: 18/05/2018
        }

        try{
            menu = Menu.loadMenu("menu");
        }
        catch (IOException e){
            System.out.println("Não conseguiu ler ficheiro de menu"); // FIXME: 18/05/2018
        }
        catch (ClassNotFoundException e){
            System.out.println("Não conseguiu ler ficheiro de menu 2");
        }


        System.out.println("BEM-VINDO!");

        while(estado != -1){
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
                /*case 1: do{
                            menu.showOps(1);

                            switch (menu.getOp()){

                                case 0: estado = 0;
                                        entidade_ativa = null;
                                        break;
                            }
                        }while(estado == 1); */
            }

        }

        try {
            sys.saveSystem("data");
        }
        catch (IOException e){
            System.out.println("Erro ao gravar dados");
        }

        try {
            menu.saveMenu("menu");
        }
        catch (IOException e){
            System.out.println("Erro ao gravar menu");
        }

        System.out.println("Obrigado!");

        exit(0);
    }
}
