import java.util.HashMap;

public class Sistema {

    private HashMap<Integer, Entidade> entidades = new HashMap<>(); // FIXME: 01/05/2018
    private String admin; // FIXME: 01/05/2018
    private String admin_password; // FIXME: 01/05/2018
    private boolean admin_mode;

    public boolean registaEntidade(Entidade e) {
        Entidade nova = this.entidades.putIfAbsent(e.getNif(), e.clone());
        return (nova == null);
    }

    public boolean entidadeLogIn(int nif, String password) {
        Entidade entidade = this.entidades.get(nif);
        if (entidade == null) return false;

        if(entidade.getPassword().equals(password)){

            return true;
        }
        return false;
    }

    public void adminLogIn(String nome, String password) {
        if (this.admin.equals(nome) && this.admin_password.equals(password)) {
            setAdminMode(true);
        }
    }

    public void setAdminMode(boolean mode) {
        this.admin_mode = mode;
    }

    public boolean getAdminMode() {
        return this.admin_mode;
    }



}
