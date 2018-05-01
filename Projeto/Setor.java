public class Setor {

    private final String nome;
    private final double taxa;


    public String getNome(){
        return this.nome;
    }

    public double getTaxa(){
        return this.taxa;
    }



    // Equals & Clone & toString

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setor s = (Setor) o;

        return this.nome.equals(s.getNome()) &&
               this.taxa == s.getTaxa();
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
        this.taxa = -1;
    }

    public Setor(String nome, double taxa){
        this.nome = nome;
        this.taxa = taxa;
    }

    public Setor(Setor outro){
        this.nome = outro.getNome();
        this.taxa = outro.getTaxa();
    }
}
