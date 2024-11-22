package activeRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {

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

    }

    @AfterEach
    public void end() {
        Personne.deleteTable();
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
            Personne personne = Personne.findByName("Spielberg").get(0);
            assertNotNull(personne);
            assertEquals("Spielberg", personne.getNom());
            assertEquals("Steven", personne.getPrenom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSaveAndDelete() {
        try {
            Personne p = new Personne("Dujardin", "Jean");
            p.save();
            Personne personne = Personne.findByName("Dujardin").getFirst();
            personne.delete();
            assertEquals(personne.getId(),5);
            assertEquals(Personne.findByName("Dujardin").size(),0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindAll(){
        try{
            ArrayList<Personne> resultat = new ArrayList<>();
            resultat.add(new Personne("Spielberg", "Steven",1));
            resultat.add(new Personne("Scott", "Ridley",2));
            resultat.add(new Personne("Kubrick", "Stanley",3));
            resultat.add(new Personne("Fincher", "David",4));


            ArrayList<Personne> personnes = Personne.findAll();
            for(int i = 0; i < 3; i++){
                assertEquals(resultat.get(i).getNom(), personnes.get(i).getNom());
                assertEquals(resultat.get(i).getPrenom(), personnes.get(i).getPrenom());
                assertEquals(resultat.get(i).getId(), personnes.get(i).getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
