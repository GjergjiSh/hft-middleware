package gjshk.expenses.tracker.repositories;

import gjshk.expenses.tracker.entities.FixedCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedCostRepository extends JpaRepository<FixedCost, Long> {
    //List<FixedCost> findByPlanId(Long planId);
//
//    @Transactional
//    void deleteByPlanId(Long planId);

//    List<F>


}
