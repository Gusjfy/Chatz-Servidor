package Services;

import Model.Usuario;
import Model.UsuarioContatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo Steinke
 */
public class ServiceUsuarioContatos {

    private SQLite db;

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

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
                System.out.println("Contato inserido!");
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

    public List<Usuario> selectUserContacts(int id) throws SQLException {
        Connection conn = db.getConection();
        conn = DriverManager.getConnection("jdbc:sqlite:banco/banco_sqlite.db");
        String sql = "SELECT * "
                + "FROM TB_USUARIO_CONTATO "
                + "WHERE ID_USUARIO = "+id;
        ServiceUsuario service = ServiceUsuario.getIntance();
        List<Usuario> userList = new ArrayList<>();
        statement = conn.createStatement();
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int i = resultSet.getInt("ID_CONTATO");
                System.out.println(i);
                userList.add(service.findUsuarioById(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        resultSet.close();
        db.desconectar();
        return userList;
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
        } finally {
            if (conectou) {
                db.desconectar();
            }
        }
    }
    
    public boolean addContact(int idUsuario, int idContato) {
        db.conectar();
        String sql = "INSERT INTO TB_USUARIO_CONTATO ("
                + "ID_USUARIO, "
                + "ID_CONTATO ) "
                + "VALUES ("
                + idUsuario + ", "
                + idContato + " );";
        boolean conectou = false;
        try {
            conectou = db.conectar();

            Statement stmt = db.criarStatement();

            stmt.execute(sql);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conectou) {
                db.desconectar();
            }
        }
    }

}
