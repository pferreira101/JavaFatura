import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

public class GestorSetor implements Serializable {
    
    private Map<String, Setor> setores;


    /**
     * Método que retorna a taxa associada a um setor
     * @param setor
     * @return taxa do setor
     */
    public double getTaxa(String setor){
        return this.setores.get(setor).getTaxa();
    }

    /**
     * Método que retorna o máximo dedutivel de um setor
     * @param setor
     * @return
     */
    public double getMax(String setor){
        return this.setores.get(setor).getMaxDedutivel();
    }

    /**
     * Getter dos setores
     * @return setores
     */
    public List<String> getSetores(){
        return this.setores.values().stream().map(s -> s.getNome()).
                                     collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Método que adiciona um novo setor de atividade económica
     * @param nome
     * @param taxa
     * @param isDedutivel
     * @param max_dedutivel
     */
    public void addSetor(String nome, double taxa, boolean isDedutivel, double max_dedutivel){
        this.setores.put(nome, new Setor(nome, taxa, isDedutivel, max_dedutivel));
    }



    // Equals & Clone & toString

    /**
     * Método que verifica se dois objetos GestorSetor são iguais
     * @param o objeto a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        GestorSetor gs = (GestorSetor) o;
        
        return this.setores.equals(gs.getSetores());
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
