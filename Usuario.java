import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userName;
    private String senha;
    private List<Mensagem> listaDeMensagens;

    public Usuario(String userName, String senha) {
        this.userName = userName;
        this.senha = senha;
        this.listaDeMensagens = new ArrayList<Mensagem>();
    }

    public String getUserName() {
        return userName;
    }

    public String getSenha() {
        return senha;
    }

    public List<Mensagem> getListaDeMensagens() {
        return listaDeMensagens;
    }

    public boolean addMensagens(Mensagem m) {
        if (this.listaDeMensagens.add(m)) {
            return true;
        }
        return false;
    }

}