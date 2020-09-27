package Services;

import Model.Usuario;
import Controller.Controller;
import Controller.Monitor;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionSignUp extends Thread implements ConnectionServer {

    Controller controller;
    private Usuario user;
    Monitor m = new Monitor();
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();
    ObjectOutputStream saida;

    public ConnectionSignUp() throws IOException {
        controller = Controller.getIntance();
    }

    @Override
    public void execute(Usuario u, ObjectOutputStream saida) {
        this.saida = saida;
        this.user = u;
        try {
            verifyData();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verifyData() throws IOException {
        Usuario u = serviceUsuario.findUsuarioByEmail(user.getEmail().toLowerCase());
        if (u != null) {
            System.out.println("E-MAIL JÁ CADASTRADO");
            saida.writeUTF("E-Mail já cadastrado");
            saida.flush();
            return;

        }
        try {
            m.UserSignUp(user);
            saida.writeUTF("true");
            saida.flush();
        } catch (Exception e) {
        }
    }
}
