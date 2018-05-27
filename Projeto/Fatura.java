import java.io.Serializable;
import java.time.LocalDateTime;

public class Fatura implements Comparable<Fatura> , Serializable {

    private String empresa;
    private int nif_emitente;
    private LocalDateTime data; // data da fatura
    private int nif_cliente; // número de contribuinte do cliente
    private String descricao; // descrição breve da despesa
    private String setor; // setor da fatura
    private LogSetor reg_alteracoes; // registo alteracoes de setor
    private double valor; // valor da fatura
 
    

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

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
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

    public LogSetor getLogSetor() {
        return this.reg_alteracoes.clone();
    }

    public void setLogSetor(LogSetor log_setor) {
        this.reg_alteracoes = log_setor.clone();
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getSetor(){
        return reg_alteracoes.getSetorAtivo();
    }
    
    public boolean hasSetor(){
        return reg_alteracoes.hasSetorAtivo();
    }
    
    // Metodo para alterar setor de uma fatura
    
    public void mudaSetor(String novo_setor){
        reg_alteracoes.addAlteracao(this.getSetor(), novo_setor);
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
               this.reg_alteracoes.equals(fatura.getLogSetor()) &&
               this.valor == fatura.getValor();
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
   
    public String toString() {
        StringBuilder s = new StringBuilder();
        String setor_fatura = "";     
        
        s.append("Empresa: "); s.append(empresa); s.append('\n');
        s.append("NIF emitente: "); s.append(nif_emitente); s.append('\n');
        s.append("Setor: "); s.append(reg_alteracoes.getSetorAtivo()); s.append('\n');
        s.append("Data: "); s.append(data); s.append('\n');
        s.append("NIF cliente: "); s.append(nif_cliente); s.append('\n');
        s.append("Valor: "); s.append(valor); s.append('\n');
        return s.toString(); 
    }

    
    public int compareTo(Fatura f){
        return this.empresa.compareTo(f.getEmpresa());
    }

    // Contrutores

    /**
     * Construtor default para objetos da classe Fatura
     */
    public Fatura(){
        this.empresa = "";
        this.nif_emitente = -1;
        this.data = LocalDateTime.MIN;
        this.nif_cliente = -1;
        this.descricao = "";
        this.reg_alteracoes = new LogSetor();
        this.valor = -1;
    }

    /**
     * Construtor parametrizado para objetos da classe Fatura
     */
    public Fatura(String empresa, int nif_emitente, LocalDateTime data, int nif_cliente, String descricao, LogSetor log_setor, double valor) {
        this.empresa = empresa;
        this.nif_emitente = nif_emitente;
        this.data = data;
        this.nif_cliente = nif_cliente;
        this.descricao = descricao;
        this.reg_alteracoes = log_setor.clone();
        this.valor = valor;
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
        this.reg_alteracoes = outra.getLogSetor();
        this.valor = outra.getValor();
    }
}
