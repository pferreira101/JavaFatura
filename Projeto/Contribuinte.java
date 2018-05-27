import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

import static java.util.stream.Collectors.*;

public class Contribuinte extends Entidade implements Serializable {
    
    private List<Integer> nif_familia;
    private int num_dependentes;
    private List<Fatura> faturas;
    private List<Fatura> faturas_pendentes;


    /**
     * Método que adiciona uma fatura à lista de faturas, caso seja válida. Caso contrário, adiciona à lista de faturas pendentes
     * @param f fatura a adicionar
     */
    public void addFatura(Fatura f){
        
        if(f.hasSetor()){ 
            faturas.add(f.clone());
        }
        else{
            faturas_pendentes.add(f.clone());
        }
    }

    /**
     * Método que altera o setor de atividade económica de uma fatura
     * @param f fatura a mudar
     * @param novo_setor setor a mudar
     */
    public void alteraSetorFatura(Fatura f, String novo_setor){
        Fatura a_alterar;
        String setor_antigo;
        int pos = 0;
        boolean fstSetor = !f.hasSetor();
        
        if(fstSetor){
            for(Fatura fat : this.faturas_pendentes){
                if(fat.equals(f))
                    break;          
                    pos++;
            }
            
            a_alterar = faturas_pendentes.get(pos);
        }
        else{
            for(Fatura fat : this.faturas){
                if(fat.equals(f))
                    break;          
                    pos++;
            }
            
            a_alterar = faturas.get(pos);
        }

        a_alterar.mudaSetor(novo_setor);
        
        if(fstSetor){
            this.faturas.add(a_alterar);
            this.faturas_pendentes.remove(pos);
        }
    }

    /**
     * Método que calcula o valor total de todas as faturas (válidas e inválidas)
     * @return valor total das faturas
     */
    public double valorTotalFaturas() {
        double res = 0;
        for (Fatura f: faturas) {
            res += f.getValor();
        }
        for (Fatura f: faturas_pendentes) {
            res += f.getValor();
        }
        return res;
    }
    

    
    // Getters & Setters

    /**
     * Getter do número de dependentes do contribuinte
     * @return número de dependentes
     */
    public int getNumDependentes(){
        return this.num_dependentes;
    }

    /**
     * Setter do número de dependentes do contribuinte
     * @param num_dependentes novo número de dependentes
     */
    public void setNumDependentes(int num_dependentes){
        this.num_dependentes = num_dependentes;
    }

    /**
     * Getter dos NIFs do agregado familiar
     * @return NIFs do agregado familiar
     */
    public List<Integer> getNIFFamilia(){
        return new ArrayList(this.nif_familia);
    }

    /**
     * Setter dos NIFs do agregador familiar
     * @param nif_familia novos NIFs
     */
    public void setNIFFamilia(List<Integer> nif_familia){
        this.nif_familia = new ArrayList<>(nif_familia);
    }

    /**
     * Getter da lista de faturas válidas
     * @return faturas válidas
     */
    public List<Fatura> getFaturas(){
        return this.faturas .stream().map(Fatura::clone).
                                      collect(toCollection(ArrayList::new));

    }

    /**
     * Setter das faturas válidas do Contribuinte
     * @param f novas faturas validas
     */
    public void setFaturas(ArrayList<Fatura> f){
        this.faturas = f.stream().map(Fatura::clone).
                                  collect(toCollection(ArrayList::new));
        
    }

    /**
     * Getter das faturas pendentes (inválidas) do Contribuinte
     * @return faturas pendendes
     */
    public ArrayList<Fatura> getFaturasPendentes(){
        return this.faturas_pendentes.stream().map(Fatura::clone).
                                               collect(toCollection(ArrayList::new));

    }

    /**
     * Setter das faturas pendentes (inválidas) do Contribuinte
     * @param f novas faturas pendentes
     */
    public void setFaturasPendentes(ArrayList<Fatura> f){
        this.faturas_pendentes = f.stream().map(Fatura::clone).
                                            collect(toCollection(ArrayList::new));

    }
    
    
    // Equals & Clone & toString

    /**
     * Método que verifica se dois Contribuintes são iguais
     * @param outro objeto a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object outro){
        if(this == outro) return true;
        if(outro == null || this.getClass() != outro.getClass()) return false;

        Contribuinte c = (Contribuinte) outro;

        return super.equals(c) &&
               this.num_dependentes == c.getNumDependentes() &&
               this.nif_familia.equals(c.getNIFFamilia()) &&
               this.faturas.equals(c.getFaturas()) &&
               this.faturas_pendentes.equals(c.getFaturasPendentes());
    }


    /**
     * Clone da class Contribuinte
     * @return Contribuinte igual
     */
    public Contribuinte clone(){
        return new Contribuinte(this);
    }

    /**
     * Método que transforma o objeto Contribuinte em String
     * @return String relativa ao Contribuinte
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append(super.toString()); s.append('\n');
        s.append("NIF: "); s.append(getNif()); s.append('\n');
        s.append("Nome: "); s.append(getNome()); s.append('\n');
        s.append("Email: "); s.append(getEmail()); s.append('\n');
        s.append("Morada:\n"); s.append(getMorada().toString()); s.append('\n');
        return s.toString();
    }


    // Construtores

    /**
     * Construtor default da classe Contribuinte
     */
    public Contribuinte(){
        super();
        this.num_dependentes = 0;
        this.nif_familia = null;
        this.faturas = new ArrayList<>();
        this.faturas_pendentes = new ArrayList<>();
    }

    /**
     * Construtor paramétrico da classe Contribuinte
     * @param nif
     * @param email
     * @param nome
     * @param morada
     * @param password
     * @param num_dependentes
     * @param nif_familia
     * @param faturas
     * @param faturas_pendentes
     */
    public Contribuinte(int nif, String email, String nome, Morada morada, String password, int num_dependentes, List<Integer> nif_familia, List<Fatura> faturas, List<Fatura> faturas_pendentes){
        super(nif, email, nome, morada, password);
        this.num_dependentes = num_dependentes;
        this.nif_familia = new ArrayList<>(nif_familia);
        this.faturas = faturas.stream().map(Fatura::clone).
                                        collect(toCollection(ArrayList::new));
        this.faturas_pendentes = faturas.stream().map(Fatura::clone).
                                                  collect(toCollection(ArrayList::new));
    }

    /**
     * Construtor de cópia da classe Contribuinte
     * @param outro
     */
    public Contribuinte(Contribuinte outro){
        super(outro);
        this.num_dependentes = outro.getNumDependentes();
        this.nif_familia = outro.getNIFFamilia();
        this.faturas = outro.getFaturas();
        this.faturas_pendentes = outro.getFaturasPendentes();
    }
}
