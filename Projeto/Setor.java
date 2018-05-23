import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/*
 * Adicionar TreeMap ordenado por data ao setor para manter
 * registo dos valores deduzidos e podermos retirar corretamente o valor deduzido por uma fatura
 * ex: fatura registada quando tinha 4 filhos descontou 10, se adicionar um filho e alterar o setor da fatura
 * sera retirado 12.5 ao setor antigo 
 */
public class Setor implements Serializable {

    private final String nome;
    private final double taxa;
    private final boolean dedutivel;
    private final double max_dedutivel;
    private double montante_deduzido;    
    private Map<Fatura,Double> valores_deduzidos;

    public String getNome(){
        return this.nome;
    }

    public double getTaxa(){
        return this.taxa;
    }

    public boolean isDedutivel() {
        return this.dedutivel;
    }

    public double getMaxDedutivel() {
        return this.max_dedutivel;
    }
    
    public double getMontDeduzido() {
        return this.montante_deduzido;
    }
    
    /*
      Falta adicionar ao tree map a fatura
     */
    public void addFaturaSetor(Fatura f){
        this.valores_deduzidos.put(f, aDeduzir(f));
    }
    
    /*
     * Assinatura correta do metodo
     *  public void removeValorDeduzido(Fatura f)
     */
   
    public void removeFaturaSetor(Fatura f){
        this.valores_deduzidos.remove(f);
    }
  
    // adicionar taxa de empresa e assim **************************
    public double aDeduzir(Fatura f){
        return f.getValor() * ( + this.taxa);
    }
    
    public double valorDeduzido(){
        double total_deduzido = 0;
        for( double valor_deduzido : valores_deduzidos.values())
            total_deduzido += valor_deduzido;
        
        return total_deduzido > this.max_dedutivel? this.max_dedutivel : total_deduzido;    
    }
    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setor s = (Setor) o;

        return this.nome.equals(s.getNome()) &&
               this.taxa == s.getTaxa() &&
               this.dedutivel == s.isDedutivel() ;
    }

    public Setor clone(){
        return new Setor(this);
    }

    public String toString(){
        return ""; // FIXME: 01/05/2018
    }



    // Construtores

    public Setor(){
        //this.valores_deduzidos = new TreeMap<>();
        this.nome = "";
        this.taxa = 0;
        this.dedutivel = false;
        this.max_dedutivel = 0;
        this.montante_deduzido = 0;
    }

    public Setor(String nome, double taxa, boolean dedutivel, double max_dedutivel, double montante_deduzido){
        this.nome = nome;
        this.taxa = taxa;
        this.dedutivel = dedutivel;
        this.max_dedutivel = max_dedutivel;
        this.montante_deduzido = montante_deduzido;
    }

    public Setor(Setor outro){
        this.nome = outro.getNome();
        this.taxa = outro.getTaxa();
        this.dedutivel = outro.isDedutivel();
        this.max_dedutivel = outro.getMaxDedutivel();
        this.montante_deduzido = outro.getMontDeduzido();
    }
}
