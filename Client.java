
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Client {

    private Client() {
    }

    public static void Menu() {
        System.out.println("-------------------------");
        System.out.println("--------- Menu ----------");
        System.out.println("-------------------------");
        System.out.println("1 ----- Cadastrar Usuario");
        System.out.println("2 ----------- GetMensagem");
        System.out.println("3 --------- GetNMensagens");
        System.out.println("4 ---------- SendMensagem");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
    }

    public static Usuario criarUsuario() throws RemoteException {
        try {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite o username do usuario");
            String userName = entrada.nextLine();
            System.out.println("Digite a senha do usuario");
            String senha = entrada.nextLine();

            return new Usuario(userName, senha);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;

    }

    public static void getMensagem(Correio stub) throws RemoteException {
        try {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite o username do usuario");
            String userName = entrada.nextLine();
            System.out.println("Digite a senha do usuario");
            String senha = entrada.nextLine();
            Mensagem m = stub.getMensagem(userName, senha);
            System.out.println("Mensagem do usuario: " + userName);
            System.out.println(m.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public static void getNMensagens(Correio stub) throws RemoteException {
        try {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite o username do usuario");
            String userName = entrada.nextLine();
            System.out.println("Digite a senha do usuario");
            String senha = entrada.nextLine();
            if (stub.getNMensagens(userName, senha) != -1) {

                System.out.println("nº de mensagem: " + stub.getNMensagens(userName, senha));
            } else {
                System.out.println("Usuario ou senha inválidos :(");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public static void sendMensagem(Correio stub) {
        try {
            Mensagem m = criarMensagem();
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite a senha: ");
            String senha = entrada.nextLine();
            System.out.println("Digite o userName do destinatario: ");
            String destinatario = entrada.nextLine();

            if (stub.sendMensagem(m, senha, destinatario)) {
                System.out.println("Mensagem enviada com sucesso!!!");
            } else {
                System.out.println("Erro: Não foi possivel enviar a mensagem :(");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private static Mensagem criarMensagem() {
        try {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite o userName do remetente: ");
            String userNameRemetente = entrada.nextLine();
            System.out.println("Digite o titulo da mensagem: ");
            String titulo = entrada.nextLine();
            System.out.println("Digite o texto da mensagem");
            String texto = entrada.nextLine();

            return new Mensagem(userNameRemetente, titulo, texto);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;

    }

    public static void main(String args[]) {

        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Correio stub = (Correio) registry.lookup("Correio");
            Menu();
            Scanner entrada = new Scanner(System.in);
            int str = entrada.nextInt();
            do {
                switch (str) {
                case 1:
                    Usuario u = criarUsuario();
                    boolean cadastrado = stub.cadastrarUsuario(u);
                    System.out.println(cadastrado);
                    if (cadastrado) {
                        System.out.println("Usuario Cadastrado com sucesso");
                    } else {
                        System.out.println("Não foi possivel cadastrar o usuario");
                    }
                    Menu();
                    str = entrada.nextInt();
                    break;
                case 2:
                    getMensagem(stub);
                    Menu();
                    str = entrada.nextInt();
                    System.out.println("");
                    break;
                case 3:
                    getNMensagens(stub);
                    Menu();
                    str = entrada.nextInt();
                    System.out.println("");
                    break;
                case 4:
                    sendMensagem(stub);
                    Menu();
                    str = entrada.nextInt();
                    System.out.println("");
                    break;
                default:
                    System.out.println("Comando Inválido");
                }
            } while (str != -1);
            entrada.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
