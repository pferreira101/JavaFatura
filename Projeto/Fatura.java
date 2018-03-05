/**
 * Classe Fatura:
 * 
 * @Henrique Pereira (a80261), Pedro Moreira (a??), Pedro Ferreira (a??) 
 * @2º Ano, 2º Semestre, POO)
 */

import java.time.LocalDate;

public class Fatura
{
    private String tipoC; //"individual" ou "coletivo" - empresas
    private int cliente; // número de contribuinte do cliente
    private int emitente; // contribuinte do emitente da fatura
    private double valor; // valor da fatura
    private LocalDate data; // data da fatura
    private String setor; // saúde, educação, restauração, transportes, etc.
    private String descricao; // descrição breve da despesa

    /**
     * Construtor default para objetos da classe Fatura
     */
    public Fatura()
    {
        // inicializa as variáveis de instância
        this.cliente=this.emitente=-1;
        this.valor=0;
        this.data=null;
        this.setor=this.descricao=this.tipoC=null;
    }
    
    /**
     * Construtor parametrizado para objetos da classe Fatura
     */
    public Fatura (String tipoC, int cliente, int emitente, double valor, 
        LocalDate data, String setor, String descricao){
        this.tipoC=tipoC;
        this.cliente=cliente;
        this.emitente=emitente;
        this.valor=valor;
        this.data=data;
        this.setor=setor;
        this.descricao=descricao;
    }

    /**
     * Construtor por cópia para objetos da classe Fatura
     */
    public Fatura (Fatura f) {
        this.tipoC=f.getTipo();
        this.cliente=f.getCliente();
        this.emitente=f.getEmitente();
        this.valor=f.getValor();
        this.data=f.getData();
        this.setor=f.getSetor();
        this.descricao=f.getDescricao();
    }
    
    /**
     * Método que verifica se dois objetos são iguais
     */
    public boolean equals (Object o) {
        if (this==o) return true;
        if (o==null || getClass() != o.getClass() ) return false;
        Fatura f = (Fatura) o;
        return f.getTipo().equals(tipoC) && f.getCliente()==cliente && f.getEmitente()==emitente && f.getValor()==valor && 
            f.getData().isEqual(data) && f.getSetor().equals(setor) && f.getDescricao().equals(descricao);
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
    public String getSetor() {
        return this.setor;
    }
    
    /**
     * Setter do setor da fatura
     */
    public void setSetor(String setor) {
        this.setor=setor;
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
            +this.getValor()+"€\nDATA: "+this.getData()+"\nSETOR: "+this.getSetor()+"\nDESCRICAO: "+this.getDescricao()+"\n");
    }
}
