package gjshk.expenses.tracker.unit.services;

import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.exceptions.AlreadyAssignedException;
import gjshk.expenses.tracker.exceptions.ResourceNotFoundException;
import gjshk.expenses.tracker.repositories.FixedCostRepository;
import gjshk.expenses.tracker.services.FixedCostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FixedCostServiceTest {
    @Mock
    private FixedCostRepository mockedFixedCostRepository;
    @InjectMocks
    private FixedCostService mockedFixedCostService;

    @Test
    @DisplayName("JUnit test for getFixedCosts")
    void getFixedCostsTest() {
        FixedCost firstFixedCost = TestObjectBuilder.dummyFixedCost();
        FixedCost secondFixedCost = TestObjectBuilder.dummyFixedCost();
        secondFixedCost.setId(2L);
        secondFixedCost.setTitle("Insurance");

        given(mockedFixedCostRepository.findAll())
                .willReturn(List.of(secondFixedCost,firstFixedCost));

        List<FixedCost> readFixedCosts = mockedFixedCostService.getFixedCosts();
        Assertions.assertNotNull(readFixedCosts);
        Assertions.assertEquals(2, readFixedCosts.size());
    }

    @Test
    @DisplayName("JUnit test for addNewFixedCost method")
    void addNewFixedCostTest() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        given(mockedFixedCostRepository.save(fixedCost)).willReturn(fixedCost);
        FixedCost addedFixedCost = mockedFixedCostService.addNewFixedCost(fixedCost);
        Assertions.assertNotNull(addedFixedCost);
    }

    @Test
    @DisplayName("JUnit test for getFixedCostById")
    void getFixedCostById() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        given(mockedFixedCostRepository.findById(fixedCost.getId()))
                .willReturn(Optional.of(fixedCost));

        FixedCost fixedCostFoundById = mockedFixedCostService
                .getFixedCostById(fixedCost.getId());

        Assertions.assertNotNull(fixedCostFoundById);
    }

    @Test
    @DisplayName("JUnit test for getFixedCostById which should throw exception")
    void getFixedCostByIdWithInvalidId() {
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mockedFixedCostService.getFixedCostById(-1L),
                "No/Wrong error thrown when getting fixed cost with invalid id"
        );
    }

    @Test
    @DisplayName("JUnit test for setFixedCostValue")
    void setFixedCostValueTest() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        given(mockedFixedCostRepository.findById(fixedCost.getId()))
                .willReturn(Optional.of(fixedCost));

        //@TODO Fixme
        //FixedCost fixedCostWithChangedValue =
         mockedFixedCostService.setFixedCostValue(
                 fixedCost.getId(), 500
         );

         Assertions.assertEquals(
                 500, fixedCost.getValue()
         );
    }

    @Test
    @DisplayName("JUnit test for setFixedCostValue which should throw en exception")
    void setFixedCostValueFixedCostAlreadyAssigned() {
        FixedCost fixedCost = TestObjectBuilder.dummyFixedCost();
        fixedCost.getPlans().add(new Plan());

        given(mockedFixedCostRepository.findById(fixedCost.getId()))
                .willReturn(Optional.of(fixedCost));

        Assertions.assertThrows(
                AlreadyAssignedException.class,
                () -> mockedFixedCostService.setFixedCostValue(
                        fixedCost.getId(), 500
                ),
                "No/Wrong error thrown when changing the value " +
                        "of an already assigned fixed cost"
        );
    }

}
