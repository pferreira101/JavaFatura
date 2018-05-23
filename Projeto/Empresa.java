import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.List;


public class Empresa extends Entidade implements Serializable {


    private ArrayList<Fatura> faturas_emitidas;
    ArrayList<String> setores;
    

    public boolean doInterior(){
        return getMorada().getDistrito().isInterior();
    }
    
    public double getBonusDeducao(){
        return getMorada().getDistrito().getTaxa();
    }

    public void addFatura(Fatura f){ 
        this.faturas_emitidas.add(f.clone());
    }

    public List<String> getSetores(){
        return setores.stream().collect(Collectors.toList());
    }

    // Requisitos Básicos

    public Set<Fatura> faturasEmitidasValor(){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> Double.compare(f1.getValor(), f2.getValor()));

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    public Set<Fatura> faturasEmitidasData(){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> f1.getData().compareTo(f2.getData()));

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    public Set<Fatura> faturasEmitidas(LocalDate inicio, LocalDate fim){
        TreeSet<Fatura> r = new TreeSet<>((f1, f2) -> f1.getData().compareTo(f2.getData()));

        this.faturas_emitidas.stream().filter(f -> f.getData().isAfter(inicio) && f.getData().isBefore(fim)).
                                       forEach(f -> r.add(f.clone()));

        return r;
    }


    public Set<Fatura> faturasFromNIF(int nif, LocalDate inicio, LocalDate fim){
        TreeSet<Fatura> r = new TreeSet<>((f1, f2) -> f1.getData().compareTo(f2.getData()));

        this.faturas_emitidas.stream().filter(f -> f.getNifCliente() == nif).
                                       filter(f -> f.getData().isAfter(inicio) && f.getData().isBefore(fim)).
                                       forEach(f -> r.add(f.clone()));

        return r;
    }

    public Set<Fatura> faturasFromNIF(int nif){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> Double.compare(f1.getValor(), f2.getValor()));

        this.faturas_emitidas.stream().filter(f -> f.getNifCliente() == nif).
                              forEach(f -> r.add(f.clone()));

        return r;
    }


    public double totalFaturado(){ // FIXME: 03/05/2018 que valor é que é faturado?
        return this.faturas_emitidas.stream().mapToDouble(Fatura::getValor).
                sum();
    }

    public double totalFaturado(LocalDate inicio, LocalDate fim){
        return this.faturas_emitidas.stream().filter(f -> f.getData().isAfter(inicio) && f.getData().isBefore(fim)).
                mapToDouble(Fatura::getValor).
                sum();
    }



    // Sorts
    // FIXME: 12/05/2018 isto era para a interface mas serão precisas agora?
    TreeSet<Fatura> sortBy(){
        TreeSet<Fatura> r = new TreeSet<Fatura>();

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    TreeSet<Fatura> sortBy(Comparator<Fatura> c){
        TreeSet<Fatura> r = new TreeSet<Fatura>(c);

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }


    // Getters & Setters

    public ArrayList<Fatura> getFaturasEmitidas(){
        return this.faturas_emitidas.stream().map(Fatura::clone).
                                              collect(Collectors.toCollection(ArrayList::new));

    }

    public void setFaturasEmitidas(ArrayList<Fatura> f){
        this.faturas_emitidas = f.stream().map(Fatura::clone).
                                           collect(Collectors.toCollection(ArrayList::new));

    }


    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;

        Empresa e = (Empresa)o;

        return super.equals(e) &&
               this.faturas_emitidas.equals(e.getFaturasEmitidas());

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
        this.faturas_emitidas = new ArrayList<>();
        this.setores = new ArrayList<>();
    }

    public Empresa(int nif, String email, String nome, Morada morada, String password, ArrayList<String> setores, ArrayList<Fatura> faturas_emitidas){
        super(nif, email, nome, morada, password);
        
        this.faturas_emitidas = faturas_emitidas.stream().map(Fatura::clone).
                                                          collect(Collectors.toCollection(ArrayList::new));
                                                          
        this.setores =   setores.stream().collect(Collectors.toCollection(ArrayList::new));                                                
    }

    public Empresa(Empresa outro){
        super(outro);
        this.faturas_emitidas = outro.getFaturasEmitidas();
    }
    
}



