package gjshk.expenses.tracker.unit.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.controllers.FixedCostController;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.services.FixedCostService;
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
public class FixedCostControllerTest {
    @Mock
    private FixedCostService mockedFixedCostService;
    @InjectMocks
    private FixedCostController mockedFixedCostController;

    @Test
    @DisplayName("JUnit Test for getFixedCosts")
    void getFixedCostsTest() {
        List<FixedCost> dummyFixedCosts = TestObjectBuilder.listOfDummyFixedCosts();
        given(mockedFixedCostService.getFixedCosts())
                .willReturn(dummyFixedCosts);

        ResponseEntity<Object> fixedCostList = mockedFixedCostController
                .getFixedCosts();

        Assertions.assertEquals(
                HttpStatus.OK,
                fixedCostList.getStatusCode());

        Assertions.assertSame(
                dummyFixedCosts,
                fixedCostList.getBody()
        );
    }

    @Test
    @DisplayName("JUnit Test for addNewFixedCost")
    void addNewFixedCostTest() {
        FixedCost dummyFixedCost = TestObjectBuilder.dummyFixedCost();
        given(mockedFixedCostService.addNewFixedCost(dummyFixedCost))
                .willReturn(dummyFixedCost);

        ResponseEntity<Object> addedFixedCost = mockedFixedCostController
                .addNewFixedCost(dummyFixedCost);

        Assertions.assertEquals(
                HttpStatus.OK,
                addedFixedCost.getStatusCode()
        );

        Assertions.assertSame(
                dummyFixedCost,
                addedFixedCost.getBody()
        );
    }
}
