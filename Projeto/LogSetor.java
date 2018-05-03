import java.time.LocalDate;
import java.util.Objects;

public class LogSetor {

    private String antes;
    private String depois;
    private LocalDate data;


    // Getters e Setters

    public String getAntes() {
        return this.antes;
    }

    public void setAntes(String antes) {
        this.antes = antes;
    }

    public String getDepois() {
        return this.depois;
    }

    public void setDepois(String depois) {
        this.depois = depois;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        LogSetor log = (LogSetor) o;

        return this.antes.equals(log.getAntes()) &&
               this.depois.equals(log.getDepois()) &&
               this.data.equals(log.getData());
    }

    public LogSetor clone(){
        return new LogSetor(this);
    }

    public String toString(){
        return ""; // FIXME: 03/05/2018
    }


    // Construtores

    public LogSetor(){
        this.antes = "";
        this.depois = "";
        this.data = LocalDate.MIN;
    }

    public LogSetor (String antes, String depois, LocalDate data){
        this.antes = antes;
        this.depois = depois;
        this.data = data;
    }

    public LogSetor(LogSetor outro){
        this.antes = outro.getAntes();
        this.depois = outro.getDepois();
        this.data = outro.getData();
    }

}
