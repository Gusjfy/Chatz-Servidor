/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatz.servidor;

import Services.SQLite;
import Services.ServiceUsuario;
import Services.ServiceUsuarioContatos;
import chatz.servidor.Controller.Controller;
import java.io.IOException;

/**
 *
 * @author leona
 */
public class ChatzServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SQLite db = SQLite.getIntance();
        db.conectar();
        db.desconectar();
        ServiceUsuario usuarioService = ServiceUsuario.getIntance();
        usuarioService.createUserTable();

        ServiceUsuarioContatos usuarioContatoService = ServiceUsuarioContatos.getIntance();
        usuarioContatoService.createUserContactsTable();

        Controller controller = Controller.getIntance();
        controller.setPORT(5555);
        controller.start();
        
    }
    
}
