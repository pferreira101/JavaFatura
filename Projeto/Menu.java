import java.io.*;
import java.util.Scanner;

public class Menu implements Serializable{

    private int op;
    private String[][] operacoes = {{"1 - Login", "2 - Registar Entidade", "3 - Registar Empresa"},
                                    {"1 - Consultar Faturas", "2 - Total Deduzido", "3 - Deduções Agregado Familiar"},
                                    {"1 - Emitir Fatura", "2 - Consultar Faturas (por data de emissão)", "3 - Consultar Faturas (por valor)", "4 - Total Faturado"}};



    public void showOps(int menu){
        for(int i = 0; i < this.operacoes[menu].length; i++){
            System.out.println(this.operacoes[menu][i]);
        }
        System.out.println("0 - Retroceder");
    }

    public int getOp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Operação: ");
        return sc.nextInt();
    }


    // I/O

    public void saveMenu(String nome_ficheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nome_ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    public static Menu loadMenu(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nome_ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Menu menu = (Menu) ois.readObject();

        ois.close();

        return menu;
    }

}
