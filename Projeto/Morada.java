import java.io.Serializable;
import java.util.Objects;

public class Morada implements Serializable {

    private String rua;
    private int cod_postal;
    private Distrito distrito;

    public String getRua(){
        return this.rua;
    }

    public void setRua(String rua){
        this.rua = rua;
    }

    public int getCodPostal(){
        return this.cod_postal;
    }

    public void setCodPostal(int cod_postal){
        this.cod_postal = cod_postal;
    }

    public Distrito getDistrito(){
        return this.distrito;
    }

    public void setDistrito(Distrito distrito){
        this.distrito = distrito;
    }

    

    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Morada morada = (Morada) o;
        
        return this.rua.equals(morada.getRua()) &&
               this.cod_postal == morada.getCodPostal() &&
               this.distrito.equals(morada.getDistrito());
    }
    
    public Morada clone(){
        return new Morada(this);
    }
    
    public String toString(){ // FIXME: 12/05/2018 
        return "";
    }

    //Contrutores
    
    public Morada(){
        this.rua = "";
        this.cod_postal = -1;
        this.distrito = null; // FIXME: 12/05/2018 
    }

    public Morada(String rua, int cod_postal, Distrito distrito){
        this.rua = rua;
        this.cod_postal = cod_postal;
        this.distrito = distrito;
    }
    
    public Morada(Morada outra){
        this.rua = outra.getRua();
        this.cod_postal = outra.getCodPostal();
        this.distrito = outra.getDistrito();
    }
}
