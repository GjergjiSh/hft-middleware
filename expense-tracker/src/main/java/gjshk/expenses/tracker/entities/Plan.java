package gjshk.expenses.tracker.entities;

import gjshk.expenses.tracker.entities.FixedCost;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    @SequenceGenerator(
            name = "plan_sequence",
            sequenceName = "plan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plan_sequence"
    )

    private Long id;
    //@Column(unique = true)
    private String title;
    private float salary;
    private float desiredSavings;
    private float currentBalance;
    private float dailyAllowance;
    //@Column(unique = true)


    //@TODO Cascade Types?
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "registered_fixed_costs",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "fixed_cost_id")
    )
    private Set<FixedCost> fixedCosts = new HashSet<>();
    private LocalDate createdOn;

    public Plan(Long id, String title, float salary,
                float desiredSavings, float currentBalance,
                float dailyAllowance, LocalDate createdOn) {
        this.id = id;
        this.title = title;
        this.salary = salary;
        this.desiredSavings = desiredSavings;
        this.currentBalance = currentBalance;
        this.dailyAllowance = dailyAllowance;
        this.createdOn = createdOn;
    }

    public Plan(String title, float salary,
                float desiredSavings, LocalDate createdOn) {
        this.title = title;
        this.salary = salary;
        this.desiredSavings = desiredSavings;
        this.createdOn = createdOn;
    }

    public Plan(Long id, String title, float salary,
                float desiredSavings, LocalDate createdOn) {
        this.id = id;
        this.title = title;
        this.salary = salary;
        this.desiredSavings = desiredSavings;
        this.createdOn = createdOn;
    }

    private float calculateFixedCostsSum() {
        return getFixedCosts()
                .stream()
                .map(FixedCost::getValue)
                .reduce((float) 0, Float::sum);
    }

    private void updateDailyAllowance(float costsSum) {
        this.dailyAllowance = (getSalary() - getDesiredSavings() - costsSum)
                / getCreatedOn().lengthOfMonth();
    }

    private void updateCurrentBalance(float costsSum) {
        this.currentBalance = this.salary - costsSum;
    }

    public void addFixedCost(FixedCost fixedCost) {
        fixedCosts.add(fixedCost);
        float sumOfPlanFixedCosts = calculateFixedCostsSum();
        //System.out.println(sumOfPlanFixedCosts);

        updateDailyAllowance(sumOfPlanFixedCosts);
        updateCurrentBalance(sumOfPlanFixedCosts);
    }

    public boolean containsFixedCost(FixedCost fixedCost) {
        return fixedCosts.contains(fixedCost);
    }


}
