package gjshk.expenses.tracker.configurations;

import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.repositories.FixedCostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FixedCostConfig {
    @Bean
    CommandLineRunner FixedCostCommandLineRunner(FixedCostRepository repository) {
        return args -> {
            FixedCost fx1 = new FixedCost("Rent", 540);
            FixedCost fx2 = new FixedCost("Insurance", 150);

            repository.saveAll(List.of(fx1, fx2));
        };
    }
}
