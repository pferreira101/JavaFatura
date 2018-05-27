import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Setor implements Serializable {

    private final String nome;
    private final double taxa;
    private final boolean dedutivel;
    private final double max_dedutivel;

    /**
     * Getter do nome do setor
     * @return nome do setor
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Getter da taxa do setor
     * @return taxa
     */
    public double getTaxa(){
        return this.taxa;
    }

    /**
     * Método que verifica se um setor é dedutivel ou não
     * @return
     */
    public boolean isDedutivel() {
        return this.dedutivel;
    }

    /**
     * Método que retorna o máximo dedutível de um setor
     * @return máximo dedutível
     */
    public double getMaxDedutivel() {
        return this.max_dedutivel;
    }
    
     

    // Equals & Clone & toString

    /**
     * Método que verifica se dois objetos Setor são iguais
     * @param o objeto a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setor s = (Setor) o;

        return this.nome.equals(s.getNome()) &&
               this.taxa == s.getTaxa() &&
               this.dedutivel == s.isDedutivel() ;
    }

    /**
     * Clone da classe Setor
     * @return Setor igual
     */
    public Setor clone(){
        return new Setor(this);
    }

    /**
     * Método que transforma um objeto Setor em String
     * @return String relativa ao Setor
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("Nome: "); s.append(nome); s.append('\n');
        s.append("Taxa: "); s.append(taxa); s.append('\n');
        s.append("Dedutivel: "); s.append(dedutivel); s.append('\n');
        s.append("Máximo dedutivel: "); s.append(max_dedutivel); s.append('\n');

        return s.toString();
    }



    // Construtores

    /**
     * Construtor default da classe Setor
     */
    public Setor(){
        this.nome = "";
        this.taxa = 0;
        this.dedutivel = false;
        this.max_dedutivel = 0;

    }

    /**
     * Construtor paramétrico da classe Setor
     * @param nome
     * @param taxa
     * @param dedutivel
     * @param max_dedutivel
     */
    public Setor(String nome, double taxa, boolean dedutivel, double max_dedutivel){
        this.nome = nome;
        this.taxa = taxa;
        this.dedutivel = dedutivel;
        this.max_dedutivel = max_dedutivel;
    }

    /**
     * Construtor de cópia da classe Setor
     * @param outro
     */
    public Setor(Setor outro){
        this.nome = outro.getNome();
        this.taxa = outro.getTaxa();
        this.dedutivel = outro.isDedutivel();
        this.max_dedutivel = outro.getMaxDedutivel();
    }
}
