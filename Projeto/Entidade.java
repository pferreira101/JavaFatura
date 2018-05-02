import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Entidade {

    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;
    private ArrayList<Setor> setores;


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

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Setor> getSetores() {
        return this.setores.stream().map(Setor::clone).
                                     collect(Collectors.toCollection(ArrayList::new));
    }

    public void setSetores(ArrayList<Setor> setores) {
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
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
               this.password.equals(entidade.getPassword()) &&
               this.setores.equals(entidade.getSetores());
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
        this.morada = "";
        this.password = "";
        this.setores = new ArrayList<>();
    }

    public Entidade(int nif, String email, String nome, String morada, String password, ArrayList<Setor> setores) {
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
        this.setores = setores.stream().map(Setor::clone).
                                        collect(Collectors.toCollection(ArrayList::new));
    }

    public Entidade(Entidade outra){
        this.nif = outra.getNif();
        this.email = outra.getEmail();
        this.nome = outra.getNome();
        this.morada = outra.getMorada();
        this.password = outra.getPassword();
        this.setores = outra.getSetores();
    }
}
