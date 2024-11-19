import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne findById(int id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String requete = "SELECT * FROM Personne WHERE id = ?";
        PreparedStatement prep = connection.prepareStatement(requete);
        prep.setInt(1, 2);
        ResultSet rs = prep.executeQuery();
        Personne res = new Personne(rs.getInt("ID"), nom = rs.getString("NOM"), rs.getString("PRENOM"));
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
}
