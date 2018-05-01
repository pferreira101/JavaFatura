import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Empresa extends Entidade{

    private ArrayList<Setor> setores;
    
     /**
     * Função que adiciona uma nova fatura ao Contribuinte
     * @param f Fatura a adicionar
     * @param c Contribuinte
     */
    void addFatura(Fatura f, Contribuinte c){
        ArrayList<Fatura> nova = c.getFaturas();

        nova.add(f);

        c.setFaturas(nova);
    }
    
    
    // Getters & Setters

    public ArrayList<Setor> getSetores() {
        return setores; // FIXME: 01/05/2018 
    }

    public void setSetores(ArrayList<Setor> setores) {
        this.setores = setores; // FIXME: 01/05/2018 
    }


    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;
        Empresa outro = (Empresa)o;

        return super.equals(outro); // FIXME: 01/05/2018 Falta setores

    }
    
    public Empresa clone(){
        return new Empresa(this);
    }
    
    public String toString(){
        return ""; // FIXME: 01/05/2018 
    }


    // Construtores

    public Empresa(){
        super();
        this.setores = null;
    }

    public Empresa(int nif, String email, String nome, String morada, String password, ArrayList<Setor> setores){
        super(nif, email, nome, morada, password);
        this.setores = setores; // FIXME: 01/05/2018 
    }

    public Empresa(Empresa outro){
        super(outro);
        this.setores = outro.getSetores();
    }
    
}



