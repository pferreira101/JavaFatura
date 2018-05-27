import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
* Classe que representa o Estado do programa, que guarda os vários registos e que retém informações acerca do Administrador do sistema
*/
public class Sistema implements Serializable{

    private HashMap<Integer, Entidade> entidades = new HashMap<>();
    private GestorSetor gestor_setores = new GestorSetor();
    private int admin_nif=20172018;
    private String admin_password="miei";
    private boolean admin_mode;
    private int nif_ativo;
    

    // Registo de Entidades

    /**
    * Método que permite registar um contribuinte no sistema
    * 
    * @param nif NIF do contribuinte
    * @param nome Nome do contribuinte
    * @param email Email do contribuinte
    * @param password Palavra-chave do contribuinte
    * @param rua Rua do contribuinte
    * @param cod_postal Código Postal do contribuinte
    * @param concelho Concelho do contribuinte
    * @param distrito Distrito do contribuinte
    * @param n_filhos Número de filhos do contribuinte
    * @param nif_familia Lista com o NIF dos filhos do contribuinte
    */
    public void registaEntidade (Integer nif, String nome, String email, String password, String rua, String cod_postal, String concelho, Distritos distrito, int n_filhos, List<Integer> nif_familia) throws NIFJaRegistadoException{
        Morada morada = new Morada(rua, cod_postal, concelho, distrito);        
        Entidade nova = new Contribuinte(nif, email, nome,  morada, password, n_filhos,  nif_familia, new ArrayList<Fatura>(), new ArrayList<Fatura>());
        if(this.entidades.putIfAbsent(nova.getNif(),nova) != null)
            throw new NIFJaRegistadoException(nif.toString());
 
    }
    
    /**
    * Método que permite registar uma empresa no sistema
    * 
    * @param nif NIF da empresa
    * @param nome Nome da empresa
    * @param email Email da empresa
    * @param password Palavra-chave da empresa
    * @param rua Rua da empresa
    * @param cod_postal Código Postal da empresa
    * @param concelho Concelho da empresa
    * @param distrito Distrito da empresa
    * @param setores Lista com os setores de atividade económica de empresa
    */
    public void registaEntidade (Integer nif, String nome, String email, String password, String rua, String cod_postal, String concelho, Distritos distrito, List<String> setores) throws ConcelhoNaoEInteriorException, NIFJaRegistadoException{
        Entidade nova;
        ConcelhosInterior concelhos_int = new ConcelhosInterior();
        double bonus=0;
        Morada morada = new Morada(rua, cod_postal, concelho, distrito);
        
        if(concelhos_int.isInterior(concelho)){
            bonus = concelhos_int.getBonusDeducao(concelho);
            nova = new EmpresaInterior(nif, email, nome, morada, password, setores, new ArrayList<Fatura>(), bonus);            
        }
        else
            nova = new Empresa(nif, email, nome, morada, password, setores, new ArrayList<Fatura>());
        
        if(this.entidades.putIfAbsent(nova.getNif(),nova) != null)
            throw new NIFJaRegistadoException(nif.toString());
    }
    
    /**
    * Método que identifica o tipo de entidade que efetuou o login
    * 
    * @param nif NIF
    * @param password Password
    * 
    * @return -1 se o login falhou, 0 se for um contribuinte, 1 se for uma empresa e 2 se for o admin
    */
    public int logIn (int nif, String password){
        if(nif == this.admin_nif && this.admin_password.equals(password)){
            this.admin_mode = true;
            return 2;
        }    
        
        Entidade entidade = this.entidades.get(nif);
        if (entidade == null) return -1;

        if(entidade.getPassword().equals(password)){
                this.nif_ativo = nif; // carrega o nif da entidade ativa
                if(entidade instanceof Contribuinte)
                    return 0;
                else 
                    return 1;
        }
        return -1;
    }
    
    /**
    * Método que permite obter faturas do contribuinte
    * 
    * @return Lista com as faturas do contribuinte com sessão ativa
    */
    public List<Fatura> faturasContribuinte() throws NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
        return ((Contribuinte)e).getFaturas();
    }
    
    /**
    * Método que permite obter as faturas pendentes do contribuinte
    * 
    * @return Lista com as faturas pendentes do contribuinte com sessão ativa
    */
    public List<Fatura> faturasPendentes() throws NIFNaoEDeUmContribuinteException{
         Entidade e = this.entidades.get(nif_ativo);
         
         if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
         return ((Contribuinte)e).getFaturasPendentes();  
    }
    
    /**
    * Método que permite obter faturas da empresa, ordenadas pelo valor
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpByValor() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByValor();
    }
    
    /**
    * Método que permite obter faturas da empresa, ordenadas pelo valor, num intervalo de tempo
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpByValor(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByValor(inicio, fim);
    }
    
    /**
    * Método que permite obter faturas da empresa, ordenadas por data
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpByData() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByData();
    }
     
    /**
    * Método que permite obter faturas da empresa, ordenadas por data, num intervalo de tempo
    * 
    * @param inicio Data de início do intervalo
    * @param fim Data de fim do intervalo
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpByData(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByData(inicio, fim);
    }
    
    /**
    * Método que permite obter faturas da empresa emitidas para um certo contribuinte
    * 
    * @param nif NIF do contribuinte
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpFromCliente(int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasFromNIF(nif);
    }  
    
    /**
    * Método que permite obter faturas da empresa emitidas para um certo contribuinte num dado intervalo de tempo
    * 
    * @param inicio Data de início do intervalo
    * @param fim Data de fim do intervalo
    * 
    * @return Lista com as faturas da empresa
    */
    public Set<Fatura> faturasEmpFromCliente(LocalDate inicio, LocalDate fim, int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasFromNIF(nif, inicio, fim);
    }    
      
    /** Método que permite obter o valor faturado por uma empresa entre determinada data
    * 
    * @param inicio Data de início do intervalo
    * @param fim Data de fim do intervalo
    * 
    * @return Valor Total Faturado
    */
    public double valorFaturadoEmpresa(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa)e).totalFaturado(inicio, fim);    
    }

    /** Método para calcular os valores deduzidos pela entidade ativa
    * 
    * @return Mapa com as deduções do NIF ativo para cada setor
    */
    
    public Map<String, Double> getDeducoesNifAtivo() throws NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
        
        return calculaDeducoes((Contribuinte) e);     
    }
    
    
    /** Método para calcular os valores deduzidos pelos familiares
    * 
    * @return Mapa com os valores deduzidos para cada familiar
    */
    public List< SimpleEntry<Integer, Map<String, Double>>>  getDeducoesFamilia() throws NIFNaoEDeUmContribuinteException{
        List< SimpleEntry<Integer, Map<String, Double>>> deducoes_fam = new ArrayList<>();
        Entidade e = this.entidades.get(nif_ativo);
        Contribuinte familiar;
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
             
        Contribuinte c = (Contribuinte) e;     
        
        for(int nif : c.getNIFFamilia()){
            familiar = (Contribuinte) this.entidades.get(nif);
            if(familiar != null)
                deducoes_fam.add(new SimpleEntry<Integer, Map<String, Double>>(nif, calculaDeducoes(familiar)));
        }
       
        return deducoes_fam;
    }
    
    /** Método que permite adicionar faturas ao sistema
    * 
    * @param nif_cliente NIF do cliente
    * @param valor Valor da Fatura
    * @param descricao Descrição da Fatura
    */
    public void addFaturaSistema(Integer nif_cliente, double valor, String descricao) throws NIFNaoRegistadoException, NIFDaFaturaEEmpresaException, EntidadeAtivaNaoEEmpresaException {
        Entidade cliente =  getEntidade(nif_cliente);
        Entidade empresa_emitente =  getEntidade(this.nif_ativo);
        Fatura f;     
        
        if(cliente instanceof Empresa)
            throw new NIFDaFaturaEEmpresaException(nif_cliente.toString());
            
        if(!(empresa_emitente instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        Empresa emp = (Empresa) empresa_emitente;
        Contribuinte cont = (Contribuinte) cliente;   
        
        List<String> setores_emp = emp.getSetores();  
        
        if( setores_emp.size() > 1)     
            f = new Fatura(emp.getNome(), emp.getNif(),  LocalDateTime.now(), nif_cliente, descricao, new LogSetor(), valor);    
        else 
            f = new Fatura(emp.getNome(), emp.getNif(),  LocalDateTime.now(), nif_cliente, descricao, new LogSetor(setores_emp.get(0)), valor);    
        
        emp.addFatura(f);
        cont.addFatura(f);
    }

    /** Método que permite obter uma entidade do sistema
    * 
    * @param nif NIF da entidade a obter
    * 
    * @return Entidade
    */
    private Entidade getEntidade(Integer nif) throws NIFNaoRegistadoException{
        Entidade e = this.entidades.get(nif);
        
        if(e == null){
            throw new NIFNaoRegistadoException(nif.toString());
        }
        
        return e;
    }

    /** Método que permite alterar o setor de uma fatura
    * 
    * @param f Fatura
    * @param novo_setor Novo Setor
    */
    public void alteraSetorFatura(Fatura f, String novo_setor) throws  NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
             
        Contribuinte c = (Contribuinte) e; 
        
        c.alteraSetorFatura(f, novo_setor);
    }
    
    /** Método para devolver a lista de setores
    * 
    * @return Lista de setores
    */
    public List<String> getSetores(){
        return this.gestor_setores.getSetores();
    }
    
    
    // Metodo para um contribuinte consultar montantes deduzidos por dependentes
    // Eliminar talvez?
    /*
    public Map<String, ArrayList<Setor>> consultaDeducoes(Contribuinte c){
        HashMap<String, ArrayList<Setor>> res = new HashMap<>();
        
        for(int nif : c.getNIFFamilia()){
            Contribuinte cf = (Contribuinte) entidades.get(nif);
            ArrayList<Setor> setores = cf.getSetores();
            res.put(cf.getNome(), setores);
        }
        return res;
    }
    */
    // ADMIN ONLY

    /** Método que permite ao Admin ver quais são os 10 contribuintes que mais gastam no sistema
    * 
    * @return Lista com os 10 contribuintes
    */
    public List<Contribuinte> top10Contribuintes() throws AdminModeNaoAtivadoException {

        if (!admin_mode) throw new AdminModeNaoAtivadoException("Admin Mode não está ativado.");

        TreeSet<Contribuinte> r = new TreeSet<>((c1,c2) -> {
                                                    if (c2.valorTotalFaturas()!=c1.valorTotalFaturas()) {
                                                        return Double.compare(c2.valorTotalFaturas(), c1.valorTotalFaturas());
                                                    } else {
                                                        return c2.getNif() - c1.getNif();
                                                    }
                                                });
                                                
        this.entidades.values().stream().filter(e -> e instanceof Contribuinte).
                                         map(e -> { Contribuinte c = (Contribuinte) (e.clone());
                                                    return c;
                                                  }).
                                         forEach(c -> r.add(c));

        List<Contribuinte> list = new ArrayList<>();
        Iterator<Contribuinte> it = r.iterator();
        int i=0;
        while (i<10 && it.hasNext()) {
            Contribuinte c = it.next();
            list.add(c);
            i++;
        }

        return list;
    }
    
    /** Método que permite ao Admin ver quais sao as N empresas com a melhor relação entre o nº de faturas emitidas e o montante de deduções fiscais que as despesas registadas representam
    * 
    * @param x número de empresas que prentedemos imprimir
    * 
    * @return Lista com as N empresas
    */
     public List<SimpleEntry<Empresa, Double>> topXEmpresas(int x) throws AdminModeNaoAtivadoException {
        List<SimpleEntry<Empresa, Double>>  resultado = new ArrayList<>();
        double valor_acumulado;
        int i=0;

        if (!admin_mode) throw new AdminModeNaoAtivadoException("Admin Mode não está ativado.");

        Map<Empresa, Double> empresas = new TreeMap<>((e1,e2) ->{
                                                    if (e2.totalFaturado()!=e1.totalFaturado()) {
                                                        return Double.compare(e2.totalFaturado(), e1.totalFaturado());
                                                    } else {
                                                        return e2.getNif() - e1.getNif();
                                                    }
                                                });
        
        this.entidades.values().stream().filter(e -> e instanceof Empresa).
                                         map(e -> (Empresa) e).
                                         forEach(emp -> empresas.put(emp,0.0));

        
        List<Contribuinte> contribuintes = this.entidades.values().stream().
                                                filter(e -> e instanceof Contribuinte).
                                                map(e -> (Contribuinte) e).
                                                collect(Collectors.toList());                              

        for(Contribuinte c : contribuintes)
          for(Fatura f : c.getFaturas()){
              Empresa emitente = (Empresa) this.entidades.get(f.getNifEmitente());
              valor_acumulado = empresas.get(emitente);
              empresas.put(emitente, valor_acumulado + calculaValorDeduzir(f.getValor(), f.getSetor(), 0, emitente, c));
          }

          
        for(Map.Entry<Empresa, Double> entry : empresas.entrySet()){
              resultado.add(new SimpleEntry <Empresa, Double> (entry.getKey().clone(),entry.getValue()));
              i++;
              if(i==x)
                break;
        }

        return resultado; 
    }

    /** Método que adicionar um novo setor de atividade ao sistema
    * 
    * @param nome Nome do setor
    * @param taxa Taxa de dedução associada ao setor
    * @param isDedutivel Boleano que dita a dedutividade do setor
    * @param maxDedutivel Valor máximo dedutível
    *
    */
    public void addSetorAtividadeEconomica(String nome, double taxa, boolean isDedutivel, double maxDedutivel)throws AdminModeNaoAtivadoException {

        if (!admin_mode) throw new AdminModeNaoAtivadoException("Admin Mode não está ativado.");
        
        this.gestor_setores.addSetor(nome, taxa, isDedutivel, maxDedutivel);
    }


    // I/O
    
    /** Método que permite gravar o Estado da aplicação
    * 
    * @param nome_ficheiro Nome do ficheiro onde queremos gravar
    */
    public void saveSystem(String nome_ficheiro) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(nome_ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    /** Método que permite carregar o Estado do sistema previamente guardado
    * 
    * @param nome_ficheiro Nome do ficheiro que queremos carregar
    * 
    * @return Sistema
    */
    public static Sistema loadSystem(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nome_ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Sistema sys = (Sistema) ois.readObject();

        ois.close();

        return sys;
    }


    // Métodos private para usar nas restantes funcoes
   
    /** Método que permite obter deduções de um contribuinte
    * 
    * @param c Contribuinte
    * 
    * @return Mapa com as deduções para cada setor
    */
    private Map<String,Double> calculaDeducoes(Contribuinte c){
        double valor_acumulado;
        String setor;
        Empresa emp;
        
        Map<String, Double> deducoes = new HashMap<>();
        
        for(String nome_setor : this.gestor_setores.getSetores())
            deducoes.put(nome_setor, 0.0);
        
        List<Fatura> faturas = c.getFaturas();        
        for(Fatura f : faturas){
            setor = f.getSetor();
            valor_acumulado = deducoes.get(setor);
            emp = (Empresa) this.entidades.get(f.getNifEmitente());
            deducoes.put(setor, valor_acumulado + calculaValorDeduzir(f.getValor(), setor, valor_acumulado, emp, c));
        }
        
        return deducoes;            
    }
    
    /** Método auxiliar para calcular o valor a deduzir de uma fatura
     * 
    * @param valor Valor da fatura
    * @param setor Setor
    * @param valor_acumulado Valor acumulado
    * @param emp Empresa
    * @param c Contribuinte
    * 
    * @return Valor a deduzir
    */
    private double calculaValorDeduzir(double valor, String setor, double valor_acumulado, Empresa emp, Contribuinte c){
        double taxa=0.0, a_deduzir;
        double max_setor = this.gestor_setores.getMax(setor);
        
        if(valor_acumulado == max_setor) return 0;
        
        taxa += this.gestor_setores.getTaxa(setor);
        
        if(c instanceof ContribuinteFamiliaNumerosa)
            taxa +=  ((ContribuinteFamiliaNumerosa) c).reducaoImposto();
        
        if(emp instanceof EmpresaInterior)
            taxa += ((EmpresaInterior)emp).reducaoImposto();
    
        a_deduzir = valor*taxa;
        
        return valor_acumulado+a_deduzir > max_setor? max_setor-valor_acumulado : a_deduzir;    
    }

}
