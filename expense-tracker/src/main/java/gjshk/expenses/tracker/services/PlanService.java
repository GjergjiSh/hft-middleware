package gjshk.expenses.tracker.services;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.exceptions.AlreadyAssignedException;
import gjshk.expenses.tracker.exceptions.ResourceNotFoundException;
import gjshk.expenses.tracker.repositories.PlanRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> getPlans() {
        return planRepository.findAll();
    }

    public Plan getPlanByTitle(String title) {
        Optional<Plan> planOptional = planRepository
                .findByTitle(title);

        if (planOptional.isPresent()) {
            return planOptional.get();
        } else {
            throw new ResourceNotFoundException(
                    "Plan Service: No plan found for provided title"
                     + title
            );
        }
    }

    public Plan getPlanByCreatedOn(LocalDate createdOn) {
        //@TODO Check if createdOn null or invalid?
        Optional<Plan> planOptional = planRepository
                .findByCreatedOn(createdOn);

        if (planOptional.isPresent()) {
            return planOptional.get();
        } else {
            throw new ResourceNotFoundException(
                    "Plan Service: No plan found for provided date"
                    + createdOn
            );
        }
    }

    public Plan getPlanById(Long id) {
        Optional<Plan> planOptional = planRepository
                .findById(id);

        if (planOptional.isPresent()) {
            return planOptional.get();
        } else {
            throw new ResourceNotFoundException(
                    "PlanService: Failed to find plan by id "
                            + id
            );
        }
    }

    public Plan addNewPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan assignFixedCost(Plan plan, FixedCost fixedCost) {
        if (plan.containsFixedCost(fixedCost)) {
           throw new AlreadyAssignedException(
                   "Plan Service: Fixed cost is already assigned to plan");
        }

        plan.addFixedCost(fixedCost);
        return planRepository.save(plan);
    }
}
