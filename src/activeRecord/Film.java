package activeRecord;
import java.sql.*;
import java.util.ArrayList;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, int id_real) {
        this.titre = titre;
        this.id_real = id_real;
        this.id = -1;
    }
    public Film(String titre,Personne p){
        this.titre =  titre;
        this.id_real = p.getId();
        this.id=-1;
    }

    private Film(String titre, int id_real, int id) {
        this.titre = titre;
        this.id_real = id_real;
        this.id = id;
    }

    public static Film findById(int id) throws SQLException {
        String requete = "SELECT * FROM Film WHERE ID = ?";
        Film film = null;
        Connection connection = DBConnection.getConnection();
        PreparedStatement prep = connection.prepareStatement(requete);

        prep.setInt(1, id);

        try{
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                String titre = rs.getString("TITRE");
                int id_real = rs.getInt("ID_REA");
                film = new Film(titre, id_real, id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return film;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    public String getTitre() {
        return titre;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(id_real);
    }

    @Override
    public String toString() {
        return "Film{" +
                "titre='" + titre + '\'' +
                ", id=" + id +
                ", id_real=" + id_real +
                '}';
    }

    public static void createTable() {
        try {
            String requete = "CREATE TABLE Film (" +
                    "id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "titre VARCHAR(40) NOT NULL, " +
                    "id_rea INT(11) DEFAULT NULL, " +
                    "PRIMARY KEY (id), " +
                    "KEY id_rea (id_rea), " +
                    "CONSTRAINT film_ibfk_1 FOREIGN KEY (id_rea) REFERENCES Personne (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci";

            Connection connection = DBConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(requete);
            stmt.execute();
            //  System.out.println("Table 'Film' créée avec succès.");
        } catch (SQLException e) {
            System.out.println("La Table 'Film' existe déjà'.");
        }
    }

    public static void deleteTable() {
        Connection connection = DBConnection.getConnection();
        String requete = "DROP TABLE FILM";
try{
        Statement stmt = connection.createStatement();
            stmt.execute(requete);
        } catch (SQLException e) {
            System.out.println("La Table 'Film' existe déjà'.");
        }
    }

    public void save() {
        if (this.getId() == -1) {
            Connection connection = DBConnection.getConnection();
            String requete = "INSERT INTO Film (titre, id_rea) VALUES (?, ?)";
            PreparedStatement prep = null;
            try {
                if (this.getId_real() == -1) {
                    throw new RealisateurAbsentException("Le realisateur est absent");
                }
                prep = connection.prepareStatement(requete);
                prep.setString(1, this.getTitre());
                prep.setInt(2, this.getId_real());
                prep.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Erreur dans la methode save de la classe Film");
            } catch (RealisateurAbsentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            Connection connection = DBConnection.getConnection();
            String requete = "UPDATE Film SET titre = ?, id_rea = ? WHERE ID = ?";
            PreparedStatement prep = null;
            try {
                if (this.getId_real() == -1) {
                    throw new RealisateurAbsentException("Le realisateur est absent");
                }
                prep = connection.prepareStatement(requete);
                prep.setString(1, this.getTitre());
                prep.setInt(2, this.getId_real());
                prep.setInt(3, this.getId());
                prep.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur dans la methode save de la classe Film");
            } catch (RealisateurAbsentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void delete() {
        Connection connection = DBConnection.getConnection();
        String requete = "DELETE FROM Film WHERE ID = ?";
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(requete);
            prep.setInt(1, this.getId());
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Film> findByRealisateur(Personne p) {
        ArrayList<Film> films = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String requete = "SELECT f.id, f.titre, f.id_rea " +
                "FROM Film f " +
                "WHERE f.id_rea = ?";

        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            prep = connection.prepareStatement(requete);
            prep.setInt(1, p.getId());
            rs = prep.executeQuery();

            while (rs.next()) {
                films.add(new Film(
                        rs.getString("titre"),
                        rs.getInt("id_rea"),
                        rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des films par réalisateur : " + e.getMessage());
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return films;
    }

    public void setTitre(String txt) {
        this.titre = txt;
    }
}