package gjshk.expenses.tracker;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestObjectBuilder {
    static public final LocalDate testDate =
            LocalDate.of(1996, Month.SEPTEMBER, 6);
    static public FixedCost dummyFixedCost() {
        return FixedCost.builder()
                .id(1L)
                .title("Rent")
                .value(700.0F)
                .plans(new HashSet<>())
                .build();
    }

    static public Plan dummyPlan() {
        return Plan.builder()
                .id(1L)
                .title("Test Plan")
                .salary(2000.0F)
                .desiredSavings(1000.0F)
                .createdOn(testDate)
                .fixedCosts(new HashSet<>())
                .build();
    }

    static public List<Plan> listOfDummyPlans() {
        Plan firstPlan = dummyPlan();
        Plan secondPlan = Plan.builder()
                .id(2L)
                .title("Test Plan 2")
                .salary(2000.0F)
                .desiredSavings(1000.0F)
                .createdOn(testDate)
                .fixedCosts(new HashSet<>())
                .build();

        return List.of(firstPlan, secondPlan);
    }

    static public List<FixedCost> listOfDummyFixedCosts() {
        FixedCost firstFixedCost = dummyFixedCost();
        FixedCost secondFixedCost = FixedCost.builder()
                .id(2L)
                .title("Insurance")
                .value(500.0F)
                .build();

        return List.of(firstFixedCost,secondFixedCost);
    }
}
