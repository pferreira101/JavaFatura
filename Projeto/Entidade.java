import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Setor.*;

public class Entidade implements Serializable {

    private int nif;
    private String email;
    private String nome;
    private Morada morada;
    private String password;


    // Getters & Setters

    public int getNif() {
        return this.nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Morada getMorada() {
        return this.morada.clone();
    }

    public void setMorada(Morada morada) {
        this.morada = morada.clone();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Equals && Clone && toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entidade entidade = (Entidade) o;

        return this.nif == entidade.nif &&
               this.email.equals(entidade.getEmail()) &&
               this.nome.equals(entidade.getNome()) &&
               this.morada.equals(entidade.getMorada()) &&
               this.password.equals(entidade.getPassword());
    }

    public Entidade clone(){
        return new Entidade(this);
    }
    
    public String toString(){
        return ""; // FIXME: 01/05/2018 
    }



// Construtores

    public Entidade() {
        this.nif = -1;
        this.email = "";
        this.nome = "";
        this.morada = null; // FIXME: 12/05/2018
        this.password = "";
    }

    public Entidade(int nif, String email, String nome, Morada morada, String password) {
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada.clone();
        this.password = password;
    }

    public Entidade(Entidade outra){
        this.nif = outra.getNif();
        this.email = outra.getEmail();
        this.nome = outra.getNome();
        this.morada = outra.getMorada();
        this.password = outra.getPassword();
    }
}
