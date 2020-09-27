package Services;

import Model.Usuario;
import Services.ConnectionServer;
import Services.ServiceUsuarioContatos;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Steinke
 */
public class ConnectionListContacts implements ConnectionServer {

    Usuario u;
    ObjectOutputStream saida;
    ServiceUsuarioContatos service = ServiceUsuarioContatos.getIntance();

    public ConnectionListContacts() {
    }

    @Override
    public void execute(Usuario u, ObjectOutputStream saida) {
        this.u = u;
        this.saida = saida;
        try {
            listUsers();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionListContacts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void listUsers() throws IOException, SQLException {
        List<Usuario> listUser = service.selectUserContacts(u.getId());
        for (Usuario usuario : listUser) {
            saida.writeUTF("true");
            saida.writeInt(usuario.getId());
            saida.writeUTF(usuario.getApelido());
            saida.writeInt(usuario.getOnline());
        }
        saida.flush();
    }

}
