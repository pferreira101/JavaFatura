import java.io.*;

public class Menu {

    private int op;
    private String[][] operacoes = {{"1 - Login", "2 - Registar Entidade", "3 - Registar Empresa"},
                                    {"1 - Total deduzido", "2 - Total deduzido por setor", "3 - "}};




    public void showOps(int menu){
        for(int i = 0; i < this.operacoes[menu].length; i++){
            System.out.println(this.operacoes[menu][i]);
        }
        System.out.println("0 - Retroceder");
    }

    public int getOp(){
        return this.op;
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
