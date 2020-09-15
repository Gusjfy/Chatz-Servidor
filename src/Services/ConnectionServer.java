package Services;

import Model.Usuario;
import java.io.ObjectOutputStream;

/**
 *
 * @author Leonardo Steinke
 */
public interface ConnectionServer {

    void execute(Usuario u, ObjectOutputStream saida);

}
