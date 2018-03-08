public class Empresa{
    
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;
    private boolean[] setores; //falta saber quantos setores existem, getters and setters
    private double fator;
   
    
     /**
     * Função que adiciona uma nova fatura ao Contribuinte
     * @param f Fatura a adicionar
     */
    void addFatura(Fatura f, Contribuinte c){
        Fatura[] faturas = c.getFaturas();
        int n_faturas = c.getNumFaturas();
        
        if(n_faturas < faturas.length){
            faturas[n_faturas] = f.clone();
        }
        
        c.setFaturas(faturas);
        c.setNumFaturas(n_faturas+1);
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
    
    public double getFator(){
        return this.fator;
    }
    
    public void setFator(double fator){
        this.fator = fator;
    }
    
    
    
    
    // Construtores
    
    public Empresa(){
        this.nif = -1;
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.password = "";
        //this.setores[]...
        this.fator = 0;
    }
    
    public Empresa(int nif, String email, String nome, String morada, String password, double fator){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
        //this.setores[]...
        this.fator = fator;
    }
    
    public Empresa(Empresa outro){
        this.nif = outro.getNIF();
        this.email = outro.getEmail();
        this.nome = outro.getNome();
        this.morada = outro.getMorada();
        this.password = outro.getPassword();
        //this.setores[]...
        this.fator = outro.getFator();
    }
    
    // Equals and clone
    
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;
        Empresa outro = (Empresa)o;
        return 
            this.nif == outro.getNIF() &&
            this.email == outro.getEmail() &&
            this.nome == outro.getNome() &&
            this.morada == outro.getMorada() &&
            this.password == outro.getPassword() &&
            this.fator == outro.getFator();
            //this.setores....
    }
    
    public Empresa clone(){
        return new Empresa(this);
    }
    
    public String toString(){
        return "Nif: " + this.nif +"\nNome: "+this.nome+"\nEmail: "+this.email+"\nMorada: "+this.morada; //...ver o resto                            
                          
                 
    }
    
}



