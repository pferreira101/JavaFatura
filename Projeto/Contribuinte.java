import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import Setor.*;

public class Contribuinte extends Entidade implements Serializable {
    
    private int dep_familia;
    private int[] nif_familia;
    private double coe_fiscal;
    private ArrayList<Fatura> faturas;
    private ArrayList<Fatura> faturas_pendentes;



    // FIXME: 20/05/2018 Contabilizar valor dedutivel ao adicionar fatura
    public void addFatura(Fatura f){
        
        if(f.getGestorSetor().getSetores().size() == 1){ 
            faturas.add(f.clone());
        }
        else{
            faturas_pendentes.add(f.clone());
        }
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
    
    public int getDepFamilia(){
        return this.dep_familia;
    }
    
    public void setDepFamilia(int dep_familia){
        this.dep_familia = dep_familia;
    }
    
    public int[] getNIFFamilia(){
        return Arrays.copyOf(this.nif_familia, this.nif_familia.length);
    }
    
    public void setNIFFamilia(int[] nif_familia){
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
    }

    public double getCoeficienteFiscal(){
        return this.coe_fiscal;
    }

    public void setCoeficienteFiscal(double coe_fiscal){
        this.coe_fiscal = coe_fiscal;
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
    

    
    
    // Equals & Clone & toString

    public boolean equals(Object outro){
        if(this == outro) return true;
        if(outro == null || this.getClass() != outro.getClass()) return false;

        Contribuinte c = (Contribuinte) outro;

        return super.equals(c) &&
               this.dep_familia == c.getDepFamilia() &&
               Arrays.equals(this.nif_familia, c.getNIFFamilia()) &&
               this.coe_fiscal == c.getCoeficienteFiscal() &&
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
        this.dep_familia = 0;
        this.nif_familia = null;
        this.coe_fiscal = 0;
        this.faturas = new ArrayList<>();
        this.faturas_pendentes = new ArrayList<>();
    }
    
    public Contribuinte(int nif, String email, String nome, Morada morada, String password, ArrayList<Setor> setores, int dep_familia, int[] nif_familia, double coe_fiscal, ArrayList<Fatura> faturas, ArrayList<Fatura> faturas_pendentes){
        super(nif, email, nome, morada, password, setores);
        this.dep_familia = dep_familia;
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
        this.coe_fiscal = coe_fiscal;
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
        this.faturas_pendentes = faturas.stream().map(Fatura::clone).
                                                  collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.dep_familia = outro.getDepFamilia();
        this.nif_familia = outro.getNIFFamilia();
        this.coe_fiscal = outro.getCoeficienteFiscal();
        this.faturas = outro.getFaturas();
        this.faturas_pendentes = outro.getFaturasPendentes();
    }
}
