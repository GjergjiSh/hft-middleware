package gjshk.expenses.tracker.services;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.exceptions.AlreadyAssignedException;
import gjshk.expenses.tracker.exceptions.ResourceNotFoundException;
import gjshk.expenses.tracker.repositories.FixedCostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FixedCostService {
    @Getter
    private final FixedCostRepository fixedCostRepository;

    public List<FixedCost> getFixedCosts() {
        return fixedCostRepository.findAll();
    }

    public FixedCost addNewFixedCost(FixedCost fixedCost) {
        return fixedCostRepository.save(fixedCost);
    }

    public FixedCost getFixedCostById(Long id) {
        Optional<FixedCost> fixedCostOptional = fixedCostRepository
                .findById(id);

        if (fixedCostOptional.isPresent()) {
            return fixedCostOptional.get();
        } else {
            throw new ResourceNotFoundException(
                    "FixedCostService: Failed to find fixed cost for id"
                    + id
            );
        }
    }

    public FixedCost setFixedCostValue(Long id, float newValue) {
        FixedCost fixedCost = getFixedCostById(id);
        if (!fixedCost.getPlans().isEmpty()) {
            throw new AlreadyAssignedException(
                    "This fixed cost has already been assigned " +
                    "to a plan and its value  can't be changed");
        }

        fixedCost.setValue(newValue);
        return fixedCostRepository.save(fixedCost);
    }
}
