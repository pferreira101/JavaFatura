import java.util.List;
import java.util.Objects;

public class ContribuinteFamiliaNumerosa extends Contribuinte implements BenificioFiscal{
    
    private double taxa_bonus;
    private static double bonus = 0.05;
    

    
    
    // Getters & Setters

    public double reducaoImposto(){
        return taxa_bonus;
    }

    public void setTaxaBonus(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }
    
    
    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        
        ContribuinteFamiliaNumerosa c = (ContribuinteFamiliaNumerosa) o;
        
        return this.taxa_bonus == c.reducaoImposto();
    }
    
    
    public ContribuinteFamiliaNumerosa clone(){
        return new ContribuinteFamiliaNumerosa(this);    
    }
    
    
    public String toString(){
        return ""; // FIXME: 23-05-2018 
    }
    
    
    


    // Construtores

    public ContribuinteFamiliaNumerosa(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }

    public ContribuinteFamiliaNumerosa(int nif, String email, String nome, Morada morada, String password, int num_dependentes, List<Integer> nif_familia,  List<Fatura> faturas, List<Fatura> faturas_pendentes) {
        super(nif, email, nome, morada, password, num_dependentes, nif_familia, faturas, faturas_pendentes);
        this.taxa_bonus = num_dependentes * bonus;
    }

    public ContribuinteFamiliaNumerosa(ContribuinteFamiliaNumerosa outro) {
        super(outro);
        this.taxa_bonus = outro.reducaoImposto();
    }
}