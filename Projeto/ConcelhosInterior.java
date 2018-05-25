import java.util.Map;
import java.util.HashMap;

public class ConcelhosInterior{
    
    private Map<String, Double> concelhos;
    
    public boolean isInterior(String concelho){
        return this.concelhos.containsKey(concelho.toUpperCase());
    }
    
    public double getBonusDeducao(String concelho) throws ConcelhoNaoEInteriorException{
        if(!isInterior(concelho))
            throw new ConcelhoNaoEInteriorException(concelho);
        else 
            return this.concelhos.get(concelho.toUpperCase());
    }
    
    public ConcelhosInterior(){
        this.concelhos = new HashMap<>();
        this.concelhos.put("PENALVA DO CASTELO", 0.1);
        this.concelhos.put("VINHAIS", 0.08);
        this.concelhos.put("RESENDE", 0.09);
        this.concelhos.put("RIBEIRA DE PENA", 0.07);
        this.concelhos.put("BAIÃO", 0.1);
        this.concelhos.put("CELORICO DE BASTOS", 0.1);
        this.concelhos.put("TABUAÇO", 0.08);
        this.concelhos.put("CINFÃES", 0.07);
        this.concelhos.put("MIRANDELA", 0.06);
    }
}