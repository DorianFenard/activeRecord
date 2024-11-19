import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    private final String userName = "root";
    private final String password = "";
    private final String serverName = "127.0.0.1";
    private final String portNumber = "8888";
    private static String dbName = "testpersonne";

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String urlDB = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);

            connection = DriverManager.getConnection(urlDB, connectionProps);

            System.out.println("Connexion à la base de données réussie !");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    public static void setNomDb(String nom){
        dbName = nom;
    }



    public static Connection getConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }
}