import java.util.ArrayList;
import java.util.Objects;

public class EmpresaInterior extends Empresa implements BenificioFiscal{
    
    private double taxa_bonus;
    
    public double reducaoImposto(){
        return taxa_bonus;
    }




    // Getters & Setters

    public double getTaxaBonus() {
        return this.taxa_bonus;
    }

    public void setTaxaBonus(double taxa_bonus) {
        this.taxa_bonus = taxa_bonus;
    }


    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EmpresaInterior e = (EmpresaInterior) o;

        return this.taxa_bonus == e.getTaxaBonus();
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

    public EmpresaInterior(int nif, String email, String nome, Morada morada, String password, ArrayList<String> setores, ArrayList<Fatura> faturas_emitidas, double taxa_bonus) {
        super(nif, email, nome, morada, password, setores, faturas_emitidas);
        this.taxa_bonus = taxa_bonus;
    }

    public EmpresaInterior(EmpresaInterior outro) {
        super(outro);
        this.taxa_bonus = outro.getTaxaBonus();
    }
}