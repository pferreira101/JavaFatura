import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

public class Contribuinte extends Entidade implements Serializable {
    
    private List<Integer> nif_familia;
    private int num_dependentes;
    private List<Fatura> faturas;
    private List<Fatura> faturas_pendentes;

    
    public void addFatura(Fatura f){
        
        if(f.hasSetor()){ 
            faturas.add(f.clone());
        }
        else{
            faturas_pendentes.add(f.clone());
        }
    }
    
    public void alteraSetorFatura(Fatura f, String novo_setor){
        Fatura a_alterar;
        String setor_antigo;
        int pos = 0;
        boolean fstSetor = !f.hasSetor();
        
        if(fstSetor){
            for(Fatura fat : this.faturas_pendentes){
                if(fat.equals(f))
                    break;          
                    pos++;
            }
            
            a_alterar = faturas_pendentes.get(pos);
        }
        else{
            for(Fatura fat : this.faturas){
                if(fat.equals(f))
                    break;          
                    pos++;
            }
            
            a_alterar = faturas.get(pos);
        }
        
        setor_antigo = a_alterar.getSetor();
        
        a_alterar.mudaSetor(novo_setor);
        
        if(fstSetor){
            this.faturas.add(a_alterar);
            this.faturas_pendentes.remove(pos);
        }
    }

    public double valorTotalFaturas() {
        double res = 0;
        for (Fatura f: faturas) {
            res += f.getValor();
        }
        for (Fatura f: faturas_pendentes) {
            res += f.getValor();
        }
        return res;
    }
    

    
    // Getters & Setters
    
    public int getNumDependentes(){
        return this.num_dependentes;
    }
    
    public void setNumDependentes(int num_dependentes){
        this.num_dependentes = num_dependentes;
    }
    
    public List<Integer> getNIFFamilia(){
        return new ArrayList(this.nif_familia);
    }
    
    public void setNIFFamilia(List<Integer> nif_familia){
        nif_familia.stream().forEach(nif -> this.nif_familia.add(nif));
    }

    public List<Fatura> getFaturas(){
        return this.faturas .stream().map(Fatura::clone).
                                      collect(Collectors.toCollection(ArrayList::new));

    }
    
    public void setFaturas(ArrayList<Fatura> f){
        this.faturas = f.stream().map(Fatura::clone).
                                  collect(Collectors.toCollection(ArrayList::new));
        
    }

    public ArrayList<Fatura> getFaturasPendentes(){
        return this.faturas_pendentes.stream().map(Fatura::clone).
                                               collect(Collectors.toCollection(ArrayList::new));

    }

    public void setFaturasPendentes(ArrayList<Fatura> f){
        this.faturas_pendentes = f.stream().map(Fatura::clone).
                                            collect(Collectors.toCollection(ArrayList::new));

    }
    
    
    // Equals & Clone & toString

    public boolean equals(Object outro){
        if(this == outro) return true;
        if(outro == null || this.getClass() != outro.getClass()) return false;

        Contribuinte c = (Contribuinte) outro;

        return super.equals(c) &&
               this.num_dependentes == c.getNumDependentes() &&
               this.nif_familia.equals(c.getNIFFamilia()) &&
               this.faturas.equals(c.getFaturas()) &&
               this.faturas_pendentes.equals(c.getFaturasPendentes());
    }


    public Contribuinte clone(){
        return new Contribuinte(this);
    }
    

    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("NIF: "); s.append(getNif()); s.append('\n');
        s.append("Nome: "); s.append(getNome()); s.append('\n');
        s.append("Email: "); s.append(getEmail()); s.append('\n');
        s.append("Morada:\n"); s.append(getMorada().toString()); s.append('\n');
        return s.toString();
    }
    
    // Construtores
    
    public Contribuinte(){
        super();
        this.num_dependentes = 0;
        this.nif_familia = null;
        this.faturas = new ArrayList<>();
        this.faturas_pendentes = new ArrayList<>();
    }
    
    public Contribuinte(int nif, String email, String nome, Morada morada, String password, int num_dependentes, List<Integer> nif_familia, List<Fatura> faturas, List<Fatura> faturas_pendentes){
        super(nif, email, nome, morada, password);
        this.num_dependentes = num_dependentes;
        this.nif_familia = nif_familia.stream().collect(Collectors.toCollection(ArrayList::new));
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
        this.faturas_pendentes = faturas.stream().map(Fatura::clone).
                                                  collect(Collectors.toCollection(ArrayList::new));                                       
    }
    
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.num_dependentes = outro.getNumDependentes();
        this.nif_familia = outro.getNIFFamilia();
        this.faturas = outro.getFaturas();
        this.faturas_pendentes = outro.getFaturasPendentes();
    }
}
