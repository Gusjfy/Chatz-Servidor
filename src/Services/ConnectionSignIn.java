package Services;

import Model.Usuario;
import chatz.servidor.Controller.Controller;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionSignIn implements ConnectionServer {
    
    Controller controller;
    private Usuario user;
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();
    private ObjectOutputStream saida;
    
    @Override
    public void execute(Usuario u, ObjectOutputStream saida) {
        this.saida = saida;
        this.user = u;
        try {
            verifyData();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verifyData() throws IOException {
        Usuario logado = serviceUsuario.login(user.getEmail(), user.getSenha());
        if (logado.getEmail() != null) {
            System.out.println("Autenticado: " + logado.getApelido());
            saida.writeUTF("true");
            saida.writeInt(logado.getId());
            saida.writeUTF(logado.getApelido());
            saida.writeUTF(logado.getEmail());
            saida.writeUTF(logado.getSenha());
            saida.writeUTF(logado.getDataNascimento());
        } else {
            saida.writeUTF("false");
            
        }
        saida.flush();
    }
    
}
