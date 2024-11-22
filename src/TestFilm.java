import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestFilm {

    @BeforeEach
    public void setUp() {
        DBConnection.setNomDb("testpersonne2");
        Personne.createTable();

        Personne p1 = new Personne("Spielberg", "Steven");
        p1.save();
        Personne p2 = new Personne("Scott", "Ridley");
        p2.save();
        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.save();
        Personne p4 = new Personne("Fincher", "David");
        p4.save();

        Film.createTable();

        Film film = new Film("Arche perdue", 1);
        film.save();
        Film film2 = new Film("Alien", 2);
        film2.save();
        Film film3 = new Film("Temple Maudit", 1);
        film3.save();
        Film film4 = new Film("Blade Runner", 2);
        film4.save();
        Film film5 = new Film("Alien3", 4);
        film5.save();
        Film film6 = new Film("Fight Club", 4);
        film6.save();
        Film film7 = new Film("Orange Mécanique", 3);
        film7.save();
    }

    @AfterEach
    public void end() {
       Film.deleteTable();
       Personne.deleteTable();
    }

    @Test
    public void testFindById() {
        try {
            Film film = Film.findById(1);
            assertNotNull(film);
            assertEquals("Arche perdue", film.getTitre());
            assertEquals("Spielberg", film.getRealisateur().getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSave(){
        try{
            Film film = new Film("Super film",-1);
            film.save();
        } catch (Exception e) {
            assertEquals(1,1);
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testFindByRealisateur() throws SQLException {
        Personne personne = Personne.findById(1);
        ArrayList<Film> films = Film.findByRealisateur(personne);
        assertEquals("Arche perdue",films.get(0).getTitre());
        assertEquals("Temple Maudit",films.get(1).getTitre());
        assertEquals(1,films.get(0).getId());
        assertEquals(3,films.get(1).getId());
    }
}
