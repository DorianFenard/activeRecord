import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDB {

    @Test
    public void testConnexion(){
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
        Connection connection2  = DBConnection.getConnection();
        assertEquals(connection.toString(), connection2.toString());

    }
}
