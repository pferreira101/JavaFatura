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

    /**
     * Getter do nome da empresa
     * @return nome da empresa
     */
    public String getEmpresa() {
        return this.empresa;
    }

    /**
     * Setter do nome da empresa
     * @param empresa novo nome da empresa
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * Getter do NIF Emitente
     * @return nif emitente
     */
    public int getNifEmitente() {
        return this.nif_emitente;
    }

    /**
     * Setter do NIF emitente
      * @param nif_emitente novo NIF emitente
     */
    public void setNifEmitente(int nif_emitente) {
        this.nif_emitente = nif_emitente;
    }

    /**
     * Getter da data de emissão da fatura
     * @return data de emissão da fatura
     */
    public LocalDateTime getData() {
        return this.data;
    }

    /**
     * Setter da data de emissão da fatura
     * @param data nova data
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * Getter do NIF do cliente
     * @return NIF do cliente
     */
    public int getNifCliente() {
        return this.nif_cliente;
    }

    /**
     * Setter do NIF do cliente
     * @param nif_cliente novo NIF do cliente
     */
    public void setNifCliente(int nif_cliente) {
        this.nif_cliente = nif_cliente;
    }

    /**
     * Getter da descrição da fatura
     * @return descrição
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Setter da descrição da fatura
     * @param descricao nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Getter do LogSetor da fatura
     * @return LogSetor
     */
    public LogSetor getLogSetor() {
        return this.reg_alteracoes.clone();
    }

    /**
     * Setter do LogSetor da fatura
     * @param log_setor nova LogSetor
     */
    public void setLogSetor(LogSetor log_setor) {
        this.reg_alteracoes = log_setor.clone();
    }

    /**
     * Getter do valor da fatura
     * @return valor da fatura
     */
    public double getValor() {
        return this.valor;
    }

    /**
     * Setter do valor da fatura
     * @param valor novo valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Getter do Setor de atividade económica da fatura
     * @return setor de atividade económica
     */
    public String getSetor(){
        return reg_alteracoes.getSetorAtivo();
    }

    /**
     * Verifica se a fatura tem um setor ativo
     * @return true se tiver setor ativo
     */
    public boolean hasSetor(){
        return reg_alteracoes.hasSetorAtivo();
    }


    /**
     * Metodo para alterar setor de uma fatura
     * @param novo_setor novo setor ativo
     */
    public void mudaSetor(String novo_setor){
        reg_alteracoes.addAlteracao(this.getSetor(), novo_setor);
    }
    
    
    
    // Equals & Clone & toString
    
    /**
     * Método que verifica se duas faturas são iguais
     * @return true se forem iguais
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
     *
     * @return fatura igual
     */
    public Fatura clone(){
        return new Fatura(this);
    }   

    
    /**
     * Método que transforma um objeto Fatura numa String
     * @return 
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
     * Construtor parametrizado da classe Fatura
     * @param empresa
     * @param nif_emitente
     * @param data
     * @param nif_cliente
     * @param descricao
     * @param log_setor
     * @param valor
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
