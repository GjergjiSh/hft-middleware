package gjshk.expenses.tracker.controllers;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.services.FixedCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/fixedcosts")
public class FixedCostController {

    private final FixedCostService fixedCostService;

    @GetMapping
    public ResponseEntity<Object> getFixedCosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fixedCostService.getFixedCosts());
    }

    @PostMapping
    public ResponseEntity<Object> addNewFixedCost(@RequestBody FixedCost fixedCost) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fixedCostService.addNewFixedCost(fixedCost));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> setFixedCostValue(@RequestParam("id") Long id,
                                                    @RequestParam("newValue") float newValue) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fixedCostService.setFixedCostValue(id, newValue));
    }

}
