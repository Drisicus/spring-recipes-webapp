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

            final RecipeCommand recipe = new RecipeCommand();
            recipe.setId(src.getId());
            recipe.setCookingTime(src.getCookingTime());
            recipe.setPreparationTime(src.getPreparationTime());
            recipe.setDescription(src.getDescription());
            recipe.setDifficulty(src.getDifficulty());
            recipe.setDirections(src.getDirections());
            recipe.setNumberServings(src.getNumberServings());
            recipe.setSource(src.getSource());
            recipe.setUrl(src.getUrl());
            recipe.setNotes(notesConverter.convert(src.getNotes()));

            if (Objects.nonNull(src.getCategories()) && !src.getCategories().isEmpty()) {
                src.getCategories()
                        .forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
            }

            if (Objects.nonNull(src.getIngredients()) && !src.getIngredients().isEmpty()) {
                src.getIngredients()
                        .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
            }
            return recipe;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
