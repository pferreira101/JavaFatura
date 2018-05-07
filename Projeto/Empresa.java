import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import Setor.*;

public class Empresa extends Entidade{

    private ArrayList<Fatura> faturas_emitidas;


    public Fatura emiteFatura(int nif, String descricao, double valor, double taxa){ // FIXME: 02/05/2018 onde vamos buscar a taxa??
        GestorSetor gs;

        if(this.getSetores().size() == 1){
            gs = new GestorSetor(this.getSetores(), this.getSetores().get(0), new ArrayList<LogSetor>());
        }
        else{
            gs = new GestorSetor(this.getSetores(), null, new ArrayList<LogSetor>()); // FIXME: 07/05/2018 new Setor() ?
        }

        Fatura f = new Fatura(this.getNome(), this.getNif(), LocalDate.now(), nif, descricao, gs, valor, taxa);

        this.faturas_emitidas.add(f.clone());

        return f;
    }



    public double totalFaturado(){ // FIXME: 03/05/2018 que valor é que é faturado?
        return this.faturas_emitidas.stream().mapToDouble(Fatura::getValor).
                                              sum();
    }

    public double totalFaturado(LocalDate inicio, LocalDate fim){
        return this.faturas_emitidas.stream().filter(f -> f.getData().isAfter(inicio) && f.getData().isBefore(fim)).
                                              mapToDouble(Fatura::getValor).
                                              sum();
    }




    // Sorts

    TreeSet<Fatura> sortBy(){
        TreeSet<Fatura> r = new TreeSet<Fatura>();

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    TreeSet<Fatura> sortBy(Comparator<Fatura> c){
        TreeSet<Fatura> r = new TreeSet<Fatura>(c);

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }
    
    // Getters & Setters

    public ArrayList<Fatura> getFaturasEmitidas(){
        return this.faturas_emitidas.stream().map(Fatura::clone).
                                              collect(Collectors.toCollection(ArrayList::new));

    }

    public void setFaturasEmitidas(ArrayList<Fatura> f){
        this.faturas_emitidas = f.stream().map(Fatura::clone).
                                           collect(Collectors.toCollection(ArrayList::new));

    }


    // Equals & Clone & toString
    
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;

        Empresa e = (Empresa)o;

        return super.equals(e) &&
               this.faturas_emitidas.equals(e.getFaturasEmitidas());

    }
    
    public Empresa clone(){
        return new Empresa(this);
    }
    
    public String toString(){
        return ""; // FIXME: 01/05/2018 
    }


    // Construtores

    public Empresa(){
        super();
        this.faturas_emitidas = new ArrayList<>();
    }

    public Empresa(int nif, String email, String nome, String morada, String password, ArrayList<Setor> setores, ArrayList<Fatura> faturas_emitidas){
        super(nif, email, nome, morada, password, setores);
        this.faturas_emitidas = faturas_emitidas.stream().map(Fatura::clone).
                                                          collect(Collectors.toCollection(ArrayList::new));
    }

    public Empresa(Empresa outro){
        super(outro);
        this.faturas_emitidas = outro.getFaturasEmitidas();
    }
    
}



