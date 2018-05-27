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


    /**
     * Getter do setor ativo
     * @return setor ativo
     */
    public String getSetorAtivo(){
        return this.setor_ativo;
    }


    /**
     * Método para obter listagem das alterações do setor ativo
     * @return alterações do setor ativo
     */
    public List< SimpleEntry<LocalDateTime, SimpleEntry<String, String>>> getAlteracoesSetor(){
        List< SimpleEntry<LocalDateTime, SimpleEntry<String, String>>> alteracoes = new ArrayList<>();
        
        for(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao : this.registo_alteracoes.entrySet())
            alteracoes.add(new SimpleEntry<>(alteracao.getKey(), alteracao.getValue()));
        
        return alteracoes;
    }
    

    /**
     * Método para adicionar uma alteração de setor ativo
     * @param antes setor antigo
     * @param depois setor novo
     */
    public void addAlteracao(String antes, String depois){
        SimpleEntry<String, String> alteracao_setor = new SimpleEntry(antes, depois);
        SimpleEntry<LocalDateTime, SimpleEntry<String, String>> alteracao = new SimpleEntry<>(LocalDateTime.now(), alteracao_setor);
        
        this.registo_alteracoes.put( alteracao.getKey(), alteracao.getValue());
        this.setor_ativo = depois;
    }
    

    /**
     * Método para saber se tem um setor ativo ou nao
     * @return
     */
    public boolean hasSetorAtivo(){
        return !this.setor_ativo.equals("Nenhum");
    }



    // Equals & Clone & toString

    /**
     * Método que verifica se dois objetos LogSetor são iguais
     * @param o objeto a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        LogSetor log = (LogSetor) o;
        
        return  this.setor_ativo.equals(log.getSetorAtivo()) &&
                this.getAlteracoesSetor().equals(log.getAlteracoesSetor());
    }

    /**
     * Clone da classe LogSetor
     * @return LogSetor igual
     */
    public LogSetor clone(){
        return new LogSetor(this);
    }


    /**
     * Método que transforma em String uma alteração do setor ativo
     * @param alteracao
     * @return
     */
    private String toStringAlteracao(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao){
        StringBuilder s = new StringBuilder();
        s.append(alteracao.getKey().toString());
        s.append(" : ");
        s.append(alteracao.getValue().getKey());
        s.append(alteracao.getValue().getValue());
        s.append('\n');
        
        return s.toString();
    }

    /**
     * Método que transforma um objeto LogSetor em String
     * @return String relativa ao objeto
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        
        for(Map.Entry<LocalDateTime, SimpleEntry<String, String>> alteracao : this.registo_alteracoes.entrySet())
                s.append(toStringAlteracao(alteracao));
                
        return s.toString();
    }


    // Construtores

    /**
     * Construtor default da classe LogSetor
     */
    public LogSetor(){
      this.registo_alteracoes = new TreeMap<>();
      this.setor_ativo = "Nenhum";
    }

    /**
     * Construtor paramétrico da classe LogSetor
     * @param setor
     */
    public LogSetor(String setor){
      this.registo_alteracoes = new TreeMap<>();  
      this.setor_ativo = setor;
    }

    /**
     * Construtor de cópia da classe LogSetor
     * @param outro
     */
    public LogSetor(LogSetor outro){
      this.registo_alteracoes = new TreeMap<>();
      this.setor_ativo = outro.getSetorAtivo();
      
      for(SimpleEntry<LocalDateTime, SimpleEntry<String, String>> alteracao : outro.getAlteracoesSetor())
            this.registo_alteracoes.put(alteracao.getKey(), alteracao.getValue());
    }

}
