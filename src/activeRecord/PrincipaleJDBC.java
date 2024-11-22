package activeRecord;

import java.sql.*;

public class PrincipaleJDBC {

    public static void main(String[] args) throws SQLException {
       DBConnection.setNomDb("testpersonne");
       Personne p = Personne.findByName("test").getFirst();
       p.delete();



    }
}