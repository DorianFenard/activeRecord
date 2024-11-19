import com.mysql.cj.jdbc.SuspendableXAConnection;

import java.sql.*;

public class PrincipaleJDBC {

    public static void main(String[] args) throws SQLException {
        System.out.println(Personne.findAll());
        System.out.println(Personne.findById(2));
        System.out.println(Personne.findByName("Spielberg"));
    }
}