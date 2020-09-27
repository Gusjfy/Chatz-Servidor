package Services;

import Model.Usuario;
import Controller.Controller;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            verifyData();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verifyData() throws IOException {
        Usuario u = serviceUsuario.findUsuarioByEmail(user.getEmail());
        user.setId(u.getId());
        if (serviceUsuario.updateUsuario(user)) {
            saida.writeUTF("true");
            saida.flush();
        } else {
            
        }
        
    }
    
}
