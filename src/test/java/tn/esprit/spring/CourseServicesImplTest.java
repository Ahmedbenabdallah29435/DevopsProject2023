package tn.esprit.spring;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServicesImplTest {
    @InjectMocks
    CourseServicesImpl courseServices;
    @Mock
    ICourseRepository  courseRepository;

    @Test
    public void testRetrieveAllCourses() {

        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        course.setSupport(Support.SKI);
        course.setPrice(50f);
        course.setTimeSlot(2);
        courseServices.addCourse(course);

        // Appelez la méthode pour récupérer tous les cours
        List<Course> listCourses = courseServices.retrieveAllCourses();
               System.out.println(course);
        // Vérifiez si la liste de cours retournée n'est pas nulle
        assertNotNull(listCourses);

        }

    @Test
    public void testAddCourse() {
        // Création d'une instance de Course à sauvegarder
        Course courseToSave = new Course();
        courseToSave.setLevel(1);
        courseToSave.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        courseToSave.setSupport(Support.SKI);
        courseToSave.setPrice(50f);
        courseToSave.setTimeSlot(2);
        // Configurer le comportement du repository mock pour retourner le cours sauvegardé
        when(courseRepository.save(courseToSave)).thenReturn(courseToSave);

        // Appel de la méthode à tester
        Course savedCourse = courseServices.addCourse(courseToSave);

        // Vérifier si la méthode save a été appelée une fois avec le cours à sauvegarder
        verify(courseRepository, times(1)).save(courseToSave);

        // Vérifier si le cours retourné est celui qui a été sauvegardé
        assertEquals(courseToSave.getNumCourse(), savedCourse.getNumCourse());
        assertEquals(courseToSave.getTypeCourse(), savedCourse.getTypeCourse());
    }
    @Test
    public void testUpdateCourse() {
        // Création d'une instance de Course à mettre à jour
        Course courseToUpdate = new Course();
        courseToUpdate.setNumCourse(1L);
        courseToUpdate.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        // Configurer le comportement du repository mock pour retourner le cours mis à jour
        when(courseRepository.save(courseToUpdate)).thenReturn(courseToUpdate);

        // Appel de la méthode à tester
        Course updatedCourse = courseServices.updateCourse(courseToUpdate);

        // Vérifier si la méthode save a été appelée une fois avec le cours à mettre à jour
        verify(courseRepository, times(1)).save(courseToUpdate);

        // Vérifier si le cours retourné est celui qui a été mis à jour
        assertEquals(courseToUpdate.getNumCourse(), updatedCourse.getNumCourse());
        assertEquals(courseToUpdate.getPrice(), updatedCourse.getPrice());
    }
    @Test
    public void testRetrieveCourse() {
        // Création d'un identifiant de cours factice pour simuler la recherche
        Long courseId = 1L;

        // Création d'un cours factice pour simuler le retour du repository
        Course course = new Course();
        course.setNumCourse(courseId);
        // Ajoutez les propriétés nécessaires pour votre test
        // ...

        // Configurer le comportement du repository mock pour retourner le cours factice lorsqu'on lui passe l'ID factice
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Appel de la méthode à tester
        Course retrievedCourse = courseServices.retrieveCourse(courseId);

        // Vérifier si la méthode findById a été appelée une fois avec l'ID du cours
        verify(courseRepository, times(1)).findById(courseId);

        // Vérifier si le cours retourné est celui qui a été simulé
        assertNotNull(retrievedCourse);
        assertEquals(courseId, retrievedCourse.getNumCourse());
        // Ajoutez d'autres assertions pour vérifier les propriétés du cours si nécessaire
        // ...
    }
    }

