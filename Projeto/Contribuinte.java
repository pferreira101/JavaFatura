import java.util.Arrays;
import java.time.LocalDate;

public class Contribuinte{
    
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;
    
    private int dep_familia;
    private int[] nif_familia;
    private double coe_fiscal;
    private boolean[] atividades_eco;
    
    private Fatura[] faturas;
    private int n_faturas;
    
    
    void VerificaFaturas(){
        Fatura[] faturas = this.getFaturas();
        int n_faturas = this.getNumFaturas();
        
        for(int i = 0; i < n_faturas; i++){
            if(faturas[i].getSetorAtivo() == ""){
                System.out.println("Fatura x");
                faturas[i].AtribuiSetor();
            }
        }
        
        this.setFaturas(faturas);
    }
    
    // Getters && Setters
    
    public int getNIF(){
        return this.nif;
    }
    
    public void setNIF(int nif){
        this.nif = nif;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getMorada(){
        return this.morada;
    }
    
    public void setMorada(String morada){
        this.morada = morada;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public int getDepFamilia(){
        return this.dep_familia;
    }
    
    public void setDepFamilia(int dep_familia){
        this.dep_familia = dep_familia;
    }
    
    public int[] getNIFFamilia(){
        int[] novo = Arrays.copyOf(this.nif_familia, this.nif_familia.length);
        
        return novo;
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
        boolean[] novo = Arrays.copyOf(this.atividades_eco, this.atividades_eco.length);
        
        return novo;
    }

    public void setAtividadesEco(boolean[] atividades_eco){
        this.atividades_eco = Arrays.copyOf(atividades_eco, atividades_eco.length);
    }
    
    public Fatura[] getFaturas(){
        Fatura[] nova = new Fatura[this.faturas.length];

        for (int i=0; i<this.n_faturas; i++){
            nova[i] = this.faturas[i].clone();
        }
        return nova;
    }
    
    public void setFaturas(Fatura[] f){

        for (int i=0; i<f.length; i++){
            if(f[i]!=null)
                this.faturas[i] = f[i].clone();
        }
        
    }
    
    public int getNumFaturas(){
        return this.n_faturas;
    }
    
    public void setNumFaturas(int n_faturas){
        this.n_faturas = n_faturas;
    }
    
    
    // Equals & Clone & toString
    
    public boolean equals(Object outro){
        if(this == outro) return true;
        if(outro == null || this.getClass() != outro.getClass()) return false;
        
        Contribuinte c = (Contribuinte) outro;
        
        return (this.nif == c.getNIF() && this.email.equals(c.getEmail()) && this.nome.equals(c.getNome()) &&
                this.morada.equals(c.getMorada()) && this.password.equals(c.getPassword()) && this.dep_familia == c.getDepFamilia() &&
                Arrays.equals(this.nif_familia, c.getNIFFamilia()) && this.coe_fiscal == c.getCoeficienteFiscal() && Arrays.equals(this.atividades_eco, c.getAtividadesEco()) &&
                Arrays.deepEquals(this.faturas, c.getFaturas()) && this.n_faturas == c.getNumFaturas());
    }
    
    public Contribuinte clone(){
        return new Contribuinte(this);
    }
    

    public String toString(){
        System.out.println("NIF: " + this.nif + "\nE-mail: " + this.email + "\nNome: " + this.nome + "\nMorada: " + this.morada);
        return ("NIF: " + this.nif + "\nE-mail: " + this.email + "\nNome: " + this.nome + "\nMorada: " + this.morada);
    }
    
    // Construtores
    
    public Contribuinte(){
        this.nif = -1;
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.password = "";
        this.dep_familia = 0;
        this.nif_familia = null;
        this.coe_fiscal = 0;
        this.atividades_eco = null;
        this.faturas = new Fatura[10];
        this.n_faturas = 0;
    }
    
    public Contribuinte(int nif, String email, String nome, String morada, String password, int dep_familia, int[] nif_familia, double coe_fiscal, boolean[] atividades_eco, Fatura[] faturas, int n_faturas){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
        this.dep_familia = dep_familia;
        this.nif_familia = Arrays.copyOf(nif_familia, nif_familia.length);
        this.coe_fiscal = coe_fiscal;
        this.atividades_eco = Arrays.copyOf(atividades_eco, atividades_eco.length);
        this.n_faturas = n_faturas;
        
        Fatura[] nova = new Fatura[faturas.length];
        
        for (int i=0; i<faturas.length; i++){
            nova[i] = faturas[i].clone();
        }
        
        this.faturas = nova;
    }
    
    public Contribuinte(Contribuinte outro){
        this.nif = outro.getNIF();
        this.email = outro.getEmail();
        this.nome = outro.getNome();
        this.morada = outro.getMorada();
        this.password = outro.getPassword();
        this.dep_familia = outro.getDepFamilia();
        this.nif_familia = outro.getNIFFamilia();
        this.coe_fiscal = outro.getCoeficienteFiscal();
        this.atividades_eco = outro.getAtividadesEco();
        this.faturas = outro.getFaturas();
        this.n_faturas = outro.getNumFaturas();
    }
}
