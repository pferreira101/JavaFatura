import java.util.ArrayList;
import java.util.stream.Collectors;

public class Empresa extends Entidade{

    private ArrayList<Setor> setores;
    // FIXME: 01/05/2018 Adicionar faturas emitidas


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
        return this.setores.stream().map(Setor::clone).
                                     collect(Collectors.toCollection(ArrayList::new));
    }

    public void setSetores(ArrayList<Setor> setores) {
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }


    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;

        Empresa e = (Empresa)o;

        return super.equals(e) &&
               this.setores.equals(e.getSetores());

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
        this.setores = new ArrayList<>();
    }

    public Empresa(int nif, String email, String nome, String morada, String password, ArrayList<Setor> setores){
        super(nif, email, nome, morada, password);
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }

    public Empresa(Empresa outro){
        super(outro);
        this.setores = outro.getSetores();
    }
    
}



