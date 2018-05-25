import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class Controller{
    
    private Sistema estado;
    private Menu menus;
    
    // Metodos de instancia
    public void setSistema(Sistema sistema){ this.estado = sistema;}
    
    public void setMenu(Menu menu) {this.menus = menu;}
    
    // Método para ler uma opcao do terminal
    public int readOp(){
        int op;
        Scanner sc = new Scanner(System.in);
        System.out.println("Operação a realizar: ");
        op = sc.nextInt();
        sc.close();
        return op;
    }
    
    // Método para imprimir opcoes
    public void showOps(String[] ops){
        for(String op : ops)
            System.out.println(ops);
    }
    
    // Método para adicionar uma fatura ao sistema
    public void criaFatura(){
        Scanner sc = new Scanner(System.in);
        Fatura f;

        System.out.println("NIF Cliente: ");
        int nif_cliente = sc.nextInt();

        System.out.println("Valor: ");
        Double valor = sc.nextDouble();

        System.out.println("Descrição: ");
        String descricao = sc.next();
        
        
        try{
            this.estado.addFaturaSistema(nif_cliente, valor, descricao);
        }
        catch(NIFNaoRegistadoException e){
            System.out.println(e.getMessage());
        }
        catch(NIFDaFaturaEEmpresaException e){
            System.out.println(e.getMessage());
        }
        catch(EntidadeAtivaNaoEEmpresaException e){
            System.out.println(e.getMessage());
        }
    }
    
    // Método para imprimir faturas
    public void printFaturas(){
        List <Fatura> faturas =  this.estado.getFaturasNIFAtivo();
        Fatura f = escolheFatura(faturas);
        if(f != null) System.out.println(f.toString());
    }
    
    // Metodo para fazer imprimir os pares setor - valor deduzido
    public void printParesSetorDeducao(List<SimpleEntry<String, Double>> valores_deduzidos){
       Double total_deduzido=0.0;
       for(Map.Entry<String, Double> deducao : valores_deduzidos){
            System.out.println(deducao.getKey() + " - " + deducao.getValue().toString());
            total_deduzido += deducao.getValue();
        }
        System.out.println("Valor total - " + total_deduzido); 
    }
    
    // Metodo para fazer display dos valores deduzidos pelo contribuinte até então
    public void printDeducoes(){      
        try{
            List<SimpleEntry<String,Double>> valores_deduzidos = this.estado.getDeducoesNIFAtivo();
            printParesSetorDeducao(valores_deduzidos);          
        }
        catch (NIFNaoEDeUmContribuinteException e){
            System.out.println("NIF: " + e.getMessage() + "não é de um contribuinte.");
        }        
    }
    
    // Metodo para consultar o valor deduzido pelos familiares
    public void printDeducoesFamilia(){
        List< SimpleEntry<Integer, List<SimpleEntry<String, Double>>>> deducoes_fam;
        try{
            deducoes_fam = this.estado.getDeducoesFamilia();
            if(deducoes_fam.size() == 0)
            System.out.println("Não possui elementos no seu agregado familiar ou estes ainda não se encontram registados.");
            
            else    
                for(SimpleEntry<Integer, List<SimpleEntry<String, Double>>> deducoes : deducoes_fam){
                    System.out.println("Nif: " + deducoes.getKey());
                    printParesSetorDeducao(deducoes.getValue());
                }
        }
         catch (NIFNaoEDeUmContribuinteException e){
            System.out.println("NIF: " + e.getMessage() + "não é de um contribuinte.");
        }
        
       
    }
    
    // Metodo para atribuir setor a uma fatura mode 0 atribuir setor, mode 1 mudar setor
    public void atribuiSetor(int mode){
        List<Fatura> faturas;
        Fatura a_alterar;
        String setor;
        
        try{
            if(mode == 0) faturas = this.estado.getFaturasPendentes();
            else faturas = this.estado.getFaturasNIFAtivo();
            
            if(faturas.size() == 0)
                System.out.println("Não tem faturas nesta categoria");
            
            else{   
            
                do{
                    a_alterar = escolheFatura(faturas);
                    setor = escolheSetorFatura();
            
                    if(a_alterar == null)
                        break;
                }while(setor == null);
        
                if(a_alterar != null){
                    this.estado.alteraSetorFatura(a_alterar, setor);
                 
                }
            }
        }
        catch (NIFNaoEDeUmContribuinteException e){
            System.out.println("NIF: " + e.getMessage() + "não é de um contribuinte.");
        }
        
        
    }
    // Método para escolher uma fatura
    public Fatura escolheFatura(List<Fatura> faturas){
        Scanner sc = new Scanner(System.in);
        Fatura f_escolhida = null;
        int i = 1, opcao;
        
        System.out.println("Escolha uma Fatura:");
        for(Fatura f : faturas)
            System.out.printf("%d - %s %s %f\n", i++, f.getData().toString(), f.getEmpresa(), f.getValor());
       
        System.out.printf("\n\n0 - Retroceder");    
        
        do{
            opcao = sc.nextInt();              
        }
        while(opcao < 0 || opcao > faturas.size());
        
        if(opcao != 0)
            f_escolhida = faturas.get(opcao-1);
        
        sc.close();
        return f_escolhida;        
    }
    
    // Escolhe o setor a atribuir a uma fatura
    public static String escolheSetorFatura(){
        Scanner sc = new Scanner(System.in);
        GestorSetor aux = new GestorSetor();
        List <Setor> setores = aux.getSetores();
        String setor_escolhido = null;
        int i = 1, opcao;
        
        System.out.println("Escolha um Setor de Atividade Económica:");
        for(Setor setor : setores)
            System.out.printf("%d - %s\n", i++, setor.getNome());
       
        System.out.printf("\n\n0 - Retroceder");    
        
        do{
            opcao = sc.nextInt();              
        }
        while(opcao < 0 || opcao > setores.size());
        
        if(opcao != 0)
            setor_escolhido = setores.get(opcao-1).getNome();
        
        sc.close();
        return setor_escolhido;    
    }
    
    // Método para escolher os setores de atividade economica para uma empresa
    public List<String> escolheSetores(){
        Scanner sc = new Scanner(System.in);
        List<String> setores_escolhidos = new ArrayList<>();
        GestorSetor aux = new GestorSetor();
        List <Setor> setores = aux.getSetores();
        String setor_esc;
        int n_setores, opcao, n_escolhidos=0;
        
        System.out.println("Número de setores económicos em que a empresa participa:"); 
        do{
            n_setores = sc.nextInt();
        }while(n_setores < 1 || n_setores > setores.size()); 
        
        while(n_escolhidos != n_setores){
            int i=1;       
            System.out.println("Escolha um Setor de Atividade Económica:");
            for(Setor setor : setores)
                System.out.printf("%d - %s\n", i++, setor.getNome()); 
        
            do{
                opcao = sc.nextInt();              
            }while(opcao < 0 || opcao > setores.size());
            
            setor_esc = setores.get(opcao-1).getNome();
            if(!setores_escolhidos.contains(setor_esc)){
                setores_escolhidos.add(setor_esc);
                n_escolhidos++;
            }
            else
                System.out.println("Já escolheu esse setor. Por favor escolha outro.");
        }
        
        sc.close();
        return setores_escolhidos;    
    }
        
    // Método para escolher um distrito
    public Distritos escolheDistrito(){
        Scanner sc = new Scanner(System.in);
        Distritos distrito;
        Distritos[] lista_distritos = Distritos.values();
        int n_distrito;
        
        System.out.println("Distrito:");
        for(Distritos d : lista_distritos)
            System.out.printf("%d - %s\n", d.ordinal()+1, d.getNome());
            
        do{
            n_distrito = sc.nextInt();            
        }
        while(n_distrito < 1 || n_distrito > lista_distritos.length);
        
        distrito = lista_distritos[n_distrito-1];
        
        sc.close();
        return distrito;
    }
    
    // Método para fazer log in
    public void loginEntidade(){
        Scanner sc = new Scanner(System.in);
        boolean dadosOk = false;
        Entidade e = null;
        int nif;
        String pw;

        do{
            System.out.println("NIF: ");
            nif = sc.nextInt(); 

            System.out.println("Password: ");
            pw = sc.next();
            
            dadosOk = this.estado.logIn(nif, pw);
            
            if(!dadosOk)
                System.out.println("NIF ou password incorretos. Tente novamente.");
                
        }while(!dadosOk);
   
        sc.close();
    }
    
    // Metodo para registar um contribuinte
    
    public void registaContribuinte(){ 
        Scanner sc = new Scanner(System.in);
        int dep_familia = 0, n_distrito, nif_filho, n_nifs_inseridos=0;
        ArrayList<Integer> nif_familia = new ArrayList<>();

        System.out.println("NIF: ");
        int nif = sc.nextInt();

        System.out.println("Nome: ");
        String nome = sc.next();

        System.out.println("E-mail: ");
        String email = sc.next();

        System.out.println("Password: ");
        String password = sc.next();

        System.out.println("Rua: ");
        String rua = sc.next();

        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal = sc.next();
        
        System.out.println("Concelho:");
        String concelho = sc.next();

        Distritos distrito = escolheDistrito();
        
        System.out.println("Número de filhos:");
        int n_filhos = sc.nextInt();
        
        System.out.println("Insira o(s) seu(s) NIF(s)");
        while(n_nifs_inseridos != n_filhos){
            nif_filho = sc.nextInt();
            if(!nif_familia.contains(nif_filho)){
                nif_familia.add(nif_filho);
                n_nifs_inseridos++;
            }
            else 
                System.out.println("Já inseriu esse NIF");
        }

        
        try{
            this.estado.registaEntidade(nif, nome, email, password, rua, cod_postal, concelho, distrito, n_filhos, nif_familia);
        }
        catch (NIFJaRegistadoException e){
            System.out.println("NIF: " + e.getMessage() + "já se encontra registado");
        }
        
        System.out.println("Registo efetuado com sucesso!");
        sc.close();
    } 
    
    // Metodo para registar uma empresa
    public void registaEmpresa(){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        ConcelhosInterior concelhos_int = new ConcelhosInterior();

        System.out.println("NIF: ");
        int nif = sc.nextInt();

        System.out.println("Nome: ");
        String nome = sc.next();

        System.out.println("E-mail: ");
        String email = sc.next();

        System.out.println("Password: ");
        String password = sc.next();

        System.out.println("Rua: ");
        String rua = sc.next();
        
        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal = sc.next();
        
        System.out.println("Concelho:");
        String concelho = sc.next();
        
        Distritos distrito = escolheDistrito();
        
        List<String> setores = escolheSetores();
   
        try{
            this.estado.registaEntidade(nif, nome, email, password, rua, cod_postal, concelho, distrito, setores);
        }
        catch (ConcelhoNaoEInteriorException e){
            System.out.println("Empresas de " + e.getMessage() + " não tem direito a bónus fiscal");
        }
        catch (NIFJaRegistadoException e){
            System.out.println("NIF: " + e.getMessage() + "já se encontra registado");
        }
        
        System.out.println("Registo efetuado com sucesso!");

    }
    // Metodo para  executar main menu
    public void start(){
        int opcao;  
        String[] menu = this.menus.getMainMenu();
     
        do{
            showOps(menu);
            opcao = readOp();          
            switch(opcao){
                case 0: break;
                case 1: loginEntidade(); break;
                case 2: registaContribuinte(); break;
                case 3: registaEmpresa(); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }
    
    // Metodo para executar menu dos contribuintes
    
    public void execMenuContribuinte(){
        int opcao;  
        String[] menu = this.menus.getMenuContribuinte();
        do{   
            showOps(menu);
            opcao = readOp();            
            switch(opcao){
                case 0: break;
                case 1: printFaturas(); break;
                case 2: printDeducoes(); break;
                case 3: printDeducoesFamilia(); break;
                case 4: atribuiSetor(0); break;
                case 5: atribuiSetor(1); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }
    
    // Metodo para executar meno das empresas
    /*
    public void execMenuEmpresa(){
        int opcao;  
        String[] menu = this.menus.getMenuEmpresa();
        do{   
            showOps(menu);
            opcao = readOp();            
            switch(opcao){
                case 0: break;
                case 1: criaFatura(); break;
                case 2: printFaturas(); break;
                case 3: printFaturas(); break;
                case 4: printFaturas(); break;
                case 5: printFaturas(); break;
                case 6: totalFaturado(); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }*/
}