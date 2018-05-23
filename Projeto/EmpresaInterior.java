public class EmpresaInterior extends Empresa implements BenificioFiscal{
    
    private double taxa_bonus;
    
    public double reducaoImposto(){
        return taxa_bonus;
    }
}