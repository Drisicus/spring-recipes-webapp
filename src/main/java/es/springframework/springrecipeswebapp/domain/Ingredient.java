package es.springframework.springrecipeswebapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(){}
    public Ingredient(String description, double amount, UnitOfMeasure unitOfMeasure){
        this.description = description;
        this.amount = BigDecimal.valueOf(amount);
        this.unitOfMeasure = unitOfMeasure;
    }

}
