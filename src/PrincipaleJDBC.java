import java.sql.*;

public class PrincipaleJDBC {

    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();

            String createString = "CREATE TABLE Personne ( "
                    + "ID INTEGER AUTO_INCREMENT, "
                    + "NOM VARCHAR(40) NOT NULL, "
                    + "PRENOM VARCHAR(40) NOT NULL, "
                    + "PRIMARY KEY (ID))";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createString);
            System.out.println("Table Personne créée avec succès.");

            String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?)";
            PreparedStatement prep = connection.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);

            prep.setString(1, "Steven");
            prep.setString(2, "Spielberg");
            prep.executeUpdate();
            System.out.println("Personne 1 insérée : Steven Spielberg.");

            prep.setString(1, "Ridley");
            prep.setString(2, "Scott");
            prep.executeUpdate();
            System.out.println("Personne 2 insérée : Ridley Scott.");

            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                int autoInc = rs.getInt(1);
                System.out.println("ID généré pour Ridley Scott : " + autoInc);
            }

            System.out.println("***** LISTE DES PERSONNES *****");
            SQLPrep = "SELECT * FROM Personne";
            PreparedStatement prep1 = connection.prepareStatement(SQLPrep);
            rs = prep1.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                System.out.println("-> (" + id + ") " + nom + ", " + prenom);
            }

            prep = connection.prepareStatement("DELETE FROM Personne WHERE ID=?");
            prep.setInt(1, 1);
            prep.execute();
            System.out.println("Personne avec ID 1 supprimée.");

            System.out.println("***** RÉCUPÉRATION PERSONNE 2 *****");
            SQLPrep = "SELECT * FROM Personne WHERE ID=?";
            prep1 = connection.prepareStatement(SQLPrep);
            prep1.setInt(1, 2);
            rs = prep1.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int id = rs.getInt("ID");
                System.out.println("-> (" + id + ") " + nom + ", " + prenom);
            }

            String updateQuery = "UPDATE Personne SET NOM=?, PRENOM=? WHERE ID=?";
            prep1 = connection.prepareStatement(updateQuery);
            prep1.setString(1, "R_i_d_l_e_y");
            prep1.setString(2, "S_c_o_t_t");
            prep1.setInt(3, 2);
            prep1.execute();
            System.out.println("Personne 2 mise à jour.");

            System.out.println("***** PERSONNE 2 APRÈS MODIFICATION *****");
            prep1 = connection.prepareStatement(SQLPrep);
            prep1.setInt(1, 2);
            rs = prep1.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int id = rs.getInt("ID");
                System.out.println("-> (" + id + ") " + nom + ", " + prenom);
            }

            String dropTable = "DROP TABLE Personne";
            stmt.executeUpdate(dropTable);
            System.out.println("Table Personne supprimée avec succès.");

            rs.close();
            prep.close();
            prep1.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("*** ERREUR SQL ***");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("*** ERREUR INCONNUE ***");
            e.printStackTrace();
        }
    }
}