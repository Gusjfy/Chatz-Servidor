package Services;

import Model.Usuario;
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
public class ServiceUsuario {

    private SQLite db;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static ServiceUsuario instance = null;

    public static ServiceUsuario getIntance() {
        if (instance == null) {
            instance = new ServiceUsuario();
        }
        return instance;
    }

    private ServiceUsuario() {
        db = SQLite.getIntance();
    }

    public Usuario login(String email, String senha) throws SQLException {
        db.conectar();
        Usuario u = new Usuario();
        String sql = "SELECT * "
                + "FROM TB_USUARIO "
                + "WHERE EMAIL = ? "
                + "AND SENHA = ?;";
        preparedStatement = db.criarPreparedStatement(sql);
        try {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            resultSet = preparedStatement.executeQuery();
            u.setId(resultSet.getInt("ID"));
            u.setApelido(resultSet.getString("APELIDO"));
            u.setEmail(resultSet.getString("EMAIL"));
            u.setSenha(resultSet.getString("SENHA"));
            u.setDataNascimento(resultSet.getString("DATANASCIMENTO"));

        } catch (Exception e) {
            System.err.println("Ocorreu um erro");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                }
            }
            resultSet.close();
            db.desconectar();

        }
        return u;
    }

    public void updateUsuarioOnline(int value, int id) {
        db.conectar();

        String sql = "UPDATE TB_USUARIO "
                + "SET ONLINE = ? "
                + "WHERE ID = ?;";
        preparedStatement = db.criarPreparedStatement(sql);

        try {
            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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

    public Usuario findUsuarioByEmail(String EMAIL) {
        db.conectar();
        String sql = "SELECT * "
                + "FROM TB_USUARIO "
                + "WHERE LOWER(EMAIL) = LOWER(?);";

        preparedStatement = db.criarPreparedStatement(sql);
        try {
            preparedStatement.setString(1, EMAIL);
            resultSet = preparedStatement.executeQuery();
            Usuario u = new Usuario();
            u.setId(resultSet.getInt("ID"));
            u.setApelido(resultSet.getString("APELIDO"));
            u.setEmail(resultSet.getString("EMAIL"));
            u.setSenha(resultSet.getString("SENHA"));
            u.setDataNascimento(resultSet.getString("DATANASCIMENTO"));
            return u;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na busca pelo Email");
        }
        db.desconectar();
        return null;
    }

    public Usuario findUsuarioById(int id) {
        db.conectar();
        Usuario u = new Usuario();
        String sql = "SELECT * "
                + "FROM TB_USUARIO "
                + "WHERE ID = ?;";

        preparedStatement = db.criarPreparedStatement(sql);
        try {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            u.setId(resultSet.getInt("ID"));
            u.setApelido(resultSet.getString("APELIDO"));
            u.setEmail(resultSet.getString("EMAIL"));
            u.setSenha(resultSet.getString("SENHA"));
            u.setDataNascimento(resultSet.getString("DATANASCIMENTO"));
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na busca pelo Email");
        }
        db.desconectar();
        return u;
    }

    public boolean updateUsuario(Usuario usuario) {
        db.conectar();
        String sql = "UPDATE TB_USUARIO "
                + "SET APELIDO = ?, SENHA = ?, DATANASCIMENTO = ? "
                + "WHERE ID = ?;";

        preparedStatement = db.criarPreparedStatement(sql);

        try {
            preparedStatement.setString(1, usuario.getApelido());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getDataNascimento());
            preparedStatement.setInt(4, usuario.getId());

            int resultado = preparedStatement.executeUpdate();

            if (resultado == 1) {
                System.out.println("Usuario atualizado!");
                return true;
            } else {
                System.err.println("Usuario não atualizado!");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Usuario não atualizado!");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                }
            }
            db.desconectar();
        }
        return true;
    }

    public List<Usuario> listUsuarios() {
        db.conectar();
        Usuario u;
        List<Usuario> listUsuario = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM TB_USUARIO";
        statement = db.criarStatement();
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                u = new Usuario();
                u.setId(resultSet.getInt("ID"));
                u.setApelido(resultSet.getString("APELIDO"));
                u.setEmail(resultSet.getString("EMAIL"));
                u.setSenha(resultSet.getString("SENHA"));
                u.setDataNascimento(resultSet.getString("DATANASCIMENTO"));
                listUsuario.add(u);
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro na listagem de usuarios");
        }
        db.desconectar();
        return listUsuario;
    }

    public void createUsuario(Usuario usuario) {
        db.conectar();

        String sqlInsert = "INSERT INTO TB_USUARIO ("
                + "APELIDO,"
                + "SENHA,"
                + "EMAIL,"
                + "DATANASCIMENTO"
                + ") VALUES(?,?,?,?)"
                + ";";

        preparedStatement = db.criarPreparedStatement(sqlInsert);
        try {
            preparedStatement.setString(1, usuario.getApelido());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getDataNascimento());

            int resultado = preparedStatement.executeUpdate();

            if (resultado == 1) {
                System.out.println("Usuario inserido!");
            } else {
                System.err.println("Usuario não inserido! =[");
            }

        } catch (SQLException e) {
            System.err.println("Usuario não inserido! =[");
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

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_USUARIO "
                + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "APELIDO TEXT NOT NULL,"
                + "SENHA TEXT NOT NULL,"
                + "EMAIL TEXT NOT NULL,"
                + "DATANASCIMENTO TEXT NOT NULL,"
                + "ONLINE INTEGER DEFAULT 0"
                + ")";
        boolean conectou = false;
        try {
            conectou = db.conectar();

            Statement stmt = db.criarStatement();

            stmt.execute(sql);

            System.out.println("Tabela Usuario criada!");

        } catch (SQLException e) {

        } finally {
            if (conectou) {
                db.desconectar();
            }
        }
    }

}
