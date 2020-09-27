package Services;

import Controller.Controller;
import Model.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Steinke
 */
public class KeepAlive extends Thread {

    Usuario user;

    Socket socket;
    Controller controller = Controller.getIntance();
    ServiceUsuario service = ServiceUsuario.getIntance();
    List<Usuario> userList;

    public KeepAlive() {
    }

    @Override
    public void run() {
        while (true) {
            userList = new ArrayList<>(controller.getListUsuarioOnline());
            try {
                for (Usuario user : userList) {
                    try {
                        socket = new Socket("localhost", 5555 + user.getId());
                        ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                        saida.writeInt(1);
                        saida.flush();

                        ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                        String line;
                        line = entrada.readUTF();
//                        if (line.equalsIgnoreCase("true")) {
//                            System.out.println("Usuario " + user.getApelido() + " = " + line);
//                        }

                    } catch (Exception e) {
                        System.out.println("Usuario " + user.getApelido() + " saiu");
                        controller.getListUsuarioOnline().remove(user);
                        service.updateUsuarioOnline(0, user.getId());
                        break;

                    }

                }
                sleep(5000);

            } catch (InterruptedException ex) {
                Logger.getLogger(KeepAlive.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
