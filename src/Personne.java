import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> personnes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        String SQLPrep = "SELECT * FROM Personne";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQLPrep);

        // Parcourir les résultats
        while (rs.next()) {
            int id = rs.getInt("ID");
            String nom = rs.getString("NOM");
            String prenom = rs.getString("PRENOM");

            Personne personne = new Personne(nom, prenom);

            personnes.add(personne);
        }

        rs.close();
        stmt.close();

        return personnes;
    }

}
