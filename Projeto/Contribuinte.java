import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.Collectors;

public class Contribuinte extends Entidade{
    
    private int dep_familia;
    private int[] nif_familia;
    private double coe_fiscal;
    private boolean[] atividades_eco; // FIXME: 01/05/2018 ArrayList<Setor>
    
    private ArrayList<Fatura> faturas;

    

    
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

    public boolean[] getAtividadesEco(){
        return Arrays.copyOf(this.atividades_eco, this.atividades_eco.length);
    }

    public void setAtividadesEco(boolean[] atividades_eco){
        this.atividades_eco = Arrays.copyOf(atividades_eco, atividades_eco.length);
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
               Arrays.equals(this.atividades_eco, c.getAtividadesEco()) ;
               // FIXME: 01/05/2018 Falta Faturas
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
        this.atividades_eco = null;
        this.faturas = new ArrayList<>();
    }
    
    public Contribuinte(int nif, String email, String nome, String morada, String password, int dep_familia, int[] nif_familia, double coe_fiscal, boolean[] atividades_eco, ArrayList<Fatura> faturas){
        super(nif, email, nome, morada, password);
        this.dep_familia = dep_familia;
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
        this.coe_fiscal = coe_fiscal;
        this.atividades_eco = Arrays.copyOf(atividades_eco, atividades_eco.length);
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.dep_familia = outro.getDepFamilia();
        this.nif_familia = outro.getNIFFamilia();
        this.coe_fiscal = outro.getCoeficienteFiscal();
        this.atividades_eco = outro.getAtividadesEco();
        this.faturas = outro.getFaturas();
    }
}
