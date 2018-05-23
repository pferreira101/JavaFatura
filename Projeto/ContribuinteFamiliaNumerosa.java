public class ContribuinteFamiliaNumerosa extends Contribuinte implements BenificioFiscal{
    
    private double taxa_bonus;
    
    public double reducaoImposto(){
        return taxa_bonus;
    }
}