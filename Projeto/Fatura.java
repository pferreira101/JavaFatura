import Setor.GestorSetor;

import java.time.LocalDate;

public class Fatura implements Comparable<Fatura>{

    private String empresa;
    private int nif_emitente;
    private LocalDate data; // data da fatura
    private int nif_cliente; // número de contribuinte do cliente
    private String descricao; // descrição breve da despesa
    private GestorSetor gestor_setor;
    private double valor; // valor da fatura antes dos impostos
    private double taxa;
    


    public double valorAPagar () {
        return (this.valor * (1 + this.taxa));
    }



    // Getters & Setters

    public String getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getNifEmitente() {
        return this.nif_emitente;
    }

    public void setNifEmitente(int nif_emitente) {
        this.nif_emitente = nif_emitente;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getNifCliente() {
        return this.nif_cliente;
    }

    public void setNifCliente(int nif_cliente) {
        this.nif_cliente = nif_cliente;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GestorSetor getGestorSetor() {
        return this.gestor_setor.clone();
    }

    public void setGestorSetor(GestorSetor gestor_setor) {
        this.gestor_setor = gestor_setor.clone();
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTaxa() {
        return this.taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }


    // Equals & Clone & toString
    
    /**
     * Método que verifica se duas faturas são iguais
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fatura fatura = (Fatura) o;

        return this.empresa.equals(fatura.getEmpresa()) &&
               this.nif_emitente == fatura.getNifEmitente() &&
               this.data.isEqual(fatura.getData()) &&
               this.nif_cliente == fatura.getNifCliente() &&
               this.descricao.equals(fatura.getDescricao()) &&
               this.gestor_setor.equals(fatura.getGestorSetor()) &&
               this.valor == fatura.getValor() &&
               this.taxa == fatura.getTaxa();
    }


    /**
     * Método que faz o clone de uma fatura
     */
    public Fatura clone(){
        return new Fatura(this);
    }   

    
    /**
     * Método que transforma um objeto Fatura numa String
     */
    public String toString () {

        return ""; // FIXME: 01/05/2018
    }


    public int compareTo(Fatura f){
        return this.empresa.compareTo(f.getEmpresa());
    }

    // Contrutores

    /**
     * Construtor default para objetos da classe Fatura
     */
    public Fatura() {
        this.empresa = "";
        this.nif_emitente = -1;
        this.data = LocalDate.MIN;
        this.nif_cliente = -1;
        this.descricao = "";
        this.gestor_setor = new GestorSetor();
        this.valor = -1;
        this.taxa = 0;
    }

    /**
     * Construtor parametrizado para objetos da classe Fatura
     */
    public Fatura(String empresa, int nif_emitente, LocalDate data, int nif_cliente, String descricao, GestorSetor gestor_setor, double valor, double taxa) {
        this.empresa = empresa;
        this.nif_emitente = nif_emitente;
        this.data = data;
        this.nif_cliente = nif_cliente;
        this.descricao = descricao;
        this.gestor_setor = gestor_setor.clone();
        this.valor = valor;
        this.taxa = taxa;
    }

    /**
     * Construtor por cópia para objetos da classe Fatura
     */
    public Fatura(Fatura outra) {
        this.empresa = outra.getEmpresa();
        this.nif_emitente = outra.getNifEmitente();
        this.data = outra.getData();
        this.nif_cliente = outra.getNifCliente();
        this.descricao = outra.getDescricao();
        this.gestor_setor = outra.getGestorSetor();
        this.valor = outra.getValor();
        this.taxa = outra.getTaxa();
    }
}
