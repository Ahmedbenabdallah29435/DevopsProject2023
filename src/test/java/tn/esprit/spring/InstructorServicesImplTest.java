package tn.esprit.spring;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

@SpringBootTest
public class InstructorServicesImplTest {

    @InjectMocks
    private InstructorServicesImpl instructorService;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorService.addInstructor(instructor);
        verify(instructorRepository).save(instructor);

        // Vous pouvez ajouter des assertions supplémentaires ici en fonction de votre cas d'utilisation
    }

    @Test
    public void testRetrieveAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> retrievedInstructors = instructorService.retrieveAllInstructors();
        verify(instructorRepository).findAll();

        // Vous pouvez ajouter des assertions supplémentaires ici en fonction de votre cas d'utilisation
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorService.updateInstructor(instructor);
        verify(instructorRepository).save(instructor);

        // Vous pouvez ajouter des assertions supplémentaires ici en fonction de votre cas d'utilisation
    }

    @Test
    public void testRetrieveInstructor() {
        Long numInstructor = 1L;
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(numInstructor)).thenReturn(Optional.of(instructor));

        Instructor retrievedInstructor = instructorService.retrieveInstructor(numInstructor);
        verify(instructorRepository).findById(numInstructor);

        // Vous pouvez ajouter des assertions supplémentaires ici en fonction de votre cas d'utilisation
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        Long numCourse = 1L;
        Instructor instructor = new Instructor();
        Course course = new Course();
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorService.addInstructorAndAssignToCourse(instructor, numCourse);
        verify(courseRepository).findById(numCourse);
        verify(instructorRepository).save(instructor);

        // Vous pouvez ajouter des assertions supplémentaires ici en fonction de votre cas d'utilisation
    }
}