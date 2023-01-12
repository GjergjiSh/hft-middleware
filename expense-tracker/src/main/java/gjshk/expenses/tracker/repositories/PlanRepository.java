package gjshk.expenses.tracker.repositories;

import gjshk.expenses.tracker.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByCreatedOn(LocalDate createdOn);
    Optional<Plan> findByTitle(String title);
}
