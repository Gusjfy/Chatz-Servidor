package Services;

import Model.Usuario;
import chatz.servidor.Controller.Controller;
import chatz.servidor.Controller.Monitor;
import java.io.IOException;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionSignUp extends Thread implements ConnectionServer {

    Controller controller;
    private Usuario user;
    Monitor m = new Monitor();

    public ConnectionSignUp() throws IOException {
        controller = Controller.getIntance();
    }

    @Override
    public void execute(Usuario u) {
        this.user = u;
        verifyData();
    }

    private void verifyData() {

        for (Usuario usuario : controller.getListUsuario()) {
            if (usuario.getApelido().equalsIgnoreCase(user.getApelido())) {
                System.out.println("USUÁRIO JÁ CADASTRADO");
                return;
            }
        }
        m.UserSignUp(user);
    }
}
