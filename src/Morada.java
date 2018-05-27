import java.io.Serializable;
import java.util.Objects;

public class Morada implements Serializable {

    private String rua;
    private String cod_postal;
    private String concelho;
    private Distritos distrito;

    /**
     * Getter da rua
     * @return rua
     */
    public String getRua(){
        return this.rua;
    }

    /**
     * Setter da rua
     * @param rua nova rua
     */
    public void setRua(String rua){
        this.rua = rua;
    }

    /**
     * Getter do código postal
     * @return código postal
     */
    public String getCodPostal(){
        return this.cod_postal;
    }

    /**
     * Setter do código postal
     * @param cod_postal novo codigo postal
     */
    public void setCodPostal(String cod_postal){
        this.cod_postal = cod_postal;
    }

    /**
     * Getter do concelho
     * @return concelho
     */
    public String getConcelho(){
        return this.concelho;
    }

    /**
     * Setter do concelho
     * @param concelho novo concelho
     */
    public void setConcelho(String concelho){
        this.concelho = concelho;
    }

    /**
     * Getter do distrito
     * @return distrito
     */
    public Distritos getDistrito(){
        return this.distrito;
    }

    /**
     * Setter do distrito
     * @param distrito novo distrito
     */
    public void setDistrito(Distritos distrito){
        this.distrito = distrito;
    }


    // Equals & Clone & toString

    /**
     * Método que verifica se duas Moradas são iguais
     * @param o morada a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Morada morada = (Morada) o;
        
        return this.rua.equals(morada.getRua()) &&
               this.cod_postal.equals(morada.getCodPostal()) &&
               this.concelho.equals(morada.getConcelho()) &&
               this.distrito.equals(morada.getDistrito());
    }

    /**
     * Clone da class Morada
     * @return Morada igual
     */
    public Morada clone(){
        return new Morada(this);
    }

    /**
     * Método que transforma um objeto Morada numa String
     * @return String relativa à Morada
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("Rua: "); s.append(rua); s.append('\n');
        s.append("Código Postal: "); s.append(cod_postal); s.append('\n');
        s.append("Concelho: "); s.append(concelho); s.append('\n');
        s.append("Distrito: "); s.append(distrito.toString()); s.append('\n');

        return s.toString();
    }


    //Contrutores

    /**
     * Construtor default da class Morada
     */
    public Morada(){
        this.rua = "";
        this.cod_postal = "";
        this.concelho = "";
        this.distrito = null; // FIXME: 12/05/2018
    }

    /**
     * Construtor paramétrico da class Morada
     * @param rua
     * @param cod_postal
     * @param concelho
     * @param distrito
     */
    public Morada(String rua, String cod_postal, String concelho, Distritos distrito){
        this.rua = rua;
        this.cod_postal = cod_postal;
        this.concelho = concelho;
        this.distrito = distrito;
    }

    /**
     * Construtor parametrizado da class Morada
     * @param outra
     */
    public Morada(Morada outra){
        this.rua = outra.getRua();
        this.cod_postal = outra.getCodPostal();
        this.concelho = outra.getConcelho();
        this.distrito = outra.getDistrito();
    }
}
