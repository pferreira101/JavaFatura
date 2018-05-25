import java.io.*;
import java.util.Scanner;

public class Menu implements Serializable{
    
    private String[][] operacoes = { // Menu inicial
                                    { "1 - Login", 
                                      "2 - Registar Contribuinte", 
                                      "3 - Registar Empresa"},
                                     // Menu contribuinte 
                                    { "1 - Consultar Faturas", 
                                      "2 - Total Deduzido", 
                                      "3 - Deduções Agregado Familiar", 
                                      "4 - Atribuir Setor de Atividade Económica a uma Fatura",
                                      "5 - Alterar Setor de Atividade Económica de uma Fatura"},
                                     // Menu empresa 
                                    { "1 - Emitir Fatura", 
                                      "2 - Consultar Faturas (por data de emissão)",
                                      "3 - Consultar Faturas (por valor)",
                                      "4 - Consultar Faturas De Certo Contribuinte (entre determinada data)",
                                      "5 - Consultar Faturas De Certo Contribuinte (por valor)", 
                                      "6 - Total Faturado"}
                                    };
        
    public String[] getMainMenu(){
        return this.operacoes[0];
    }
    
    public String[] getMenuContribuinte(){
        return this.operacoes[1];
    }
    
    public String[] getMenuEmpresa(){
        return this.operacoes[2];
    }
    
    /*
    public void showOps(int menu){
        for(int i = 0; i < this.operacoes[menu].length; i++){
            System.out.println(this.operacoes[menu][i]);
        }
        if (menu==0)System.out.println("0 - Gravar e Sair");
        else System.out.println("0 - Logout");
    }
    */


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
