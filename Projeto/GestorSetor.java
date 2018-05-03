import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class GestorSetor {
    
    private ArrayList<Setor> setores;
    private Setor setor_ativo;
    private ArrayList<LogSetor> log;

    
    
    // Getters & Setters
    
    public ArrayList<Setor> getSetores(){
        return this.setores.stream().map(Setor::clone).
                                     collect(Collectors.toCollection(ArrayList::new));
    }

    public void setSetores(ArrayList<Setor> setores){
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }

    public Setor getSetorAtivo(){
        return this.setor_ativo.clone();
    }

    public void setSetorAtivo(Setor setor_ativo){
        this.setor_ativo = setor_ativo.clone();
    }

    public ArrayList<LogSetor> getLog(){
        return this.log.stream().map(LogSetor::clone).
                                 collect(Collectors.toCollection(ArrayList::new));
    }

    public void setLog(ArrayList<LogSetor> log){
        this.log = log.stream().map(LogSetor::clone).
                                collect(Collectors.toCollection(ArrayList::new));
    }

    
    // Equals & Clone & toString
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        GestorSetor gs = (GestorSetor) o;
        
        return this.setores.equals(gs.getSetores()) &&
               this.setor_ativo.equals(gs.getSetorAtivo()) &&
               this.log.equals(gs.getLog()); 
    }
    
    public GestorSetor clone(){
        return new GestorSetor(this);
    }
    
    public String toString(){
        return ""; // FIXME: 03/05/2018 
    }
    
    
    // Construtores
    
    public GestorSetor(){
        this.setores = new ArrayList<>();
        this.setor_ativo = null;
        this.log = new ArrayList<>();
    }

    public GestorSetor(ArrayList<Setor> setores, Setor setor_ativo, ArrayList<LogSetor> log) {
        this.setores = setores.stream().map(Setor::clone).collect(Collectors.toCollection(ArrayList::new));
        this.setor_ativo = setor_ativo.clone();
        this.log = log.stream().map(LogSetor::clone).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public GestorSetor(GestorSetor outro){
        this.setores = outro.getSetores();
        this.setor_ativo = outro.getSetorAtivo();
        this.log = outro.getLog();
    }
}
