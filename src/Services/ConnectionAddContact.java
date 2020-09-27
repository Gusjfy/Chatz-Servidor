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
public class ConnectionAddContact implements ConnectionServer {

    Usuario u;
    ObjectOutputStream saida;
    ServiceUsuario serviceUsuario = ServiceUsuario.getIntance();
    ServiceUsuarioContatos serviceUsuarioContatos = ServiceUsuarioContatos.getIntance();
    
    @Override
    public void execute(Usuario u, ObjectOutputStream saida) {
        this.u = u;
        this.saida = saida;
        try {
            addContact();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addContact() throws IOException, SQLException {
        Usuario user = serviceUsuario.findUsuarioById(u.getId());
        Usuario newContact = serviceUsuario.findUsuarioByEmail(u.getEmail());
        if (newContact != null && serviceUsuarioContatos.addContact(user.getId(), newContact.getId())) {
            saida.writeUTF("true");
            saida.flush();
            System.out.println("CONTATO ADICIONADO");
        } else {
            saida.writeUTF("false");
            saida.flush();
            System.out.println("DEU PROBLEMA AO ADICIONAR CONTATO");
        }
        
    }
    
}
