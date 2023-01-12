package gjshk.expenses.tracker.unit.controllers;

import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.controllers.PlanController;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.services.FixedCostService;
import gjshk.expenses.tracker.services.PlanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlanControllerTest {
    @Mock
    private FixedCostService mockedFixedCostService;
    @Mock
    private PlanService mockedPlanService;
    @InjectMocks
    private PlanController mockedPlanController;

    @Test
    @DisplayName("JUnit test for getPlans")
    void getPlansTest() {
        List<Plan> dummyPlans = TestObjectBuilder.listOfDummyPlans();
        given(mockedPlanService
                .getPlans())
                .willReturn(dummyPlans);

        ResponseEntity<Object> listOfPlans = mockedPlanController
                .getPlans();

        Assertions.assertEquals(
                HttpStatus.OK,
                listOfPlans.getStatusCode()
        );

        Assertions.assertSame(
                dummyPlans,
                listOfPlans.getBody()
        );
    }

    @Test
    @DisplayName("JUnit test for getPlanByTitle")
    void getPlanByTitleTest() {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();
        given(mockedPlanService
                .getPlanByTitle(dummyPlan.getTitle()))
                .willReturn(dummyPlan);

        ResponseEntity<Object> planByTitle = mockedPlanController
                .getPlanByTitle(dummyPlan.getTitle());

        Assertions.assertEquals(
                HttpStatus.OK,
                planByTitle.getStatusCode()
        );

        Assertions.assertSame(
                dummyPlan,
                planByTitle.getBody()
        );
    }

    @Test
    @DisplayName("JUnit test for getPlanByCreatedOn")
    void getPlanByCreatedOnTest() {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();
        given(mockedPlanService
                .getPlanByCreatedOn(TestObjectBuilder.testDate))
                .willReturn(dummyPlan);

        ResponseEntity<Object> planByCreatedOn =  mockedPlanController
                .getPlanByCreatedOn(TestObjectBuilder.testDate.toString());

        Assertions.assertEquals(
                HttpStatus.OK,
                planByCreatedOn.getStatusCode()
        );

        Assertions.assertSame(
                dummyPlan,
                planByCreatedOn.getBody()
        );
    }

    @Test
    @DisplayName("JUnit test for addNewPlan")
    void addNewPlanTest() {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();
        given(mockedPlanService.addNewPlan(dummyPlan))
                .willReturn(dummyPlan);

        ResponseEntity<Object> addedPlan = mockedPlanController
                .addNewPlan(dummyPlan);

        Assertions.assertEquals(
                HttpStatus.OK,
                addedPlan.getStatusCode()
        );

        Assertions.assertSame(
                dummyPlan,
                addedPlan.getBody()
        );
    }

    @Test
    @DisplayName("JUnit test for addFixedCostToPlan")
    void addFixedCostToPlanTest() {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();
        FixedCost dummyFixedCost = TestObjectBuilder.dummyFixedCost();

        given(mockedFixedCostService
                .getFixedCostById(dummyFixedCost.getId()))
                .willReturn(dummyFixedCost);

        given(mockedPlanService
                .getPlanById(dummyPlan.getId()))
                .willReturn(dummyPlan);

        given(mockedPlanService.assignFixedCost(dummyPlan, dummyFixedCost))
                .willAnswer(invocationOnMock -> {
                    dummyPlan.addFixedCost(dummyFixedCost);
                    return dummyPlan;
                });

        ResponseEntity<Object> planWithAssignedFixCost =
                mockedPlanController.addFixedCostToPlan(
                        dummyPlan.getId(),
                        dummyFixedCost.getId());

        Assertions.assertEquals(
                HttpStatus.OK,
                planWithAssignedFixCost.getStatusCode()
        );

        Assertions.assertSame(
                dummyPlan,
                planWithAssignedFixCost.getBody()
        );
    }

}
