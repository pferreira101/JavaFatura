import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Entidade implements Serializable {

    private int nif;
    private String email;
    private String nome;
    private Morada morada;
    private String password;


    // Getters & Setters

    /**
     * Getter do NIF
     * @return nif
     */
    public int getNif() {
        return this.nif;
    }

    /**
     * Setter do nif
     * @param nif novo nif
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     * Getter do email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter do email
     * @param email novo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter do nome
     * @return nome
     */
    public String getNome() {
        return this.nome;
    }


    /**
     * Setter do nome
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter da morada
     * @return morada
     */
    public Morada getMorada() {
        return this.morada.clone();
    }

    /**
     * Setter da morada
     * @param morada nova morada
     */
    public void setMorada(Morada morada) {
        this.morada = morada.clone();
    }

    /**
     * Getter da password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter da password
     * @param password nova password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    // Equals && Clone && toString

    /**
     * Função que verifica se duas entidades são iguais
     * @param o Objeto a comparar
     * @return true se forem iguais
     */
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

    /**
     * Clone da class Entidade
     * @return entidade igual
     */
    public Entidade clone(){
        return new Entidade(this);
    }

    /**
     * Transforma em String a Entidade
     * @return string do objeto
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("NIF: "); s.append(nif); s.append('\n');
        s.append("Email: "); s.append(email); s.append('\n');
        s.append("Nome: "); s.append(nome); s.append('\n');
        s.append("Morada: "); s.append(morada); s.append('\n');

        return s.toString();
    }



    // Construtores

    /**
     * Construtor default para objetos da classe Entidade
     */
    public Entidade() {
        this.nif = -1;
        this.email = "";
        this.nome = "";
        this.morada = null; // FIXME: 12/05/2018
        this.password = "";
    }

    /**
     * Construtor paramétrico da classe Entidade
     * @param nif
     * @param email
     * @param nome
     * @param morada
     * @param password
     */
    public Entidade(int nif, String email, String nome, Morada morada, String password) {
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada.clone();
        this.password = password;
    }

    /**
     * Cpnstrutor de cópia da classe Entidade
     * @param outra
     */
    public Entidade(Entidade outra){
        this.nif = outra.getNif();
        this.email = outra.getEmail();
        this.nome = outra.getNome();
        this.morada = outra.getMorada();
        this.password = outra.getPassword();
    }
}
