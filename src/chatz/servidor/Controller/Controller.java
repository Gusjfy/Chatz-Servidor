package chatz.servidor.Controller;

import Services.ConnectionSignIn;
import Model.Usuario;
import Services.ConnectionSignUp;
import Services.ConnectionStartNewChat;
import Services.ConnectionUpdate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import Services.ConnectionServer;

/**
 *
 * @author Leonardo Steinke
 */
public class Controller extends Thread {

    List<Usuario> listUsuario = new ArrayList<>();
    List<Usuario> listUsuarioOnline = new ArrayList<>();

    private ObjectOutputStream saida;

    private ServerSocket server;
    private int PORT;

    private Controller() {

    }

    private static Controller instance = null;

    public static Controller getIntance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void signUp(String apelido, String senha, String email, String dataNascimento) {
        Usuario u = new Usuario();
        u.setApelido(apelido);
        u.setSenha(senha);
        u.setEmail(email);
        u.setDataNascimento(dataNascimento);
        u.setId(listUsuario.size() + 1);

        listUsuario.add(u);
    }

    public Usuario signIn(String apelido, String senha) {
        for (Usuario usuario : listUsuario) {
            if (usuario.getApelido().equalsIgnoreCase(apelido) && usuario.getSenha().equals(senha)) {
                listUsuarioOnline.add(usuario);
                return usuario;
            }
        }
        return null;
    }

    public void updateUser(Usuario usuario) {

    }

    public List<Usuario> getListUsuarioOnline() {
        return listUsuarioOnline;
    }

    public void setListUsuarioOnline(List<Usuario> listUsuarioOnline) {
        this.listUsuarioOnline = listUsuarioOnline;
    }

    public void removeUser(Usuario user) {
        listUsuarioOnline.remove(user);
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) throws IOException {
        this.PORT = PORT;
        server = new ServerSocket(PORT);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Aguardando conexão...");
            try (Socket conn = server.accept();) {
                System.out.println("Conectado com: " + conn.getInetAddress().getHostName());
                ObjectInputStream entrada = new ObjectInputStream(conn.getInputStream());

                int code = entrada.readInt(); //Código de Operação

                Usuario u; //Usuário solicitante

                ConnectionServer connection;

                switch (code) {
                    case 1://Cadastro
                        connection = new ConnectionSignUp();
                        u = new Usuario();
                        u.setApelido(entrada.readUTF());
                        u.setEmail(entrada.readUTF());
                        u.setSenha(entrada.readUTF());
                        u.setDataNascimento(entrada.readUTF());
                        saida = new ObjectOutputStream(conn.getOutputStream());
                        connection.execute(u, saida);
                        break;
                    case 2://Login
                        connection = new ConnectionSignIn();
                        u = new Usuario();
                        u.setEmail(entrada.readUTF());
                        u.setSenha(entrada.readUTF());
                        saida = new ObjectOutputStream(conn.getOutputStream());
                        connection.execute(u, saida);
                        break;
                    case 3://Atualiza dados
                        connection = new ConnectionUpdate();
                        u = new Usuario();
                        u.setApelido(entrada.readUTF());
                        u.setEmail(entrada.readUTF());
                        u.setSenha(entrada.readUTF());
                        u.setDataNascimento(entrada.readUTF());
                        saida = new ObjectOutputStream(conn.getOutputStream());
                        connection.execute(u, saida);
                        break;
                    case 4://Inicia conexão com outro usuario
                        connection = new ConnectionStartNewChat();
                        break;
                }
                conn.close();
                // entrada.close();
            } catch (Exception e) {

            }
        }
    }
}
