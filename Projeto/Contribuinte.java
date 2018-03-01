import java.util.Arrays;

public class Contribuinte{
    
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;
    
    private int dep_familia;
    private int fiscais_familia;
    private double coe_fiscal;
    private int[] atividades_eco;
    
    
    
    
    
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
    
    /* public void setNome(String nome){
    /*    this.nome = nome;
    } */
    
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
    
    public int getFiscaisFamilia(){
        return this.fiscais_familia;
    }
    
    public void setFiscaisFamilia(int fiscais_familia){
        this.fiscais_familia = fiscais_familia;
    }

    public double getCoeficienteFiscal(){
    	return this.coe_fiscal;
    }

    public void setCoeficienteFiscal(double coe_fiscal){
    	this.coe_fiscal = coe_fiscal;
    }

    public int[] getAtividadesEco(){
    	return this.atividades_eco;
    }

    public void setAtividades(int[] atividades_eco){
    	this.atividades_eco = atividades_eco;
    }
    
    // Equals & Clone & toString
    
    public boolean equals(Object outro){
        if(this == outro) return true;
        if(this.getClass() != outro.getClass()) return false;
        
        Contribuinte c = (Contribuinte) outro;
        
        if(this.nif == c.getNIF() && this.email.equals(c.getEmail()) && this.nome.equals(c.getNome()) &&
           this.morada.equals(c.getMorada()) && this.password.equals(c.getPassword()) && this.dep_familia == c.getDepFamilia() &&
           this.fiscais_familia == c.getFiscaisFamilia() && this.coe_fiscal == c.getCoeficienteFiscal() && Arrays.equals(this.atividades_eco, c.getAtividadesEco())) return true;
           
        return false;
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
    }
    
    public Contribuinte(int nif, String email, String nome, String morada, String password, int dep_familia, int fiscais_familia, double coe_fiscal, int[] atividades_eco){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
        this.dep_familia = dep_familia;
        this.fiscais_familia = fiscais_familia;
        this.coe_fiscal = coe_fiscal;
        this.atividades_eco = atividades_eco;
    }
    
    public Contribuinte(Contribuinte outro){
        this.nif = outro.getNIF();
        this.email = outro.getEmail();
        this.nome = outro.getNome();
        this.morada = outro.getMorada();
        this.password = outro.getPassword();
        this.dep_familia = outro.getDepFamilia();
        this.fiscais_familia = outro.getFiscaisFamilia();
        this.coe_fiscal = outro.getCoeficienteFiscal();
        this.atividades_eco = outro.getAtividadesEco();
    }
}
