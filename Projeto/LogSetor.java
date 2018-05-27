import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

public class LogSetor implements Serializable {

    private Map<LocalDateTime, SimpleEntry<String, String>> registo_alteracoes;
    private String setor_ativo;

    // Metodo para obter setor ativo
    
    public String getSetorAtivo(){
        return this.setor_ativo;
    }
    
    // Metodo para obter listagem das alteracoes
    
    public List< SimpleEntry<LocalDateTime, SimpleEntry<String, String>>> getAlteracoesSetor(){
        List< SimpleEntry<LocalDateTime, SimpleEntry<String, String>>> alteracoes = new ArrayList<>();
        
        for(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao : this.registo_alteracoes.entrySet())
            alteracoes.add(new SimpleEntry<>(alteracao.getKey(), alteracao.getValue()));
        
        return alteracoes;
    }
    
    // Metodo para adicionar uma alteracao
    
    public void addAlteracao(String antes, String depois){
        SimpleEntry<String, String> alteracao_setor = new SimpleEntry(antes, depois);
        SimpleEntry<LocalDateTime, SimpleEntry<String, String>> alteracao = new SimpleEntry<>(LocalDateTime.now(), alteracao_setor);
        
        this.registo_alteracoes.put( alteracao.getKey(), alteracao.getValue());
        this.setor_ativo = depois;
    }
    
    // Metodo para saber se tem um setor ativo ou nao
    
    public boolean hasSetorAtivo(){
        return !this.setor_ativo.equals("Nenhum");
    }
    
    // Equals & Clone & toString
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        LogSetor log = (LogSetor) o;
        
        return  this.setor_ativo.equals(log.getSetorAtivo()) &&
                this.getAlteracoesSetor().equals(log.getAlteracoesSetor());
    }

    public LogSetor clone(){
        return new LogSetor(this);
    }

    private String toStringAlteracao(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao){
        StringBuilder s = new StringBuilder();
        s.append(alteracao.getKey().toString());
        s.append(" : ");
        s.append(alteracao.getValue().getKey());
        s.append(alteracao.getValue().getValue());
        s.append('\n');
        
        return s.toString();
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        
        for(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao : this.registo_alteracoes.entrySet())
                s.append(toStringAlteracao(alteracao));
                
        return s.toString();
    }


    // Construtores

    public LogSetor(){
      this.registo_alteracoes = new TreeMap<>();
      this.setor_ativo = "Nenhum";
    }

    public LogSetor(String setor){
      this.registo_alteracoes = new TreeMap<>();  
      this.setor_ativo = setor;
    }    

    public LogSetor(LogSetor outro){
      this.registo_alteracoes = new TreeMap<>();
      this.setor_ativo = outro.getSetorAtivo();
      
      for(SimpleEntry<LocalDateTime, SimpleEntry<String, String>> alteracao : outro.getAlteracoesSetor())
            this.registo_alteracoes.put(alteracao.getKey(), alteracao.getValue());
    }

}
