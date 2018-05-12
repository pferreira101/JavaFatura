import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Sistema {

    private HashMap<Integer, Entidade> entidades = new HashMap<>(); // FIXME: 01/05/2018
    private String admin; // FIXME: 01/05/2018
    private String admin_password; // FIXME: 01/05/2018
    private boolean admin_mode;

    public boolean registaEntidade(Entidade e) {
        Entidade nova = this.entidades.putIfAbsent(e.getNif(), e.clone());
        return (nova == null);
    }

    public boolean entidadeLogIn(int nif, String password) {
        Entidade entidade = this.entidades.get(nif);
        if (entidade == null) return false;

        if(entidade.getPassword().equals(password)){

            return true;
        }
        return false;
    }

    public void adminLogIn(String nome, String password) {
        if (this.admin.equals(nome) && this.admin_password.equals(password)) {
            setAdminMode(true);
        }
    }

    public void setAdminMode(boolean mode) {
        this.admin_mode = mode;
    }

    public boolean getAdminMode() {
        return this.admin_mode;
    }



    // ADMIN ONLY

    public Set<Contribuinte> top10Contribuintes(){
        TreeSet<Contribuinte> r = new TreeSet<>((c1, c2) -> c1.totalDeduzido().compareTo(c2.totalDeduzido()));

        this.entidades.values().stream().filter(e -> e instanceof Contribuinte).
                                         map(e -> { Contribuinte c = (Contribuinte) (e.clone());
                                                    return c;
                                                  }).
                                         forEach(c -> r.add(c));


        return r; // FIXME: 12/05/2018 restringir os 10 e a ser so admin
    }

    public Set<Empresa> topXEmpresas(int x){
        TreeSet<Empresa> r = new TreeSet<>((e1, e2) -> e1.totalDeduzido().compareTo(e2.totalDeduzido())); // FIXME: 12/05/2018 totalFaturado? total de faturas?

        this.entidades.values().stream().filter(e -> e instanceof Empresa).
                                         map(e -> { Empresa em = (Empresa) (e.clone());
                                                    return em;
                                                  }).
                                         forEach(em -> r.add(em));

        return r; // FIXME: 12/05/2018 restringir a x e a ser so admin
    }


    // I/O

    public void guardaEstado(String nome_ficheiro) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(nome_ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    public static Sistema carregaEstado(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nome_ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Sistema sys = (Sistema) ois.readObject();

        ois.close();

        return sys;
    }

}
