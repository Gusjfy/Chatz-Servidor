package Services;

import Model.Usuario;
import chatz.servidor.Controller.Controller;
import java.io.ObjectOutputStream;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionUpdate implements ConnectionServer {
    
    Controller controller = Controller.getIntance();
    private Usuario user;
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();
    private ObjectOutputStream saida;
    
    @Override
    public void execute(Usuario usuario, ObjectOutputStream saida) {
        this.saida = saida;
        this.user = usuario;
        verifyData();
    }
    
    public void verifyData() {
        Usuario u = serviceUsuario.findUsuarioByEmail(user.getEmail());
        user.setId(u.getId());
        if (serviceUsuario.updateUsuario(user)) {
            
        } else {
            
        }
        
    }
    
}
