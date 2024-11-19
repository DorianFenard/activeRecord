import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDB {

    @Test
    public void testConnexion() {
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
        Connection connection2 = DBConnection.getConnection();
        assertEquals(connection.toString(), connection2.toString());
    }

    @Test
    public void testChangeDB() {
        try {
            DBConnection.setNomDb("test");
            Connection connection = DBConnection.getConnection();
            assertNotNull(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindById() {
        try {
            Personne personne = Personne.findById(1);
            assertNotNull(personne);
            assertEquals("Spielberg", personne.getNom());
            assertEquals("Steven", personne.getPrenom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByName() {
        try {
            Personne personne = Personne.findByName("Spielberg");
            assertNotNull(personne);
            assertEquals("Spielberg", personne.getNom());
            assertEquals("Steven", personne.getPrenom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() {
        try {
            ArrayList<Personne> personnes = Personne.findAll();
            assertNotNull(personnes);
            assertEquals(4, personnes.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}