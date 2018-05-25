import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

public class GestorSetor implements Serializable {
    
    private Map<String, Setor> setores;    
    
    
    public void contabilizaFatura(Fatura f){             
        Setor setor = setores.get(f.getSetor());
      
        setor.addFaturaSetor(f);   
    }
    
    /*
     * Corrigir este metodo quando tiver treemap no setor
     */
    public void descontabilizaFatura(Fatura f, String setor){        
        Setor setor_antigo = setores.get(setor);
        
        setor_antigo.removeFaturaSetor(f);
    }
    
    // Getters & Setters
       
    public List<Setor> getSetores(){
        return this.setores.values().stream().map(s -> s.clone()).
                                     collect(Collectors.toCollection(ArrayList::new));
    }
    
    
    public void setSetores(List<Setor> setores){
        this.setores = new HashMap<>();
        
        for(Setor s : setores){
            SimpleEntry<String, Setor> setor = new SimpleEntry<>(s.getNome(), s.clone());
            this.setores.put(setor.getKey(), setor.getValue());
        }                                         
    }
    

 
    // Equals & Clone & toString
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        GestorSetor gs = (GestorSetor) o;
        
        return this.setores.equals(gs.getSetores());
    }
    
    public GestorSetor clone(){
        return new GestorSetor(this);
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

    /*
    public GestorSetor(ArrayList<Setor> setores, Setor setor_ativo, ArrayList<LogSetor> log) {
        this.setores = setores.stream().map(Setor::clone).collect(Collectors.toCollection(ArrayList::new));
        this.setor_ativo = setor_ativo.clone();
        this.log = log.stream().map(LogSetor::clone).collect(Collectors.toCollection(ArrayList::new));
    }
    */
   
    public GestorSetor(GestorSetor outro){
        this.setSetores(outro.getSetores());
    }
}
