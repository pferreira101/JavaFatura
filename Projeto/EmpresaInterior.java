import java.util.List;
import java.util.Objects;

public class EmpresaInterior extends Empresa implements BenificioFiscal{
    
    private double taxa_bonus;
    
    public double reducaoImposto(){
        return taxa_bonus;
    }

    // Getters & Setter

    public void setTaxaBonus(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }


    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EmpresaInterior e = (EmpresaInterior) o;

        return this.taxa_bonus == e.reducaoImposto();
    }


    public EmpresaInterior clone(){
        return new EmpresaInterior(this);
    }


    public String toString(){
        return ""; // FIXME: 23-05-2018
    }



    // Construtores

    public EmpresaInterior(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }

    public EmpresaInterior(int nif, String email, String nome, Morada morada, String password, List<String> setores, List<Fatura> faturas_emitidas, double taxa_bonus) {
        super(nif, email, nome, morada, password, setores, faturas_emitidas);
        this.taxa_bonus = taxa_bonus;
    }

    public EmpresaInterior(EmpresaInterior outro) {
        super(outro);
        this.taxa_bonus = outro.reducaoImposto();
    }
}