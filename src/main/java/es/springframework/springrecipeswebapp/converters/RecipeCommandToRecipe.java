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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        Function<RecipeCommand, Recipe> conversion = src -> {

            final Recipe recipe = new Recipe();
            recipe.setId(src.getId());
            recipe.setCookingTime(src.getCookingTime());
            recipe.setPreparationTime(src.getPreparationTime());
            recipe.setDescription(src.getDescription());
            recipe.setDifficulty(src.getDifficulty());
            recipe.setDirections(src.getDirections());
            recipe.setNumberServings(src.getNumberServings());
            recipe.setSource(src.getSource());
            recipe.setUrl(src.getUrl());

            if(Objects.nonNull(src.getNotes())) {
                recipe.setNotes(notesConverter.convert(src.getNotes()));
            }

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
