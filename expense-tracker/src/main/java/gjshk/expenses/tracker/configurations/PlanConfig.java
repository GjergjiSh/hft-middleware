package gjshk.expenses.tracker.configurations;

import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.repositories.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class PlanConfig {
    @Bean
    CommandLineRunner PlanCommandLineRunner(PlanRepository repository) {
        return args -> {
            Plan p1 = new Plan(
                    "t1",
                    2400,
                    150,
                    LocalDate.of(1996, Month.OCTOBER, 8)
            );
            Plan p2 = new Plan(
                    "Test title 2",
                    2401,
                    140,
                    LocalDate.of(1997, Month.OCTOBER, 8)
            );

            repository.saveAll(List.of(p1,p2));
        };
    }
}
