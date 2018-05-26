import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
import java.time.LocalDate;

public class Sistema implements Serializable{

    private HashMap<Integer, Entidade> entidades = new HashMap<>(); 
    private int admin_nif; 
    private String admin_password; 
    private boolean admin_mode;
    private int nif_ativo;

    // Registo de Entidades

    // Método para registar um Contribuinte no sistema
    public void registaEntidade (Integer nif, String nome, String email, String password, String rua, String cod_postal, String concelho, Distritos distrito, int n_filhos, List<Integer> nif_familia) throws NIFJaRegistadoException{
        Morada morada = new Morada(rua, cod_postal, concelho, distrito);        
        Entidade nova = new Contribuinte(nif, email, nome,  morada, password, n_filhos,  nif_familia, new GestorSetor(), new ArrayList<Fatura>(), new ArrayList<Fatura>());
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
    public boolean logIn (int nif, String password){
        Entidade entidade = this.entidades.get(nif);
        if (entidade == null) return false;

        if(entidade.getPassword().equals(password)){
            if(nif == this.getAdminNIF()) setAdminMode(true);
            else
                this.nif_ativo = nif; // carrega o nif da entidade ativa
            return true;
        }

        return false;
    }
    
    // Obter faturas do nif ativo
    public List<Fatura> getFaturasContribuinte() throws NIFNaoEDeUmContribuinteException{
        Entidade e = this.entidades.get(nif_ativo);
        if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
        return ((Contribuinte)e).getFaturas();
    }
    
    public List<Fatura> getFaturasPendentes() throws NIFNaoEDeUmContribuinteException{
         Entidade e = this.entidades.get(nif_ativo);
         
         if(!(e instanceof Contribuinte))
            throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
            
         return ((Contribuinte)e).getFaturasPendentes();  
    }
    
    // Obter faturas da empresa ordenadas por valor
    public Set<Fatura> getFaturasEmpByValor() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasValor();
    }
    // Obter faturas da empresa ordenadas por data
    public Set<Fatura> getFaturasEmpByData() throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidasData();
    }
     
    // Obter faturas da empresa ordenadas por data
    public Set<Fatura> faturasEmpresa(LocalDate inicio, LocalDate fim) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasEmitidas(inicio, fim);
    }
    
    // Obter faturas da empresa emitidas para um determinado contribuinte, ordenadas por data
    public Set<Fatura> faturasCliente(LocalDate inicio, LocalDate fim, int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasFromNIF(nif, inicio, fim);
    }    
    
    // Obter faturas da empresa emitidas para um contribuinte
    public Set<Fatura> faturasCliente(int nif) throws EntidadeAtivaNaoEEmpresaException{
        Entidade e = this.entidades.get(nif_ativo);
        
        if(!(e instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        return ((Empresa) e).faturasFromNIF(nif);
    }    
    
    // Obter deducoes do nif ativo
    public List<SimpleEntry<String,Double>> getDeducoesNIFAtivo() throws NIFNaoEDeUmContribuinteException{     
        Entidade e = this.entidades.get(nif_ativo);
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
        
        Contribuinte c = (Contribuinte) e;
        
        return c.valoresDeduzidos();     
    }
    
    // Metodo para consultar o valor deduzido pelos familiares
    public List< SimpleEntry<Integer, List<SimpleEntry<String, Double>>>>  getDeducoesFamilia() throws NIFNaoEDeUmContribuinteException{
        List< SimpleEntry<Integer, List<SimpleEntry<String, Double>>>> deducoes_fam = new ArrayList<>();
        Entidade e = this.entidades.get(nif_ativo);
        Contribuinte familiar;
        
        if(!(e instanceof Contribuinte))
             throw new NIFNaoEDeUmContribuinteException(String.valueOf(nif_ativo));
             
        Contribuinte c = (Contribuinte) e;     
        
        for(int nif : c.getNIFFamilia()){
            familiar = (Contribuinte) this.entidades.get(nif);
            if(familiar != null)
                deducoes_fam.add(new SimpleEntry<Integer, List<SimpleEntry<String, Double>>>(nif, familiar.valoresDeduzidos()));
        }
       
        return deducoes_fam;
    }
    
    
    public void addFaturaSistema(Integer nif_cliente, double valor, String descricao) throws NIFNaoRegistadoException, NIFDaFaturaEEmpresaException, EntidadeAtivaNaoEEmpresaException {
        Entidade cliente =  getEntidade(nif_cliente);
        Entidade empresa_emitente =  getEntidade(this.nif_ativo);
        double bonus=0;
        
        if(cliente instanceof Empresa)
            throw new NIFDaFaturaEEmpresaException(nif_cliente.toString());
            
        if(!(empresa_emitente instanceof Empresa))
            throw new EntidadeAtivaNaoEEmpresaException(String.valueOf(nif_ativo));
        
        Empresa emp = (Empresa) empresa_emitente;
        Contribuinte cont = (Contribuinte) cliente;
        
        if (empresa_emitente instanceof EmpresaInterior)
            bonus += ((EmpresaInterior)empresa_emitente).reducaoImposto();
        if (cliente instanceof ContribuinteFamiliaNumerosa)
            bonus += ((ContribuinteFamiliaNumerosa)cliente).reducaoImposto();
        
        Fatura f = new Fatura(emp.getNome(), emp.getNif(),  LocalDate.now(), nif_cliente, descricao, new LogSetor(), valor, 0, bonus);    
        
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

    public Set<Contribuinte> top10Contribuintes(){
        TreeSet<Contribuinte> r = new TreeSet<>((c1,c2) -> Double.compare(c1.totalDeduzido(), c2.totalDeduzido()));

        this.entidades.values().stream().filter(e -> e instanceof Contribuinte).
                                         map(e -> { Contribuinte c = (Contribuinte) (e.clone());
                                                    return c;
                                                  }).
                                         forEach(c -> r.add(c));


        return r; // FIXME: 12/05/2018 restringir os 10 e a ser so admin
    }

    public Set<Empresa> topXEmpresas(int x){
        TreeSet<Empresa> r = new TreeSet<>((e1,e2) -> Double.compare(e1.totalFaturado(), e2.totalFaturado())); 
        
        this.entidades.values().stream().filter(e -> e instanceof Empresa).
                                         map(e -> { Empresa em = (Empresa) (e.clone());
                                                    return em;
                                                  }).
                                         forEach(em -> r.add(em));

        return r; // FIXME: 12/05/2018 restringir a x e a ser so admin
    }


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




    // Getters & Setters

    public void setAdminMode(boolean mode) {
        this.admin_mode = mode;
    }

    public boolean getAdminMode() {
        return this.admin_mode;
    }


    public void setAdminNIF(int nif){
        this.admin_nif = nif;
    }

    public int getAdminNIF(){
        return this.admin_nif;
    }

    public void setAdminPassword(String pw){
        this.admin_password = pw;
    }

    public String getAdminPassword(){
        return this.admin_password;
    }

}
