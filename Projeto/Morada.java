import java.io.Serializable;
import java.util.Objects;

public class Morada implements Serializable {

    private String rua;
    private String cod_postal;
    private String concelho;
    private Distritos distrito;

    public String getRua(){
        return this.rua;
    }

    public void setRua(String rua){
        this.rua = rua;
    }

    public String getCodPostal(){
        return this.cod_postal;
    }

    public void setCodPostal(String cod_postal){
        this.cod_postal = cod_postal;
    }
    
    public String getConcelho(){
        return this.concelho;
    }
    
    public void setConcelho(String concelho){
        this.concelho = concelho;
    }
    
    public Distritos getDistrito(){
        return this.distrito;
    }

    public void setDistrito(Distritos distrito){
        this.distrito = distrito;
    }

    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Morada morada = (Morada) o;
        
        return this.rua.equals(morada.getRua()) &&
               this.cod_postal.equals(morada.getCodPostal()) &&
               this.concelho.equals(morada.getConcelho()) &&
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
        this.cod_postal = "";
        this.concelho = "";
        this.distrito = null; // FIXME: 12/05/2018 
    }

    public Morada(String rua, String cod_postal, String concelho, Distritos distrito){
        this.rua = rua;
        this.cod_postal = cod_postal;
        this.concelho = concelho;
        this.distrito = distrito;
    }

    public Morada(Morada outra){
        this.rua = outra.getRua();
        this.cod_postal = outra.getCodPostal();
        this.concelho = outra.getConcelho();
        this.distrito = outra.getDistrito();
    }
}
