import java.util.List;
import java.util.Objects;

public class ContribuinteFamiliaNumerosa extends Contribuinte implements BeneficioFiscal{
    
    private double taxa_bonus;
    private static double bonus = 0.05;
    

    
    
    // Getters & Setters

    /**
     * Getter da taxa bonus
     * @return taxa bonus
     */
    public double reducaoImposto(){
        return taxa_bonus;
    }

    /**
     * Setter da taxa bonus
     * @param taxa_bonus nova taxa bonus
     */
    public void setTaxaBonus(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }
    
    
    // Equals & Clone & toString

    /**
     * Método que verifica se duas ContribuinteFamiliaNumerosa são iguais
     * @param o objeto a verificar
     * @return true se forem iguais
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        
        ContribuinteFamiliaNumerosa c = (ContribuinteFamiliaNumerosa) o;
        
        return this.taxa_bonus == c.reducaoImposto();
    }


    /**
     * Clone da classe ContribuinteFamiliaNumerosa
     * @return
     */
    public ContribuinteFamiliaNumerosa clone(){
        return new ContribuinteFamiliaNumerosa(this);    
    }

    /**
     * Método que transforma um objeto ContribuinteFamiliaNumerosa em String
     * @return String relativa ao ContribuinteFamiliaNumerosa
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append(super.toString()); s.append('\n');
        s.append("Taxa bonus: "); s.append(taxa_bonus); s.append('\n');

        return s.toString();
    }
    


    // Construtores

    /**
     * Construtor default da classe ContribuinteFamiliaNumerosa
     * @param taxa_bonus
     */
    public ContribuinteFamiliaNumerosa(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }

    /**
     * Construtor paramétrico da classe ContribuinteFamiliaNumerosa
     * @param nif
     * @param email
     * @param nome
     * @param morada
     * @param password
     * @param num_dependentes
     * @param nif_familia
     * @param faturas
     * @param faturas_pendentes
     */
    public ContribuinteFamiliaNumerosa(int nif, String email, String nome, Morada morada, String password, int num_dependentes, List<Integer> nif_familia,  List<Fatura> faturas, List<Fatura> faturas_pendentes) {
        super(nif, email, nome, morada, password, num_dependentes, nif_familia, faturas, faturas_pendentes);
        this.taxa_bonus = num_dependentes * bonus;
    }

    /**
     * Construtor de cópia da classe ContribuinteFamiliaNumerosa
     * @param outro
     */
    public ContribuinteFamiliaNumerosa(ContribuinteFamiliaNumerosa outro) {
        super(outro);
        this.taxa_bonus = outro.reducaoImposto();
    }
}