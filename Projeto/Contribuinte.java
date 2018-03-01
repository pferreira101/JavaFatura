
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
    
    // Equals & Clone & toString
    
    public boolean equals(Object outro){
        if(this == outro) return true;
        if(this.getClass() != outro.getClass()) return false;
        
        Contribuinte c = (Contribuinte) outro;
        
        if(this.nif == c.getNIF() && this.email.equals(c.getEmail()) && this.nome.equals(c.getNome()) &&
           this.morada.equals(c.getMorada()) && this.password.equals(c.getPassword())) return true;
           
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
    
    public Contribuinte(int nif, String email, String nome, String morada, String password){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
    }
    
    public Contribuinte(Contribuinte outro){
        this.nif = outro.getNIF();
        this.email = outro.getEmail();
        this.nome = outro.getNome();
        this.morada = outro.getMorada();
        this.password = outro.getPassword();
    }
}
