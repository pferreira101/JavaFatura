import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Contribuinte extends Entidade{
    
    private int dep_familia;
    private int[] nif_familia;
    private double coe_fiscal;
    private ArrayList<Setor> setores;
    
    private ArrayList<Fatura> faturas;


    TreeSet<Fatura> sortByDate(){
        TreeSet<Fatura> r = new TreeSet<Fatura>(new Comparator<Fatura>(){
                                                    public int compare(Fatura a, Fatura b){
                                                        return a.getData().compareTo(b.getData());
                                                    }
                                                });

        this.faturas.forEach(f -> r.add(f.clone()));

        return r;
    }

    TreeSet<Fatura> sortByPrice(){
        TreeSet<Fatura> r = new TreeSet<Fatura>(new Comparator<Fatura>(){
                                                    public int compare(Fatura a, Fatura b){
                                                        return (int)(a.valorAPagar() - b.valorAPagar()); // FIXME: 01/05/2018 17.50 - 17.25 = 0 double compare?
                                                    }
                                                });

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

    public ArrayList<Setor> getSetores(){
        return this.setores.stream().map(Setor::clone).
                                     collect(Collectors.toCollection(ArrayList::new));

    }

    public void setSetores(ArrayList<Setor> setores){
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<Fatura> getFaturas(){
        return this.faturas .stream().map(Fatura::clone).
                                      collect(Collectors.toCollection(ArrayList::new));

    }
    
    public void setFaturas(ArrayList<Fatura> f){
        this.faturas = f.stream().map(Fatura::clone).
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
               this.setores.equals(c.getSetores()) &&
               this.faturas.equals(c.getFaturas());
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
        this.setores = new ArrayList<>();
        this.faturas = new ArrayList<>();
    }
    
    public Contribuinte(int nif, String email, String nome, String morada, String password, int dep_familia, int[] nif_familia, double coe_fiscal,  ArrayList<Setor> setores, ArrayList<Fatura> faturas){
        super(nif, email, nome, morada, password);
        this.dep_familia = dep_familia;
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
        this.coe_fiscal = coe_fiscal;
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.dep_familia = outro.getDepFamilia();
        this.nif_familia = outro.getNIFFamilia();
        this.coe_fiscal = outro.getCoeficienteFiscal();
        this.setores = outro.getSetores();
        this.faturas = outro.getFaturas();
    }
}
