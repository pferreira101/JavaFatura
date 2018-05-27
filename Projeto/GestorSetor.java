import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

public class GestorSetor implements Serializable {
    
    private Map<String, Setor> setores;    
    
    
    // Metodo para saber a taxa a deduzir de um dado setor
    public double getTaxa(String setor){
        return this.setores.get(setor).getTaxa();
    }
    
    // Metodo para saber o maximo a deduzir de um dado setor
    public double getMax(String setor){
        return this.setores.get(setor).getMaxDedutivel();
    }
    
    // Metodo para obter nomes dos setores disponiveis   
    public List<String> getSetores(){
        return this.setores.values().stream().map(s -> s.getNome()).
                                     collect(Collectors.toCollection(ArrayList::new));
    }
    
    // Metodo para adicionar um novo setor
    public void addSetor(String nome, double taxa, boolean isDedutivel, double max_dedutivel){
        this.setores.put(nome, new Setor(nome, taxa, isDedutivel, max_dedutivel));
    }
 
    // Equals & Clone & toString
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        GestorSetor gs = (GestorSetor) o;
        
        return this.setores.equals(gs.getSetores());
    }
    
    public String toString(){
        return ""; // FIXME: 03/05/2018 
    }
    
    
    // Construtores
    
    public GestorSetor(){
        this.setores = new HashMap<>(); 
        
        Setor cab = new Setor("Cabeleireiros", 0, false, 0);
        Setor exi_fat = new Setor("Exigência Fatura", 0.15, true, 250);
        Setor geral = new Setor("Geral", 0.35, true, 250);
        Setor edu = new Setor("Educação", 0.3, true, 800);
        Setor auto = new Setor("Automoveis", 0, false, 0);
        Setor moto = new Setor("Motociclos", 0, false, 0);
        Setor res_alo = new Setor("Restauração e Alojamento", 0, false, 0);
        Setor sau = new Setor("Saúde", 0.15, true, 1000);
        Setor pas = new Setor("Passes Mensais", 0, false, 0);
        Setor vet = new Setor("Veterenário", 0, false, 0);
        Setor lar = new Setor("Lares", 0.15, true, 403.75);
        Setor imo = new Setor("Imóveis", 0.15, true, 502);
        
        setores.put("Cabeleireiros", cab);
        setores.put("Exigência Fatura", exi_fat);
        setores.put("Geral", geral);
        setores.put("Educação", edu);
        setores.put("Automoveis", auto);
        setores.put("Motociclos", moto);
        setores.put("Restauração e Alojamento", res_alo);
        setores.put("Saúde", sau);
        setores.put("Passes Mensais", pas);
        setores.put("Veterenário", vet);
        setores.put("Lares", lar);
        setores.put("Imóveis", imo);      
    }   

}
