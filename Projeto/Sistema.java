import java.util.HashMap;

public class Sistema {

    HashMap<Integer, Entidade> entidades = new HashMap<>(); // FIXME: 01/05/2018
    String admin; // FIXME: 01/05/2018
    String admin_password; // FIXME: 01/05/2018
    
    boolean registaEntidade(Entidade e){
        Entidade aux = entidades.putIfAbsent(e.getNif(), e);
        return (aux == null)? true : false;
    }
    
    boolean checkDadosLogIn(int nif, String password){
        Entidade aux = entidades.get(nif);
        if(aux == null) 
            return false;
        return aux.getPassword().equals(password);            
    }
}
