package Services;

import Model.UsuarioContatos;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Leonardo Steinke
 */
public class ServiceUsuarioContatos {

    private SQLite db;

    private static ServiceUsuarioContatos instance = null;

    public static ServiceUsuarioContatos getIntance() {
        if (instance == null) {
            instance = new ServiceUsuarioContatos();
        }
        return instance;
    }

    private ServiceUsuarioContatos() {
        db = SQLite.getIntance();
    }

    public void createUsuario(UsuarioContatos usuarioContatos) {
        db.conectar();
        String sqlInsert = "INSERT INTO TB_USUARIO "
                + "("
                + "ID_USUARIO,"
                + "ID_CONTATO"
                + ") "
                + "VALUES(?,?);";

        PreparedStatement preparedStatement = db.criarPreparedStatement(sqlInsert);
        try {

            preparedStatement.setInt(1, usuarioContatos.getIdUsuario());
            preparedStatement.setInt(2, usuarioContatos.getIdContato());

            int resultado = preparedStatement.executeUpdate();

            if (resultado == 1) {
                System.out.println("Contato inseridp!");
            } else {
                System.out.println("Contato não inserido! =[");
            }

        } catch (SQLException e) {
            System.out.println("Contato não inserido! =[");
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

    public void createUserContactsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_USUARIO_CONTATO "
                + "("
                + "ID_USUARIO INTEGER NOT NULL,"
                + "ID_CONTATO INTEGER NOT NULL"
                + ")";
        boolean conectou = false;
        try {
            conectou = db.conectar();

            Statement stmt = db.criarStatement();

            stmt.execute(sql);

            System.out.println("Tabela UsuarioContato criada!");

        } catch (SQLException e) {
            //mensagem de erro na criação da tabela
        } finally {
            if (conectou) {
                db.desconectar();
            }
        }
    }

}
