import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.List;


public class Empresa extends Entidade implements Serializable {


    private List<Fatura> faturas_emitidas;
    private List<String> setores;

    /**
     * Método que adiciona uma fatura às faturas emitidas
     * @param f fatura a acrescentar
     */
    public void addFatura(Fatura f){ 
        this.faturas_emitidas.add(f.clone());
    }

    /**
     * Getter dos setores de atividade económica que uma empresa tem
     * @return setores
     */
    public List<String> getSetores(){
        return setores.stream().collect(Collectors.toList());
    }


    // Requisitos Básicos

    /**
     * Método que retorna as faturas emitidas ordenadas por valor
     * @return faturas ordenadas por valor
     */
    public Set<Fatura> faturasEmitidasByValor(){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (f2.getValor()!=f1.getValor()) {
                                                        return Double.compare(f2.getValor(), f1.getValor());
                                                    } else {
                                                        return f2.getNifCliente() - f1.getNifCliente();
                                                    }
                                                });

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    /**
     * Método que retorna as faturas emitidas ordenadas por valor entre duas datas
     * @param inicio data inicial
     * @param fim data final
     * @return faturas ordenadas por valor
     */
    public Set<Fatura> faturasEmitidasByValor(LocalDate inicio, LocalDate fim){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (f2.getValor()!=f1.getValor()) {
                                                        return Double.compare(f2.getValor(), f1.getValor());
                                                    } else {
                                                        return f2.getNifCliente() - f1.getNifCliente();
                                                    }
                                                });

        this.faturas_emitidas.stream().filter(f -> f.getData().toLocalDate().isAfter(inicio) && f.getData().toLocalDate().isBefore(fim)).
                                       forEach(f -> r.add(f.clone()));
                                       
        return r;
    }

    /**
     * Método que retorna as faturas emitidas ordenadas por data
     * @return faturas ordenadas por data
     */
    public Set<Fatura> faturasEmitidasByData(){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (!f1.getData().equals(f2.getData())){
                                                        return f2.getData().compareTo(f1.getData());
                                                    } else {
                                                        return f2.getNifCliente() - f1.getNifCliente();
                                                    }
                                                });

        this.faturas_emitidas.forEach(f -> r.add(f.clone()));

        return r;
    }

    /**
     * Método que retorna as faturas emitidas ordenadas por data entre duas datas
     * @param inicio data inicial
     * @param fim data final
     * @return faturas ordenadas por data
     */
    public Set<Fatura> faturasEmitidasByData(LocalDate inicio, LocalDate fim){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (!f1.getData().equals(f2.getData())){
                                                        return f2.getData().compareTo(f1.getData());
                                                    } else {
                                                        return f2.getNifCliente() - f1.getNifCliente();
                                                    }
                                                });

        this.faturas_emitidas.stream().filter(f -> f.getData().toLocalDate().isAfter(inicio) && f.getData().toLocalDate().isBefore(fim)).
                                       forEach(f -> r.add(f.clone()));

        return r;
    }

    /**
     * Método que retorna as faturas emitidas a um NIF
     * @param nif nif de quem se passou as faturas
     * @return faturas emitidas ao nif
     */
    public Set<Fatura> faturasEmitidasFromNIF(int nif){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (f2.getValor()!=f1.getValor()) {
                                                        return Double.compare(f2.getValor(), f1.getValor());
                                                    } else {
                                                        return 1;
                                                    }
                                                });

        this.faturas_emitidas.stream().filter(f -> f.getNifCliente() == nif).
                              forEach(f -> r.add(f.clone()));

        return r;
    }

    /**
     * Método que retorna as faturas emitidas a um NIF entre duas datas
     * @param nif nif de quem se passou as faturas
     * @param inicio data inicial
     * @param fim data final
     * @return faturas emitidas ao nif
     */
    public Set<Fatura> faturasEmitidasFromNIF(int nif, LocalDate inicio, LocalDate fim){
        TreeSet<Fatura> r = new TreeSet<>((f1,f2) -> {
                                                    if (!f1.getData().equals(f2.getData())){
                                                        return f2.getData().compareTo(f1.getData());
                                                    } else {
                                                        return 1;
                                                    }
                                                });

        this.faturas_emitidas.stream().filter(f -> f.getNifCliente() == nif).
                                       filter(f -> f.getData().toLocalDate().isAfter(inicio) && f.getData().toLocalDate().isBefore(fim)).
                                       forEach(f -> r.add(f.clone()));

        return r;
    }

    /**
     * Método que verifica o total faturado pela empresa entre duas datas
     * @param inicio data inicial
     * @param fim data final
     * @return
     */
    public double totalFaturado(LocalDate inicio, LocalDate fim){
        return this.faturas_emitidas.stream().filter(f -> f.getData().toLocalDate().isAfter(inicio) && f.getData().toLocalDate().isBefore(fim)).
                                              mapToDouble(Fatura::getValor).
                                              sum();
    }


    /**
     * Método que retorna o número de faturas emitidas
     * @return número de faturas emitidas
     */
    public int getNFatEmitidas(){
        return this.faturas_emitidas.size();
    }


    // Getters & Setters

    /**
     * Getter das faturas emitidas
     * @return faturas emitidas
     */
    public List<Fatura> getFaturasEmitidas(){
        return this.faturas_emitidas.stream().map(Fatura::clone).
                                              collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * Setter das faturas emitidas
     * @param f novas faturas emitidas
     */
    public void setFaturasEmitidas(ArrayList<Fatura> f){
        this.faturas_emitidas = f.stream().map(Fatura::clone).
                                           collect(Collectors.toCollection(ArrayList::new));

    }

    public double totalFaturado(){
        return this.faturas_emitidas.stream().mapToDouble(f -> f.getValor()).sum();
    }


    // Equals & Clone & toString

    /**
     * Método que verifica se duas empresas são iguais
     * @param o Objeto a comparar
     * @return true se forem iguais
     */
    public boolean equals(Object o){
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;

        Empresa e = (Empresa)o;

        return super.equals(e) &&
               this.faturas_emitidas.equals(e.getFaturasEmitidas());

    }

    /**
     * Clone da class Empresa
     * @return Empresa igual
     */
    public Empresa clone(){
        return new Empresa(this);
    }

    /**
     * Método que transforma uma objeto Empresa numa String
     * @return String relativa à Empresa
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append(super.toString()); s.append('\n');
        s.append("Faturas Emitidas: "); s.append(faturas_emitidas.toString()); s.append('\n');
        s.append("Setores: "); s.append(setores.toString()); s.append('\n');

        return s.toString();
    }



    // Construtores

    /**
     * Construtor default da classe Empresa
     */
    public Empresa(){
        super();
        this.faturas_emitidas = new ArrayList<>();
        this.setores = new ArrayList<>();
    }

    /**
     * Construtor paramétrico da classe Empresa
     * @param nif
     * @param email
     * @param nome
     * @param morada
     * @param password
     * @param setores
     * @param faturas_emitidas
     */
    public Empresa(int nif, String email, String nome, Morada morada, String password, List<String> setores, List<Fatura> faturas_emitidas){
        super(nif, email, nome, morada, password);
        
        this.faturas_emitidas = faturas_emitidas.stream().map(Fatura::clone).
                                                          collect(Collectors.toCollection(ArrayList::new));
                                                          
        this.setores = new ArrayList<>(setores);
    }

    /**
     * Construtor de cópia da classe Empresa
     * @param outro
     */
    public Empresa(Empresa outro){
        super(outro);
        this.faturas_emitidas = outro.getFaturasEmitidas();
        this.setores = outro.getSetores();
    }
    
}



