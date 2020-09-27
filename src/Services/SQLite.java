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
    private static SQLite instance = null;

    public static SQLite getIntance() {
        if (instance == null) {
            instance = new SQLite();
        }
        return instance;
    }

    private SQLite() {

    }

    public boolean conectar() {
        try {
            String url = "jdbc:sqlite:banco/banco_sqlite.db";
            if (conection == null) {
                this.conection = DriverManager.getConnection(url);
            } else {
                this.conection.close();
                this.conection = DriverManager.getConnection(url);
            }
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

}
