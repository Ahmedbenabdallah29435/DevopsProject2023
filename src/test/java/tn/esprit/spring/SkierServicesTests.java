package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class SkierServicesTests {

    @InjectMocks
    private SkierServicesImpl skierService;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddSkier() {

        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skier.setCity("Sample City");

        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        skier.setSubscription(subscription);

        Mockito.when(skierRepository.save(Mockito.any())).thenReturn(skier);

        // Act
        Skier savedSkier = skierService.addSkier(skier);

        // Assert
        assertNotNull(savedSkier.getNumSkier());
        assertEquals("John", savedSkier.getFirstName());
        assertEquals("Doe", savedSkier.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), savedSkier.getDateOfBirth());
        assertEquals("Sample City", savedSkier.getCity());
        assertNotNull(savedSkier.getSubscription());
        assertEquals(TypeSubscription.ANNUAL, savedSkier.getSubscription().getTypeSub());
        assertEquals(LocalDate.now().plusYears(1), savedSkier.getSubscription().getEndDate());
    }

    @Test
     void testRemoveSkier() {
        // Arrange
        long skierId = 1L;

        // Act
        skierService.removeSkier(skierId);

        // Assert
        Mockito.verify(skierRepository, Mockito.times(1)).deleteById(skierId);
    }

    @Test
     void testRetrieveSkier() {
        // Arrange
        long skierId = 1L;
        Skier expectedSkier = new Skier();
        expectedSkier.setNumSkier(skierId);

        Mockito.when(skierRepository.findById(skierId)).thenReturn(Optional.of(expectedSkier));

        // Act
        Skier retrievedSkier = skierService.retrieveSkier(skierId);

        // Assert
        assertEquals(skierId, retrievedSkier.getNumSkier());
    }

    @Test
     void testRetrieveAllSkiers() {
        // Arrange
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier());
        skiers.add(new Skier());
        skiers.add(new Skier());

        Mockito.when(skierRepository.findAll()).thenReturn(skiers);

        // Act
        List<Skier> retrievedSkiers = skierService.retrieveAllSkiers();

        // Assert
        assertEquals(3, retrievedSkiers.size()); // Adjust the count as per your test data
    }

    @Test
     void testAssignSkierToSubscription() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        Subscription subscription = new Subscription();
        subscription.setNumSub(2L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusYears(1));

        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Mockito.when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));
        Mockito.when(skierRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        Skier updatedSkier = skierService.assignSkierToSubscription(1L, 2L);

        // Assert
        assertEquals(2L, updatedSkier.getSubscription().getNumSub());
        assertEquals(LocalDate.now(), updatedSkier.getSubscription().getStartDate());
        assertEquals(LocalDate.now().plusYears(1), updatedSkier.getSubscription().getEndDate());
    }
/*
    @Test
    public void testAddSkierAndAssignToCourse() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        Course course = new Course();
        course.setNumCourse(2L);

        Registration registration1 = new Registration();
        Registration registration2 = new Registration();

        skier.setRegistrations(new HashSet<>());
        skier.getRegistrations().add(registration1);
        skier.getRegistrations().add(registration2);

        Mockito.when(skierRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(courseRepository.getById(2L)).thenReturn(course);

        // Act
        Skier savedSkier = skierService.addSkierAndAssignToCourse(skier, 2L);

        // Assert
        assertEquals(2, savedSkier.getRegistrations().size());
        assertEquals(2L, savedSkier.getRegistrations().iterator().next().getCourse().getNumCourse());
        assertEquals(1L, savedSkier.getRegistrations().iterator().next().getSkier().getNumSkier());
    }
*/
    @Test
     void testAssignSkierToPiste() {
        // Arrange
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setPistes(new HashSet<>());

        Piste piste = new Piste();
        piste.setNumPiste(2L);

        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Mockito.when(pisteRepository.findById(2L)).thenReturn(Optional.of(piste));
        Mockito.when(skierRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        Skier updatedSkier = skierService.assignSkierToPiste(1L, 2L);

        // Assert
        assertNotNull(updatedSkier);
        assertEquals(1L, updatedSkier.getNumSkier());
        assertEquals(2L, updatedSkier.getPistes().iterator().next().getNumPiste());
    }

    @Test
     void testRetrieveSkiersByAnnualSubscription() {
        testRetrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL);
    }

    @Test
     void testRetrieveSkiersByMonthlySubscription() {
        testRetrieveSkiersBySubscriptionType(TypeSubscription.MONTHLY);
    }

    @Test
     void testRetrieveSkiersBySemestrielSubscription() {
        testRetrieveSkiersBySubscriptionType(TypeSubscription.SEMESTRIEL);
    }
    private void testRetrieveSkiersBySubscriptionType(TypeSubscription subscriptionType) {
        // Arrange
        List<Skier> skiers = new ArrayList<>();
        Skier skier1 = new Skier();
        skier1.setNumSkier(1L);
        skier1.setSubscription(new Subscription(subscriptionType));

        Skier skier2 = new Skier();
        skier2.setNumSkier(2L);
        skier2.setSubscription(new Subscription(subscriptionType));

        skiers.add(skier1);
        skiers.add(skier2);

        // Mock the repository method to return the sample skiers
        Mockito.when(skierRepository.findBySubscription_TypeSub(subscriptionType)).thenReturn(skiers);

        // Act
        List<Skier> retrievedSkiers = skierService.retrieveSkiersBySubscriptionType(subscriptionType);

        // Assert
        assertEquals(2, retrievedSkiers.size());
        assertEquals(1L, retrievedSkiers.get(0).getNumSkier());
        assertEquals(2L, retrievedSkiers.get(1).getNumSkier());
    }


}
