package gjshk.expenses.tracker.unit.services;

import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.exceptions.AlreadyAssignedException;
import gjshk.expenses.tracker.exceptions.ResourceNotFoundException;
import gjshk.expenses.tracker.repositories.PlanRepository;
import gjshk.expenses.tracker.services.PlanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    @Mock
    private PlanRepository mockedPlanRepository;
    @InjectMocks
    private PlanService mockedPlanService;

    @Test
    @DisplayName("JUnit test for assignFixedCost method")
    void assignFixedCostTest() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        Plan plan = TestObjectBuilder.dummyPlan();

        given(mockedPlanRepository.save(plan))
                .willReturn(plan);

        Plan planWithAssignedFixedCost = mockedPlanService.assignFixedCost(plan, fixedCost);

        Assertions.assertTrue(planWithAssignedFixedCost.getFixedCosts().contains(fixedCost));
        Assertions.assertEquals(1300, planWithAssignedFixedCost.getCurrentBalance());
        Assertions.assertEquals(10, planWithAssignedFixedCost.getDailyAllowance());
    }

    @Test
    @DisplayName("JUnit test for assignFixedCost method which throws exception")
    void assignInvalidFixedCostTest() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        Plan plan = TestObjectBuilder.dummyPlan();

        mockedPlanService.assignFixedCost(plan, fixedCost);
        Assertions.assertThrows(
                AlreadyAssignedException.class,
                () -> mockedPlanService.assignFixedCost(plan, fixedCost),
                "No/Wrong thrown when assigned fixed cost was re-assigned to the plan"
        );
    }

    @Test
    @DisplayName("JUnit test for addNewPlan method")
    void addNewPlanTest() {
        Plan plan = TestObjectBuilder.dummyPlan();
        given(mockedPlanRepository.save(plan))
                .willReturn(plan);

        Plan addedPlan = mockedPlanService.addNewPlan(plan);
        Assertions.assertNotNull(addedPlan);
    }

    @Test
    @DisplayName("JUnit test for getPlanById")
    void getPlanByIdTest() {
        Plan plan = TestObjectBuilder.dummyPlan();
        given(mockedPlanRepository.findById(1L))
                .willReturn(Optional.of(plan));

        Plan planFoundById = mockedPlanService
                .getPlanById(plan.getId());

        Assertions.assertNotNull(planFoundById);
    }

    @Test
    @DisplayName("JUnit test for getPlanById which should throw exception")
    void getPlanByIdWithInvalidIdTest() {
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mockedPlanService.getPlanById(-1L),
                "No/Wrong error thrown when getting plan with invalid id"
        );
    }

    @Test
    @DisplayName("JUnit test for getPlanByTitle")
    void getPlanByTitleTest() {
        Plan plan = TestObjectBuilder.dummyPlan();
        given(mockedPlanRepository.findByTitle(plan.getTitle()))
                .willReturn(Optional.of(plan));

        Plan planFoundByTitle = mockedPlanService
                .getPlanByTitle(plan.getTitle());

        Assertions.assertNotNull(planFoundByTitle);
    }

    @Test
    @DisplayName("JUnit test for getPlanByTitle which should throw exception")
    void getPlanByTitleWithInvalidTitleTest() {
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mockedPlanService.getPlanByTitle("Invalid Title"),
                "No/Wrong error thrown when getting plan with invalid date"
        );
    }

    @Test
    @DisplayName("JUnit test for getPlanByCreatedOn")
    void getPlanByCreatedOn() {
        Plan plan = TestObjectBuilder.dummyPlan();
        given(mockedPlanRepository.findByCreatedOn(
                plan.getCreatedOn()))
                .willReturn(Optional.of(plan));

        Plan planFoundByCreatedOn = mockedPlanService
                .getPlanByCreatedOn(plan.getCreatedOn());

        Assertions.assertNotNull(planFoundByCreatedOn);
    }

    @Test
    @DisplayName("JUnit test for getPlanByCreatedOn which should throw exception")
    void getPlanByCreatedOnWithInvalidDate() {
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mockedPlanService.getPlanByCreatedOn(
                        LocalDate.of(1,1,1)
                ),
                "No/Wrong error thrown when getting plan with invalid date"
        );
    }

    @Test
    @DisplayName("JUnit test for getPlans")
    void getPlansTest() {
        Plan firstPlan = TestObjectBuilder.dummyPlan();
        Plan secondPlan = TestObjectBuilder.dummyPlan();
        secondPlan.setId(2L);
        secondPlan.setTitle("Test Plan 2");

        given(mockedPlanRepository.findAll())
                .willReturn(List.of(firstPlan,secondPlan));

        List<Plan> readPlans = mockedPlanService.getPlans();
        Assertions.assertNotNull(readPlans);
        Assertions.assertEquals(2, readPlans.size());
    }
}
