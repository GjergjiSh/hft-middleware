package gjshk.expenses.tracker.controllers;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.exceptions.AlreadyAssignedException;
import gjshk.expenses.tracker.exceptions.ApiExceptionHandler;
import gjshk.expenses.tracker.services.FixedCostService;
import gjshk.expenses.tracker.services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/plans")
public class PlanController {
    private final PlanService planService;
    private final FixedCostService fixedCostService;

    @GetMapping
    public ResponseEntity<Object> getPlans() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planService.getPlans());
    }

    @GetMapping(path = "/title/{title}")
    public ResponseEntity<Object> getPlanByTitle(@PathVariable("title") String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planService.getPlanByTitle(title));
    }

    @GetMapping(path = "/createdOn/{createdOn}")
    public ResponseEntity<Object> getPlanByCreatedOn(@PathVariable("createdOn") String createdOn) {
        LocalDate createdOnLocalDate = LocalDate.parse(createdOn);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planService.getPlanByCreatedOn(createdOnLocalDate));
    }

    @PostMapping
    public ResponseEntity<Object> addNewPlan(@RequestBody Plan plan) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planService.addNewPlan(plan));
    }

    @PostMapping("/{planId}/fixedcosts/{fixedCostId}")
    public ResponseEntity<Object> addFixedCostToPlan(@PathVariable Long planId, @PathVariable Long fixedCostId) {
        Plan plan = planService.getPlanById(planId);
        FixedCost fixedCost = fixedCostService.getFixedCostById(fixedCostId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planService.assignFixedCost(plan, fixedCost));
    }


}
