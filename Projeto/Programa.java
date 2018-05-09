import java.io.Console;
import java.util.Scanner;

public class Programa {

    private static Sistema sys;
    private static Entidade entidade_ativa;

    public static void setEntidadeAtiva(Entidade entidade){
        entidade_ativa = entidade.clone();
    }

    public static Entidade getEntidadeAtiva() {
        return entidade_ativa.clone();
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        boolean login = false;
        System.out.println("Bem-vindo ao JAVA-FATURA \nPor favor, efetue o login");

        while(!login){
            System.out.println("NIF: ");
            int nif = sc.nextInt();

            System.out.println("Password: ");
            String password = sc.next(); // FIXME: 09/05/2018 nao pode ser visivel
            
            login = sys.entidadeLogIn(nif, password);
            if(!login) System.out.println("NIF e/ou Password incorretos.\nTente novamente.");
        }
        // entidade_ativa = setEntidadeAtiva(sys.getEntidades().get(nif));
        System.out.println("Olá, " + entidade_ativa.getNome());
        System.out.println("Selecione o nº do que deseja realizar: ");
        System.out.println("Vária ops..."); // FIXME: 09/05/2018
        int op = sc.nextInt();
        
        switch (op){
            case 1: System.out.printf("ok");
        }


    }
}
