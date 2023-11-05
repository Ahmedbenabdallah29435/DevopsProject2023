package tn.esprit.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)

public class PisteServiceTest {
    @InjectMocks
    PisteServicesImpl pisteService;
    @Mock
    IPisteRepository pisteRepository;



    @Test

    public void testRetrieveAllPistes() {
        // Créez des données factices (pistes) pour simuler ce que le dépôt renverrait

        Piste piste = new Piste();
        piste.setNamePiste("Piste1 ");
        piste.setColor(Color.BLACK);
        piste.setLength(10);
        piste.setSlope(10);
        pisteService.addPiste(piste);


        // Appelez la méthode pour récupérer tous les cours
        List<Piste> listPistes = pisteService.retrieveAllPistes();
        System.out.println(piste);
        // Vérifiez si la liste de cours retournée n'est pas nulle
        assertNotNull(listPistes);

    }

}
