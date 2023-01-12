package gjshk.expenses.tracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FixedCost {
    @Id
    @SequenceGenerator(
            name = "fixed_cost_sequence",
            sequenceName = "fixed_cost_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fixed_cost_sequence"
    )
    private Long id;
    private String title;
    private float value;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "plan_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Plan plan;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "fixedCosts"
    )
    @JsonIgnore
    private Set<Plan> plans = new HashSet<>();

    public FixedCost(Long id, String title, float value) {
        this.id = id;
        this.title = title;
        this.value = value;
    }

    public FixedCost(String title, float value) {
        this.title = title;
        this.value = value;
    }

    @Override
    public String toString() {
        return "FixedCost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", value=" + value +
                '}';
    }
}
