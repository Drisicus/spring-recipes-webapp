package es.springframework.springrecipeswebapp.converters;

import es.springframework.springrecipeswebapp.commands.RecipeCommand;
import es.springframework.springrecipeswebapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        Function<Recipe, RecipeCommand> conversion = src -> {

            final RecipeCommand command = new RecipeCommand();
            command.setId(src.getId());
            command.setCookingTime(src.getCookingTime());
            command.setPreparationTime(src.getPreparationTime());
            command.setDescription(src.getDescription());
            command.setDifficulty(src.getDifficulty());
            command.setDirections(src.getDirections());
            command.setNumberServings(src.getNumberServings());
            command.setSource(src.getSource());
            command.setUrl(src.getUrl());
            command.setImage(src.getImage());
            command.setNotes(notesConverter.convert(src.getNotes()));

            if (Objects.nonNull(src.getCategories()) && !src.getCategories().isEmpty()) {
                src.getCategories()
                        .forEach(category -> command.getCategories().add(categoryConveter.convert(category)));
            }

            if (Objects.nonNull(src.getIngredients()) && !src.getIngredients().isEmpty()) {
                src.getIngredients()
                        .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
            }
            return command;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
