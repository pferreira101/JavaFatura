/**
 * Classe Fatura:
 * 
 * @Henrique Pereira (a80261), Pedro Moreira (a??), Pedro Ferreira (a??) 
 * @2º Ano, 2º Semestre, POO)
 */

import java.time.LocalDate;
import java.util.Scanner;

public class Fatura{

    private String tipoC; //"individual" ou "coletivo" - empresas
    private int cliente; // número de contribuinte do cliente
    private int emitente; // contribuinte do emitente da fatura
    private double valor; // valor da fatura antes dos impostos
    private double taxa; // valor da taxa de imposto (em decimal, i.e. 0.06=6%, 0.23=23%, etc.)
    private LocalDate data; // data da fatura
    private String[] setores; // saúde, educação, restauração, transportes, etc.
    private String setor_ativo;
    private String descricao; // descrição breve da despesa

    
    
    public double valorAPagar () {
        double res=this.valor*(1+this.taxa);
        return res;
    }
    
    public void AtribuiSetor(){
        String[] setores = this.getSetores();
        Scanner sc = new Scanner(System.in);
        int x;
        
        System.out.println("Escolha o setor que deseja atribuir à fatura");
        
        for(int i = 0; i < setores.length; i++){
            System.out.println(i + setores[i]);
        }
        
        x = sc.nextInt();
        this.setSetorAtivo(setores[x]);
    }

    
    
    /**
     * Construtor default para objetos da classe Fatura
     */
    public Fatura(){
        this.cliente = -1;
        this.emitente = -1;
        this.valor = 0;
        this.data = LocalDate.MIN;
        this.setores = null;
        this.setor_ativo = "";
        this.taxa = 0;
        this.descricao = "";
        this.tipoC = "";
    }
    
    /**
     * Construtor parametrizado para objetos da classe Fatura
     */
    public Fatura (String tipoC, int cliente, int emitente, double valor, LocalDate data, String[] setores, String setor_ativo, String descricao){
        this.tipoC = tipoC;
        this.cliente = cliente;
        this.emitente = emitente;
        this.valor = valor;
        this.data = data;
        this.setores = setores;
        this.setor_ativo = setor_ativo;
        this.descricao = descricao;
    }

    /**
     * Construtor por cópia para objetos da classe Fatura
     */
    public Fatura (Fatura f) {
        this.tipoC = f.getTipo();
        this.cliente = f.getCliente();
        this.emitente = f.getEmitente();
        this.valor = f.getValor();
        this.data = f.getData();
        this.setores = f.getSetores();
        this.setor_ativo = f.getSetorAtivo();
        this.descricao = f.getDescricao();
    }
    
    /**
     * Método que verifica se duas faturas são iguais
     */
    public boolean equals (Object o) {
        if (this==o) return true;
        if (o==null || getClass() != o.getClass() ) return false;
        
        Fatura f = (Fatura) o;
        
        return (this.tipoC.equals(f.getTipo()) && this.cliente == f.getCliente() && this.emitente == f.getEmitente() && this.valor == f.getValor() &&
                this.data.isEqual(f.getData()) && this.setores.equals(f.getSetores()) && this.descricao.equals(f.getDescricao()));
    }

    /**
     * Método que faz o clone de uma fatura
     */
    public Fatura clone(){
        return new Fatura(this);
    }   
    
    /**
     * Getter do tipo de emitente da fatura
     */
    public String getTipo() {
        return this.tipoC;
    }
    
    /**
     * Setter do tipo de emitente da fatura
     */
    public void setTipo(String tipo) {
        this.tipoC=tipo;
    }
    
    /**
     * Getter do NIF do cliente 
     */
    public int getCliente() {
        return this.cliente;
    }
    
    /**
     * Setter do NIF do cliente
     */
    public void setCliente(int cliente) {
        this.cliente=cliente;
    }
    
    /**
     * Getter do NIF do emitente
     */
    public int getEmitente() {
        return this.emitente;
    }
    
    /**
     * Setter do NIF do emitente
     */
    public void setEmitente(int emitente) {
        this.emitente=emitente;
    }
    
    /**
     * Getter do valor da fatura
     */
    public double getValor() {
        return this.valor;
    }
    
    /**
     * Setter do valor da fatura
     */
    public void setValor(double valor) {
        this.valor=valor;
    }
    
    /**
     * Getter do valor da taxa
     */
    public double getTaxa() {
        return this.taxa;
    }
    
    /**
     * Setter do valor da taxa
     */
    public void setTaxa(double taxa) {
        this.taxa=taxa;
    }
    
    /**
     * Getter da data da fatura
     */
    public LocalDate getData() {
        return this.data;
    }
    
    /**
     * Setter da data da fatura
     */
    public void setData(LocalDate data) {
        this.data=data;
    }
    
    /**
     * Getter do setor da fatura
     */
    public String[] getSetores() {
        return this.setores;
    }
    
    /**
     * Setter do setor da fatura
     */
    public void setSetores(String[] setores) {
        this.setores = setores;
    }
    
     /**
     * Getter do setor ativo da fatura
     */
    public String getSetorAtivo() {
        return this.setor_ativo;
    }
    
    /**
     * Setter do setor ativo da fatura
     */
    public void setSetorAtivo(String setor_ativo) {
        this.setor_ativo = setor_ativo;
    }
    
    /**
     * Getter da descrição da fatura
     */
    public String getDescricao() {
        return this.descricao;
    }
    
    /**
     * Getter da descrição da fatura
     */
    public void setDescricao(String descricao) {
        this.descricao=descricao;
    }
    
    /**
     * Método que transforma um objeto Fatura numa String
     */
    public String toString () {
        /* TESTE
         * System.out.print("TIPO: "+this.getTipo()+"\nCLIENTE: "+this.getCliente()+"\nEMITENTE: "+this.getEmitente()+"\nVALOR: "
            +this.getValor()+"€\nDATA: "+this.getData()+"\nSETOR: "+this.getSetor()+"\nDESCRICAO: "+this.getDescricao()+"\n");
        */
        return ("TIPO: "+this.getTipo()+"\nCLIENTE: "+this.getCliente()+"\nEMITENTE: "+this.getEmitente()+"\nVALOR: "
            +this.getValor()+"€\nDATA: "+this.getData()+"\nSETOR: "+this.getSetores()+"\nDESCRICAO: "+this.getDescricao()+"\n");
    }
    
}
