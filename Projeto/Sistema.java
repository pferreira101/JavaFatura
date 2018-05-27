import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class Sistema implements Serializable{

    private HashMap<Integer, Entidade> entidades = new HashMap<>();
    private GestorSetor gestor_setores = new GestorSetor();
    private int admin_nif=20172018;
    private String admin_password="miei";
    private boolean admin_mode;
    private int nif_ativo;
    

    // Registo de Entidades

    // Método para registar um Contribuinte no sistema
    public void registaEntidade (Integer nif, String nome, String email, String password, String rua, String cod_postal, String concelho, Distritos distrito, int n_filhos, List<Integer> nif_familia) throws NIFJaRegistadoException{
        Morada morada = new Morada(rua, cod_postal, concelho, distrito);        
        Entidade nova = new Contribuinte(nif, email, nome,  morada, password, n_filhos,  nif_familia, new ArrayList<Fatura>(), new ArrayList<Fatura>());
        if(this.entidades.putIfAbsent(nova.getNif(),nova) != null)
            throw new NIFJaRegistadoException(nif.toString());
 
    }
    
    // Método para registar uma Empresa no sistema
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
    
    
    // Log in 
    // retorna -1 se log in falhou
    // retorna 0 se foi um contruibuinte a fazer log in
    // retorna 1 se foi empresa
    // retorna 2 se foi o admin
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
    
    // Obter faturas do nif ativo
    public List<Fatura> faturasContribuinte() throws NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
        return ((Contribuinte)e).getFaturas();
    }
    
    public List<Fatura> faturasPendentes() throws NIFNaoEDeUmContribuinteException{
         Entidade e = this.entidades.get(nif_ativo);
         
         if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
         return ((Contribuinte)e).getFaturasPendentes();  
    }
    
    // Obter faturas da empresa ordenadas por valor
    public Set<Fatura> faturasEmpByValor() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByValor();
    }
    // Obter faturas da empresa ordenadas por valor
    public Set<Fatura> faturasEmpByValor(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByValor(inicio, fim);
    }
    
    // Obter faturas da empresa ordenadas por data
    public Set<Fatura> faturasEmpByData() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByData();
    }
     
    // Obter faturas da empresa ordenadas por data
    public Set<Fatura> faturasEmpByData(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasByData(inicio, fim);
    }
    
    // Obter faturas da empresa emitidas para um contribuinte
    public Set<Fatura> faturasEmpFromCliente(int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasFromNIF(nif);
    }  
    
    // Obter faturas da empresa emitidas para um determinado contribuinte, ordenadas por data
    public Set<Fatura> faturasEmpFromCliente(LocalDate inicio, LocalDate fim, int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasFromNIF(nif, inicio, fim);
    }    
      

    // Metodo para obter valor faturado por uma empresa entre determinada data
    public double valorFaturadoEmpresa(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa)e).totalFaturado(inicio, fim);    
    }


    
    

    // Metodo para calcular os valores deduzidos pela entidade ativa
    public Map<String, Double> getDeducoesNifAtivo() throws NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
        
        return calculaDeducoes((Contribuinte) e);     
    }
    
    
    // Metodo para consultar o valor deduzido pelos familiares
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

    private Entidade getEntidade(Integer nif) throws NIFNaoRegistadoException{
        Entidade e = this.entidades.get(nif);
        
        if(e == null){
            throw new NIFNaoRegistadoException(nif.toString());
        }
        
        return e;
    }


    public void alteraSetorFatura(Fatura f, String novo_setor) throws  NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
             
        Contribuinte c = (Contribuinte) e; 
        
        c.alteraSetorFatura(f, novo_setor);
    }

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

    public ArrayList<Contribuinte> top10Contribuintes() throws AdminModeNaoAtivadoException {

        if (!admin_mode) throw new AdminModeNaoAtivadoException("Admin Mode não está ativado.");

        TreeSet<Contribuinte> r = new TreeSet<>((c1,c2) -> Double.compare(c2.valorTotalFaturas(), c1.valorTotalFaturas()));

        this.entidades.values().stream().filter(e -> e instanceof Contribuinte).
                                         map(e -> { Contribuinte c = (Contribuinte) (e.clone());
                                                    return c;
                                                  }).
                                         forEach(c -> r.add(c));

        ArrayList<Contribuinte> list = new ArrayList<>();
        Iterator<Contribuinte> it = r.iterator();
        int i=0;
        while (i<10 && it.hasNext()) {
            Contribuinte c = it.next();
            list.add(c);
            i++;
        }

        return list;
    }

    /*public Set<Empresa> topXEmpresas(int x){
        TreeSet<Empresa> r = new TreeSet<>((e1,e2) -> Double.compare(e1.totalFaturado(), e2.totalFaturado())); 
        
        this.entidades.values().stream().filter(e -> e instanceof Empresa).
                                         map(e -> { Empresa em = (Empresa) (e.clone());
                                                    return em;
                                                  }).
                                         forEach(em -> r.add(em));

        return r; // FIXME: 12/05/2018 restringir a x e a ser so admin
    }*/


    // I/O

    public void saveSystem(String nome_ficheiro) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(nome_ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    public static Sistema loadSystem(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nome_ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Sistema sys = (Sistema) ois.readObject();

        ois.close();

        return sys;
    }


    // Métodos private para usar nas restantes funcoes
    
    // Obter deducoes de um contribuinte
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
    
    
    // Metodo auxiliar para calcular o valor a deduzir de uma fatura
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
