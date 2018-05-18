package Setor;


import java.io.Serializable;

public class Setor implements Serializable {

    private final String nome;
    private final double taxa;
    private final boolean dedutivel;
    private final double max_dedutivel;
    private double montante_deduzido;

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
    // Calcular valor a deduzir de uma fatura
    
    public void processaFatura(double valorFatura){
    double a_deduzir = valorFatura*taxa;
    
    if(montante_deduzido + a_deduzir > max_dedutivel)
        montante_deduzido = max_dedutivel;
    else     
        montante_deduzido += a_deduzir;
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
