import java.sql.*;

public class PrincipaleJDBC {

    public static void main(String[] args) throws SQLException {
        System.out.println(Personne.findAll());
    }
}