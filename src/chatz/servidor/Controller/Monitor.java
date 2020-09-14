package chatz.servidor.Controller;

import Model.Usuario;
import Services.ServiceUsuario;

/**
 *
 * @author Leonardo Steinke
 */
public class Monitor {

    Controller controller = Controller.getIntance();

    ServiceUsuario usuarioService = ServiceUsuario.getIntance();

    public synchronized void UserSignUp(Usuario u) {
        controller.getListUsuario().add(u);
        usuarioService.createUsuario(u);

    }

}
