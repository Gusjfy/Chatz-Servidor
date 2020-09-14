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
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();

    @Override
    public void execute(Usuario u) {
        this.user = u;
        verifyData();
    }

    public void verifyData() {
        Usuario logado = serviceUsuario.login(user.getEmail(), user.getSenha());
        System.out.println("Autenticado: " + logado.getApelido());
    }

}
