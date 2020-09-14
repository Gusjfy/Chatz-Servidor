/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatz.servidor;

import Model.Usuario;
import Services.SQLite;
import Services.ServiceUsuario;
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
        //SQLite sqlite = new SQLite();
        //sqlite.conectar();
        //sqlite.desconectar();
        //sqlite.createUserTable();
        ServiceUsuario usuService = ServiceUsuario.getIntance();
        Usuario u = new Usuario();
        u.setId(10);
        u.setApelido("Teste");
        u.setEmail("teste@teste.com");
        u.setSenha("senha123");
        u.setDataNascimento("28/06/1999");
        usuService.createUsuario(u);

//Controller controller = Controller.getIntance();
        //        controller.setPORT(5555);
        //        controller.start();
    }
    
}
