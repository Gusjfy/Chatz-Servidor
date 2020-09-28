/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Usuario;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo
 */
public class ConnectionRemoveContact implements ConnectionServer {

    Usuario u;
    ObjectOutputStream saida;
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();
    ServiceUsuarioContatos serviceUsuarioContatos = ServiceUsuarioContatos.getIntance();

    
    @Override
    public void execute(Usuario u, ObjectOutputStream saida) {
        this.u = u;
        this.saida = saida;
        try {
            removeContact();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void removeContact() throws IOException, SQLException {
        Usuario user = serviceUsuario.findUsuarioByEmail(u.getEmail());
        Usuario Contact = serviceUsuario.findUsuarioById(u.getId());
        if (Contact != null && serviceUsuarioContatos.removeContact(user.getId(), Contact.getId())) {
            if (serviceUsuarioContatos.removeContact(Contact.getId(), user.getId())) {
                saida.writeUTF("true");
                saida.flush();
            } else {
            saida.writeUTF("false");
            saida.flush();
            }
        } else {
            saida.writeUTF("false");
            saida.flush();
        }
    }
    
}
