import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;
import java.util.stream.Collectors;

public class Contribuinte extends Entidade implements Serializable {
    
    private int[] nif_familia;
    private int num_dependentes;
    private double bonus_fam_num;
    private GestorSetor gestor_deducoes;
    private ArrayList<Fatura> faturas;
    private ArrayList<Fatura> faturas_pendentes;



    // FIXME: 20/05/2018 Contabilizar valor dedutivel ao adicionar fatura
    
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
        else
            this.gestor_deducoes.descontabilizaFatura(a_alterar, setor_antigo); // tem de ser retirado o valor atribuido ao antigo setor da fatura
        
        this.gestor_deducoes.contabilizaFatura(a_alterar);
    }
    
    double totalDeduzido(){ 
        return this.getSetores().stream().mapToDouble(s -> s.getMontDeduzido()).sum();
                                 
    }
    
    double totalDeduzido(String setor) { 
        double total=0;
        
        for(Setor s : this.getSetores())
            if(s.getClass().getSimpleName().equals(setor))
                total = s.getMontDeduzido();           
        
        return total;
    }
    
    TreeSet<Fatura> sortBy(){
        TreeSet<Fatura> r = new TreeSet<Fatura>();

        this.faturas.forEach(f -> r.add(f.clone()));

        return r;
    }

    TreeSet<Fatura> sortBy(Comparator<Fatura> c){
        TreeSet<Fatura> r = new TreeSet<Fatura>(c);

        this.faturas.forEach(f -> r.add(f.clone()));

        return r;
    }

    
    // Getters & Setters
    
    public int getNumDependentes(){
        return this.num_dependentes;
    }
    
    public void setNumDependentes(int num_dependentes){
        this.num_dependentes = num_dependentes;
    }
    
    public int[] getNIFFamilia(){
        return Arrays.copyOf(this.nif_familia, this.nif_familia.length);
    }
    
    public void setNIFFamilia(int[] nif_familia){
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
    }

    public double getBonusDeducao(){
        return this.bonus_fam_num;
    }

    public void setBonusDeducao(double bonus){
        this.bonus_fam_num = bonus;
    }

    public ArrayList<Fatura> getFaturas(){
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
    
    public List<Setor> getSetores(){
        return this.gestor_deducoes.getSetores().stream().map(Setor::clone).
                                          collect(Collectors.toCollection(ArrayList::new));        
    }
    
    public GestorSetor getGestorSetor(){
        return this.gestor_deducoes.clone();
    }
    
    // Equals & Clone & toString

    public boolean equals(Object outro){
        if(this == outro) return true;
        if(outro == null || this.getClass() != outro.getClass()) return false;

        Contribuinte c = (Contribuinte) outro;

        return super.equals(c) &&
               this.num_dependentes == c.getNumDependentes() &&
               Arrays.equals(this.nif_familia, c.getNIFFamilia()) &&
               this.bonus_fam_num == c.getBonusDeducao() &&
               this.faturas.equals(c.getFaturas()) &&
               this.faturas_pendentes.equals(c.getFaturasPendentes());
    }


    public Contribuinte clone(){
        return new Contribuinte(this);
    }
    

    public String toString(){
        return ""; // FIXME: 01/05/2018
    }
    
    // Construtores
    
    public Contribuinte(){
        super();
        this.num_dependentes = 0;
        this.nif_familia = null;
        this.bonus_fam_num = 0;
        this.faturas = new ArrayList<>();
        this.faturas_pendentes = new ArrayList<>();
    }
    
    public Contribuinte(int nif, String email, String nome, Morada morada, String password, int num_dependentes, int[] nif_familia, double bonus, GestorSetor ges_deducoes, ArrayList<Fatura> faturas, ArrayList<Fatura> faturas_pendentes){
        super(nif, email, nome, morada, password);
        this.num_dependentes = num_dependentes;
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
        this.bonus_fam_num = bonus;
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
        this.faturas_pendentes = faturas.stream().map(Fatura::clone).
                                                  collect(Collectors.toCollection(ArrayList::new));
        this.gestor_deducoes = ges_deducoes.clone();                                          
    }
    
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.num_dependentes = outro.getNumDependentes();
        this.nif_familia = outro.getNIFFamilia();
        this.bonus_fam_num = outro.getBonusDeducao();
        this.faturas = outro.getFaturas();
        this.faturas_pendentes = outro.getFaturasPendentes();
        this.gestor_deducoes = outro.getGestorSetor();
    }
}
