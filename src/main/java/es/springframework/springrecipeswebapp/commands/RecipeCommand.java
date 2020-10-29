package es.springframework.springrecipeswebapp.commands;

import es.springframework.springrecipeswebapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer preparationTime;

    @Min(1)
    @Max(999)
    private Integer cookingTime;

    @Min(1)
    @Max(100)
    private Integer numberServings;

    private String url;

    @NotBlank
    private String directions;

    private String source;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Byte[] image;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

}
