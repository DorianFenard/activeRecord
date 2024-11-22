package activeRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDB {


    @BeforeEach
    public void setUp() {
        DBConnection.setNomDb("testpersonne2");
        Personne.createTable();
    }

    @AfterEach
    public void end() {
        Personne.deleteTable();
    }

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
            DBConnection.setNomDb("testpersonne2");
            Connection connection = DBConnection.getConnection();
            assertNotNull(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}