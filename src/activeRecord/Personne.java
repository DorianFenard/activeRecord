package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne(String nom, String prenom, int id) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static Personne findById(int id) {
        String requete = "SELECT * FROM Personne WHERE ID = ?";
        Personne personne = null;

        try {
            Connection connection = DBConnection.getConnection();

             PreparedStatement prep = connection.prepareStatement(requete);

            prep.setInt(1, id);

            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    int i = rs.getInt("ID");
                    personne = new Personne(nom, prenom, i);
                }
            }
        } catch (SQLException e) {
            // Logger au lieu d'imprimer la stack trace
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la recherche de la personne par ID", e);
        }

        return personne;
    }

    public static ArrayList<Personne> findByName(String name) {
        String requete = "SELECT * FROM Personne WHERE NOM = ?";
        ArrayList<Personne> personnes = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();

             PreparedStatement prep = connection.prepareStatement(requete);

            prep.setString(1, name);

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) { // Correction de "if" en "while" pour parcourir tous les résultats
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    int id = rs.getInt("ID");
                    Personne personne = new Personne(nom, prenom, id);
                    personnes.add(personne);
                }
            }
        } catch (SQLException e) {
            // Logger au lieu d'imprimer la stack trace
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la recherche de personnes par nom", e);
        }

        return personnes;
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

        while (rs.next()) {
            int id = rs.getInt("ID");
            String nom = rs.getString("NOM");
            String prenom = rs.getString("PRENOM");

            Personne personne = new Personne(nom, prenom,id);

            personnes.add(personne);
        }

        rs.close();
        stmt.close();

        return personnes;
    }

    public void save() {
        if (this.getId() == -1) {
            Connection connection = DBConnection.getConnection();
            String requete = "INSERT INTO Personne (NOM, PRENOM) VALUES (?, ?)";
            PreparedStatement prep = null;
            try {
                prep = connection.prepareStatement(requete);
                prep.setString(1, this.getNom());
                prep.setString(2, this.getPrenom());
                prep.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Connection connection = DBConnection.getConnection();
            String requete = "UPDATE Personne SET NOM = ?, PRENOM = ? WHERE ID = ?";
            PreparedStatement prep = null;
            try {
                prep = connection.prepareStatement(requete);
                prep.setString(1, this.getNom());
                prep.setString(2, this.getPrenom());
                prep.setInt(3, this.getId());
                prep.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        Connection connection = DBConnection.getConnection();
        String requete = "DELETE FROM Personne WHERE ID = ?";
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(requete);
            prep.setInt(1, this.getId());
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        Connection connection = DBConnection.getConnection();
        String requete = "CREATE TABLE Personne (" +
                "id INT(11) NOT NULL AUTO_INCREMENT, " +
                "nom VARCHAR(40) NOT NULL, " +
                "prenom VARCHAR(40) NOT NULL, " +
                "PRIMARY KEY (id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(requete);
           // System.out.println("Table 'Personne' créée avec succès.");
        } catch (SQLException e) {
            System.out.println("La table Personne existe déjà");
        }
    }

    public static void deleteTable(){
        Connection connection = DBConnection.getConnection();
        String requete = "DROP TABLE Personne";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(requete);
           // System.out.println("Table 'Personne' supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNom(String n) {
        this.nom = n;
    }

    public void setPrenom(String pre) {
        this.prenom = pre;
    }
}
