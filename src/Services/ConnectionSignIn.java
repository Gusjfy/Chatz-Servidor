package Services;

import Model.Usuario;
import chatz.servidor.Controller.Controller;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionSignIn implements ConnectionServer {

    Controller controller;
    private Usuario user;

    @Override
    public void execute(Usuario u) {
        this.user = u;
        verifyData();
    }

    public void verifyData() {

        for (Usuario usuario : controller.getListUsuario()) {
            if (usuario.getApelido().equalsIgnoreCase(user.getApelido()) && usuario.getSenha().equals(user.getSenha())) {
                controller.getListUsuarioOnline().add(user);
            }
        }

    }

}
