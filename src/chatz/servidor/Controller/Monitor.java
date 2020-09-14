package chatz.servidor.Controller;

import Model.Usuario;

/**
 *
 * @author Leonardo Steinke
 */
public class Monitor {

    Controller controller = Controller.getIntance();

    public synchronized void UserSignUp(Usuario u) {
        u.setId(controller.getListUsuario().size() + 1);
        controller.getListUsuario().add(u);

    }

}
