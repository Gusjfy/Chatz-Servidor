package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Leonardo Steinke
 */
public class SQLite {

    private Connection conection;

    public boolean conectar() {
        try {
            String url = "jdbc:sqlite:banco/banco_sqlite.db";

            this.conection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean desconectar() {
        try {
            if (!this.conection.isClosed()) {
                this.conection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Statement criarStatement() {
        try {
            return this.conection.createStatement();
        } catch (SQLException e) {
            return null;
        }
    }

    public PreparedStatement criarPreparedStatement(String sql) {
        try {
            return conection.prepareStatement(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public Connection getConection() {
        return this.conection;
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_USUARIO "
                + "("
                + "ID INTEGER PRIMARY KEY,"
                + "APELIDO TEXT NOT NULL,"
                + "SENHA TEXT NOT NULL,"
                + "EMAIL TEXT NOT NULL,"
                + "DATANASCIMENTO TEXT NOT NULL"
                + ")";
        boolean conectou = false;
        try {
            conectou = this.conectar();

            Statement stmt = this.criarStatement();

            stmt.execute(sql);

            System.out.println("Tabela Usuario criada!");

        } catch (SQLException e) {
            //mensagem de erro na criação da tabela
        } finally {
            if (conectou) {
                this.desconectar();
            }
        }
    }

}
