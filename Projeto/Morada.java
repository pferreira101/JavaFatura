import java.io.Serializable;
import java.util.Objects;

public class Morada implements Serializable {

    private String rua;
    private String cod_postal;
    private Distrito distrito;

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
               this.cod_postal.equals(morada.getCodPostal()) &&
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
        this.distrito = null; // FIXME: 12/05/2018 
    }

    public Morada(String rua, String cod_postal, Distrito distrito){
        this.rua = rua;
        this.cod_postal = cod_postal;
        this.distrito = distrito;
    }

    public Morada(String rua, String cod_postal, int distrito){
        this.rua = rua;
        this.cod_postal = cod_postal;
        switch(distrito) {
            case 1: this.distrito = Distrito.AVEIRO; break;
            case 2: this.distrito = Distrito.BEJA; break;
            case 3: this.distrito = Distrito.BRAGA; break;
            case 4: this.distrito = Distrito.BRAGANCA; break;
            case 5: this.distrito = Distrito.CASTELOBRANCO; break;
            case 6: this.distrito = Distrito.COIMBRA; break;
            case 7: this.distrito = Distrito.EVORA; break;
            case 8: this.distrito = Distrito.FARO; break;
            case 9: this.distrito = Distrito.GUARDA; break;
            case 10: this.distrito = Distrito.LEIRIA; break;
            case 11: this.distrito = Distrito.LISBOA; break;
            case 12: this.distrito = Distrito.PORTALEGRE; break;
            case 13: this.distrito = Distrito.PORTO; break;
            case 14: this.distrito = Distrito.SANTAREM; break;
            case 15: this.distrito = Distrito.SETUBAL;
            case 16: this.distrito = Distrito.VIANACASTELO; break;
            case 17: this.distrito = Distrito.VILAREAL; break;
            case 18: this.distrito = Distrito.VISEU; break;
        }
    }
    
    public Morada(Morada outra){
        this.rua = outra.getRua();
        this.cod_postal = outra.getCodPostal();
        this.distrito = outra.getDistrito();
    }
}
