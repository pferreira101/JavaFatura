import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller{
    
    private Sistema estado;
    
    // Contrutor
    public Controller(Sistema estado){
        this.estado = estado;
    }
    
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
            System.out.println(op);
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
            System.out.println("O NIF: "+ e.getMessage() + " não se encontra registado");
        }
        catch(NIFDaFaturaEEmpresaException e){
            System.out.println("Não pode emitir faturas para o seguinte NIF: " + e.getMessage() +" pois corresponde a uma empresa");
        }
        catch(EntidadeAtivaNaoEEmpresaException e){
            System.out.println(e.getMessage());
        }
    }
    
    // Método para imprimir faturas
    public void printFaturasContribuinte(){
        
        try{
            List <Fatura> faturas =  this.estado.faturasContribuinte();
            printOpcoesFatura(faturas, 0, 1);
        }
        catch(NIFNaoEDeUmContribuinteException e){
            System.out.println(e.getMessage());
        }
    }
       
    // Metodo converter set<Fatura> em List<Fatura>
    private static List<Fatura> setParaList(Set<Fatura> faturas){
        return new ArrayList(faturas);
    }
    
    // Metodo para imprimir faturas empresa por data
    // tipo 0 - ordenadas por data
    // tipo 1 - ordenadas por valor
    // tipo 2 - todas de um dado nif
    // mode 0 - todas as faturas
    // mode 1 - apenas entre um certo intervalo de tempo
    public void printFaturasEmpresa(int tipo, int modo){
        Scanner sc = new Scanner(System.in);
        List<Fatura> faturas=null;
        String inicio, fim;
        LocalDate data_inicio=null, data_fim=null;
        int nif=-1;
        
        if(tipo == 2){
            System.out.println("NIF a consultar:");
            nif = sc.nextInt();
        }
        
        
        if (modo==1){
            System.out.println("Introduza a data de início do intervalo a consultar (AAAA-MM-DD): ");
            inicio = sc.next();
            data_inicio = LocalDate.parse(inicio);
            System.out.println("Introduza a data de fim do intervalo a consultar (AAAA-MM-DD): ");
            fim = sc.next();
            data_fim = LocalDate.parse(fim);
        }
        
        try{
            switch(tipo){
                    case 0: if(modo == 0) faturas = setParaList(this.estado.faturasEmpByData());
                            else faturas = setParaList(this.estado.faturasEmpByData(data_inicio, data_fim));
                            break;
                    case 1: if(modo == 0) faturas = setParaList(this.estado.faturasEmpByValor());
                            else faturas = setParaList(this.estado.faturasEmpByValor(data_inicio, data_fim));
                            break;  
                    case 2: if(modo == 0) faturas = setParaList(this.estado.faturasEmpFromCliente(nif));
                            else faturas = setParaList(this.estado.faturasEmpFromCliente(data_inicio, data_fim, nif));
                            break;
            }
        }
        catch (EntidadeAtivaNaoEEmpresaException e){
            System.out.println(e.getMessage());
        }
        printOpcoesFatura(faturas, 1, 1);
        sc.close();
    }
    
    // Metodo para obter valor faturado por uma empresa entre determinada data
    public void valorFaturadoEmpresa(){
        Double valor_faturado;
        int sair;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduza a data de início do intervalo a consultar (AAAA-MM-DD): ");
        String inicio = sc.next();
        LocalDate data_inicio = LocalDate.parse(inicio);
        
        System.out.println("Introduza a data de fim do intervalo a consultar (AAAA-MM-DD): ");
        String fim = sc.next();
        LocalDate data_fim = LocalDate.parse(fim);

        try{    
            valor_faturado = this.estado.valorFaturadoEmpresa(data_inicio, data_fim);
            System.out.println("Valor faturado : " + valor_faturado.toString());
            System.out.println("\n0 - Regressar menu anterior");
            do{
               sair = sc.nextInt();
            }while(sair != 0);
        }
        catch(EntidadeAtivaNaoEEmpresaException e){
            System.out.println(e.getMessage());
        }
    } 
    
    // Metodo para fazer imprimir os pares setor - valor deduzido
    public void printParesSetorDeducao(Map<String, Double> valores_deduzidos){
       Scanner sc = new Scanner(System.in);
       int sair;
       Double total_deduzido=0.0;
       for(Map.Entry<String, Double> deducao : valores_deduzidos.entrySet()){
            System.out.println(deducao.getKey() + " - " + deducao.getValue().toString());
            total_deduzido += deducao.getValue();
       }
       System.out.println("Valor total - " + total_deduzido); 
       System.out.println("\n0 - Voltar ao menu anterior");
       do{
           sair = sc.nextInt();
       }while(sair!=0);

       sc.close();
    }
    
    // Metodo para fazer display dos valores deduzidos pelo contribuinte até então
    public void printDeducoes(){      
        try{
            Map<String,Double> valores_deduzidos = this.estado.getDeducoesNifAtivo();
            printParesSetorDeducao(valores_deduzidos);
        }
        catch (NIFNaoEDeUmContribuinteException e){
            System.out.println("NIF: " + e.getMessage() + "não é de um contribuinte.");
        }        
    }
  
    
    // Metodo para consultar o valor deduzido pelos familiares
    public void printDeducoesFamilia(){
        List< SimpleEntry<Integer, Map<String, Double>>> deducoes_fam;
        try{
            deducoes_fam = this.estado.getDeducoesFamilia();
            if(deducoes_fam.size() == 0)
            System.out.println("Não possui elementos no seu agregado familiar ou estes ainda não se encontram registados.");
            
            else    
                for(SimpleEntry<Integer, Map<String, Double>> deducoes : deducoes_fam){
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
        Scanner sc = new Scanner(System.in);
        List<Fatura> faturas=null;
        Fatura a_alterar;
        String setor;
        int sair;
        
        try{
            if(mode == 0) faturas = this.estado.faturasPendentes();
            
            do{
                if(mode == 1) faturas = this.estado.faturasContribuinte(); //ao alterar setor nao deve desaparecer das opcoes
                
                if(faturas.size() == 0){
                    System.out.println("Não tem faturas nesta categoria");
                    System.out.println("\n0 - Regressar menu anterior");
                    a_alterar = null;
                    do{
                        sair = sc.nextInt();
                    }while(sair != 0);
                }
            
                else{
                    a_alterar = printOpcoesFatura(faturas, 0, 0);
                            
                    if(a_alterar != null){
                        setor = escolheSetorFatura();
                        if(setor!=null) {
                            this.estado.alteraSetorFatura(a_alterar, setor);
                            if(mode==0)faturas.remove(a_alterar);
                        }
                    }
                }
                if(a_alterar!=null) printSeparador();        
            }while(a_alterar != null);
      
        }
        catch (NIFNaoEDeUmContribuinteException e){
            System.out.println("NIF: " + e.getMessage() + "não é de um contribuinte.");
        }  
        sc.close();
    }

    public void printTopContribuintes() {
        try {
            ArrayList<Contribuinte> list = estado.top10Contribuintes();
            int size = list.size();
            if (size==0) {
                System.out.println("Não existem contribuintes no sistema.");
                return;
            }
            else if (list.size()<10) System.out.println("Existem apenas "+list.size()+" contribuintes.\n");
            System.out.println("Valor total das faturas:\n");
            for (int i=0;i<list.size();i++){
                Contribuinte c = list.get(i);
                System.out.println((i+1)+"º: "+c.valorTotalFaturas()+"€");
                System.out.println(c.toString());
            }
        }
        catch (AdminModeNaoAtivadoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printTopEmpresas() {
        return;
    }

    // Método para escolher uma fatura
    // user 0 - display faturas para contribuinte ver (data empresa valor)
    // user 1 - display faturas para empresa ver (data nif_cliente valor)
    // modo 0 - termina execucao mal acabe de imprimir a fatura retorna o valor 
    // modo 1 - permite consultar varias faturas e aguarda que utilizador deseje escolher outra
    // 
    public Fatura printOpcoesFatura(List<Fatura> faturas, int user, int mode){
        Scanner sc = new Scanner(System.in);
        Fatura f_escolhida = null;
        int i, opcao, recuar;
        
        if(faturas.size() == 0){
            System.out.println("Não tem faturas nesta categoria");
            System.out.println("\n0 - Regressar menu anterior");       
            do{
               recuar = sc.nextInt();
            }while(recuar != 0);
            
            return null;
        }
        
        do{
            i = 1;
            System.out.println("Escolha uma Fatura:");
            
            for(Fatura f : faturas)
                if(user == 0)
                    System.out.printf("%d - Data: %s Empresa: %s Valor: %f\n", i++, f.getData().toString(), f.getEmpresa(), f.getValor());
                else
                    System.out.printf("%d - Data: %s Nif Cliente: %d Valor%f\n", i++, f.getData().toString(), f.getNifCliente(), f.getValor());
                
            System.out.println("\n\n0 - Retroceder"); 
               
            opcao = sc.nextInt(); 
            
            if(opcao != 0 && opcao <= faturas.size()){
                f_escolhida = faturas.get(opcao-1);
                printSeparador();
                System.out.println(f_escolhida.toString());
                if(mode == 1){
                    System.out.println("\n0 - Retroceder");
                    do{
                        recuar = sc.nextInt();
                    }while(recuar != 0);
                }               
            }
            
            if(opcao != 0 && mode != 0) printSeparador();
        } 
        while(opcao != 0 && mode != 0);
        
        sc.close();        
        return f_escolhida;
    }

    // Escolhe o setor a atribuir a uma fatura
    public String escolheSetorFatura(){
        Scanner sc = new Scanner(System.in);
        List <String> setores = this.estado.getSetores();
        String setor_escolhido = null;
        int i = 1, opcao;
        
        System.out.println("Escolha um Setor de Atividade Económica:");
        for(String nome_setor : setores)
            System.out.printf("%d - %s\n", i++, nome_setor);
       
        System.out.println("\n\n0 - Retroceder");    
        
        do{
            opcao = sc.nextInt();              
        }
        while(opcao < 0 || opcao > setores.size());
        
        if(opcao != 0)
            setor_escolhido = setores.get(opcao-1);
        
        sc.close();
        return setor_escolhido;    
    }
    
    // Método para escolher os setores de atividade economica para uma empresa
    public List<String> escolheSetores(){
        Scanner sc = new Scanner(System.in);
        List<String> setores_escolhidos = new ArrayList<>();
        GestorSetor aux = new GestorSetor();
        List <String> setores = this.estado.getSetores();
        String setor_esc;
        int n_setores, opcao, n_escolhidos=0, i=1;
        
        System.out.println("Número de setores económicos em que a empresa participa:"); 
        do{
            n_setores = sc.nextInt();
        }while(n_setores < 1 || n_setores > setores.size()); 
               
        System.out.println("Escolha um Setor de Atividade Económica:");
        for(String nome_setor : setores)
             System.out.printf("%d - %s\n", i++, nome_setor); 
             
        while(n_escolhidos != n_setores){        
            do{
                opcao = sc.nextInt();              
            }while(opcao < 1 || opcao > setores.size());
            
            setor_esc = setores.get(opcao-1);
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
        int tipoEntidade = -1;
        Entidade e = null;
        int nif, sair=0;
        String pw;

        do{
            System.out.println("NIF: ");
            nif = sc.nextInt();

            System.out.println("Password: ");
            pw = sc.next();
            
            tipoEntidade = this.estado.logIn(nif, pw);
            
            if(tipoEntidade == -1){
                System.out.println("NIF ou password incorretos. Tente novamente.\n\n1 - Tentar novamente\n0 - Cancelar operação");
                do{
                    sair = sc.nextInt();
                }while(sair != 0 && sair != 1);
            }
                
        }while(tipoEntidade == -1 && sair != 0);
        
        sc.close();
        
        if(tipoEntidade == 0) execMenuContribuinte();
        if(tipoEntidade == 1) execMenuEmpresa();
        if(tipoEntidade == 2) execMenuAdmin();

    }
    
    // Metodo para registar um contribuinte
    
    public void registaContribuinte(){ 
        Scanner sc = new Scanner(System.in);
        int dep_familia = 0, n_distrito, nif_filho, n_nifs_inseridos=0;
        ArrayList<Integer> nif_familia = new ArrayList<>();

        System.out.println("NIF: ");
        int nif = -1;
        do {
            nif = sc.nextInt();
        } while(nif==-1);

        System.out.println("Nome: ");
        String nome;
        do {
           nome = sc.nextLine();
        } while (nome.equals(""));


        System.out.println("E-mail: ");
        String email;
        do {
            email = sc.nextLine();
        } while (email.equals(""));

        System.out.println("Password: ");
        String password;
        do {
            password = sc.nextLine();
        } while (password.equals(""));

        System.out.println("Rua: ");
        String rua;
        do{
            rua = sc.nextLine();
        } while (rua.equals(""));

        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal;
        do {
            cod_postal = sc.nextLine();
        } while (cod_postal.equals(""));

        Distritos distrito = escolheDistrito();

        System.out.println("Concelho:");
        String concelho;
        do {
            concelho = sc.nextLine();
        } while (concelho.equals(""));
        
        System.out.println("Número de filhos:");
        int n_filhos = sc.nextInt();
        
        if (n_filhos!=0) System.out.println("Insira o(s) seu(s) NIF(s)");
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
            System.out.println("Registo efetuado com sucesso!");
        }
        catch (NIFJaRegistadoException e){
            System.out.println("NIF: " + e.getMessage() + " já se encontra registado");
        }
        
        sc.close();
    } 
    
    // Metodo para registar uma empresa
    public void registaEmpresa(){ // FIXME: 16/05/2018 verificar se todos os inputs sao validos porque senao crash
        Scanner sc = new Scanner(System.in);
        ConcelhosInterior concelhos_int = new ConcelhosInterior();

        System.out.println("NIF: ");
        int nif = -1;
        do {
            nif = sc.nextInt();
        } while(nif==-1);

        System.out.println("Nome: ");
        String nome;
        do {
            nome = sc.nextLine();
        } while (nome.equals(""));


        System.out.println("E-mail: ");
        String email;
        do {
            email = sc.nextLine();
        } while (email.equals(""));

        System.out.println("Password: ");
        String password;
        do {
            password = sc.nextLine();
        } while (password.equals(""));

        System.out.println("Rua: ");
        String rua;
        do{
            rua = sc.nextLine();
        } while (rua.equals(""));

        System.out.println("Código Postal (XXXX-XXX): ");
        String cod_postal;
        do {
            cod_postal = sc.nextLine();
        } while (cod_postal.equals(""));

        Distritos distrito = escolheDistrito();

        System.out.println("Concelho:");
        String concelho;
        do {
            concelho = sc.nextLine();
        } while (concelho.equals(""));
        
        List<String> setores = escolheSetores();
   
        try{
            this.estado.registaEntidade(nif, nome, email, password, rua, cod_postal, concelho, distrito, setores);
            System.out.println("Registo efetuado com sucesso!");
        }
        catch (ConcelhoNaoEInteriorException e){
            System.out.println("Empresas de " + e.getMessage() + " não tem direito a bónus fiscal");
        }
        catch (NIFJaRegistadoException e){
            System.out.println("NIF: " + e.getMessage() + "já se encontra registado");
        }
                
    }
    // Metodo para  executar main menu
    public void start(){
        int opcao;  
        String[] menu = Menu.getMainMenu();
     
        do{
            printSeparador();    
            showOps(menu);
            opcao = readOp();
            printSeparador();
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
        String[] menu = Menu.getMenuContribuinte();
        do{ 
            printSeparador();
            showOps(menu);
            opcao = readOp();
            if(opcao!=0)printSeparador();
            switch(opcao){
                case 0: break;
                case 1: printFaturasContribuinte(); break;
                case 2: printDeducoes(); break;
                case 3: printDeducoesFamilia(); break;
                case 4: atribuiSetor(0); break;
                case 5: atribuiSetor(1); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }
    
    // Metodo para executar meno das empresas
        // tipo 0 - ordenadas por data
    // tipo 1 - ordenadas por valor
    // tipo 2 - todas de um dado nif
    // mode 0 - todas as faturas
    // mode 1 - apenas entre um certo intervalo de tempo
    public void execMenuEmpresa(){
        int opcao;  
        String[] menu = Menu.getMenuEmpresa();
        do{ 
            printSeparador();
            showOps(menu);
            opcao = readOp(); 
            if(opcao!=0) printSeparador();
            switch(opcao){
                case 0: break;
                case 1: criaFatura(); break;
                case 2: printFaturasEmpresa(0,0); break;
                case 3: printFaturasEmpresa(1,0); break;
                case 4: printFaturasEmpresa(2,0); break;
                case 5: printFaturasEmpresa(0,1); break;
                case 6: printFaturasEmpresa(1,1); break;
                case 7: printFaturasEmpresa(2,1); break;
                case 8: valorFaturadoEmpresa(); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }

    public void execMenuAdmin(){
        int opcao;
        String[] menu = Menu.getMenuAdmin();
        do{
            printSeparador();
            showOps(menu);
            opcao = readOp();
            if(opcao!=0) printSeparador();
            switch(opcao){
                case 0: break;
                case 1: printTopContribuintes(); break;
                case 2: printTopEmpresas(); break;
                default: System.out.println("Insira uma opção correta");
            }
        }while(opcao != 0);
    }
    
    public static void main(String args[]){
        Sistema sistema = new Sistema();
        Menu menu = new Menu();

        try {
            sistema = Sistema.loadSystem("data");
        }
        catch (IOException e){
            System.out.println("Não conseguiu ler ficheiro de dados");
        }
        catch (ClassNotFoundException e){
            System.out.println("Não conseguiu ler ficheiro de dados 2"); 
        } 
        
        Controller controlador = new Controller(sistema);
        
        controlador.start();
        
        try {
            sistema.saveSystem("data");
        }
        catch (IOException e){
            System.out.println("Erro ao gravar dados");
        }  
        System.out.println("Obrigado!");
        
        
    }
    
    private static void printSeparador(){
           System.out.println("****************JavaFatura****************");
    }
}