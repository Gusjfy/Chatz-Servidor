package Services;

import Model.Usuario;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Leonardo Steinke
 */
public class ServiceUsuario {

    private SQLite db;

    private static ServiceUsuario instance = null;

    public static ServiceUsuario getIntance() {
        if (instance == null) {
            instance = new ServiceUsuario();
        }
        return instance;
    }

    private ServiceUsuario() {
        db = new SQLite();
    }

    public void createUsuario(Usuario usuario) {
        db.conectar();
        String sqlInsert = "INSERT INTO TB_USUARIO ("
                + "ID,"
                + "APELIDO,"
                + "SENHA,"
                + "EMAIL"
                + "DATANASCIMENTO"
                + ") VALUES(?,?,?,?,?)"
                + ";";

            PreparedStatement preparedStatement = db.criarPreparedStatement(sqlInsert);
        try {

            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setString(2, usuario.getApelido());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getDataNascimento());

            int resultado = preparedStatement.executeUpdate();

            if (resultado == 1) {
                System.out.println("Pessoa inserida!");
            } else {
                System.out.println("Pessoa não inserida! =[");
            }

        } catch (SQLException e) {
            System.out.println("Pessoa não inserida! =[");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                }
            }
            db.desconectar();
        }
    }

}
