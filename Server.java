
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server implements Correio {
    private Map<String, Usuario> Usuarios;

    public Server() {
        this.Usuarios = new HashMap<String, Usuario>();
    }

    public static void main(String args[]) {

        try {
            System.setProperty("java.rmi.server.hostname", "localhost");

            // Create and export a remote object
            Server obj = new Server();
            Correio stub = (Correio) UnicastRemoteObject.exportObject((Remote) obj, 0);

            // Register the remote object with a Java RMI registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Correio", stub);

            System.out.println("Server Ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean cadastrarUsuario(Usuario u) throws RemoteException {
        try {
            if (!this.Usuarios.isEmpty()) {
                String userName = u.getUserName();
                if (!this.Usuarios.containsKey(userName)) {
                    this.Usuarios.put(userName, u);
                    return true;
                }
            } else {
                this.Usuarios.put(u.getUserName(), u);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return false;
    }

    @Override
    public Mensagem getMensagem(String userName, String senha) throws RemoteException {
        Mensagem mensagem;
        try {
            if (this.Usuarios.containsKey(userName)) {
                Usuario usuario = this.Usuarios.get(userName);

                String key = usuario.getSenha();
                if (key.equals(senha)) {
                    if (!usuario.getListaDeMensagens().isEmpty()) {
                        mensagem = usuario.getListaDeMensagens().get(0);
                        usuario.getListaDeMensagens().remove(0);
                    } else {
                        mensagem = new Mensagem("Server", "Não Possuí Mensagem", "Esse usuario não possuí mensagem :(");
                    }
                } else {
                    mensagem = new Mensagem("Server", "Senha inválida", "Senha inválida :(");
                }
            } else {
                mensagem = new Mensagem("Server", "Usuario não existe",
                        "Não existe esse usuario em nosso banco de dados :(");
            }
        } catch (Exception e) {
            mensagem = new Mensagem("Server", "Erro: ", e.toString());
        }

        return mensagem;
    }

    @Override
    public int getNMensagens(String userName, String senha) throws RemoteException {
        try {
            Usuario usuario = this.Usuarios.get(userName);

            if (usuario.getSenha().equals(senha)) {
                return usuario.getListaDeMensagens().size();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return -1;

    }

    @Override
    public boolean sendMensagem(Mensagem m, String senha, String userNameDestinatario) throws RemoteException {
        try {
            String usuarioRemetente = m.getUserNameRemetente();
            if (this.Usuarios.containsKey(usuarioRemetente)) {
                Usuario remetente = this.Usuarios.get(usuarioRemetente);
                if (remetente.getSenha().equals(senha)) {
                    Usuario destinatario = this.Usuarios.get(userNameDestinatario);
                    if (destinatario.addMensagens(m)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return false;
    }
}
