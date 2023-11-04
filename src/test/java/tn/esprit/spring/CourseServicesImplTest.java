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
import static org.junit.jupiter.api.Assertions.*;
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
    }

