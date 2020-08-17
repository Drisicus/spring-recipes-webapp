package es.springframework.springrecipeswebapp.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import es.springframework.springrecipeswebapp.domain.Category;
import es.springframework.springrecipeswebapp.domain.Difficulty;
import es.springframework.springrecipeswebapp.domain.Ingredient;
import es.springframework.springrecipeswebapp.domain.Notes;
import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.domain.UnitOfMeasure;
import es.springframework.springrecipeswebapp.repositories.CategoryRepository;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import es.springframework.springrecipeswebapp.repositories.UnitOfMeasureReporitory;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    RecipeRepository recipeRepository;
    CategoryRepository categoryRepository;
    UnitOfMeasureReporitory unitOfMeasureReporitory;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureReporitory unitOfMeasureReporitory){
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureReporitory = unitOfMeasureReporitory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(createRecipes());
    }

    private List<Recipe> createRecipes(){

        List<Recipe> recipes = new ArrayList<>();

        Optional<Category> mexicanCategory = categoryRepository.findByDescription("Mexican");

        Optional<UnitOfMeasure> tablespoonUOM = unitOfMeasureReporitory.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> teaspoonUOM = unitOfMeasureReporitory.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> dashUOM = unitOfMeasureReporitory.findByDescription("Dash");
        Optional<UnitOfMeasure> unitUOM = unitOfMeasureReporitory.findByDescription("Unit");

        boolean allElementsPresent = Stream.of(mexicanCategory, tablespoonUOM, teaspoonUOM, dashUOM, unitUOM).allMatch(Optional::isPresent);
        if(allElementsPresent){
            Recipe guacamole = new Recipe();

            guacamole.getCategories().add(mexicanCategory.get());

            Ingredient avocado = new Ingredient("Avocado", 2, unitUOM.get());
            Ingredient salt = new Ingredient("Salt", 0.25, teaspoonUOM.get());
            Ingredient chile = new Ingredient("Serrano chiles", 2, unitUOM.get());
            Ingredient blackPepper = new Ingredient("Black pepper", 1, dashUOM.get());
            Ingredient cilantro = new Ingredient("Cilantro", 2, tablespoonUOM.get());

            guacamole.addIngredient(avocado);
            guacamole.addIngredient(salt);
            guacamole.addIngredient(chile);
            guacamole.addIngredient(blackPepper);
            guacamole.addIngredient(cilantro);

            Notes guacamoleNote = new Notes();
            guacamoleNote.setRecipeNotes("For an extry spicy guacamole put a 1L bottle of tabasco");
            guacamole.setNotes(guacamoleNote);

            guacamole.setPreparationTime(5);
            guacamole.setCookingTime(0);
            guacamole.setDifficulty(Difficulty.EASY);
            guacamole.setDirections("Chop chop chop, put the kitchen on fire, extinguish fire, order online, eat");
            guacamole.setNumberServings(4);
            guacamole.setDescription("Guacamole");
            guacamole.setUrl("https://www.not-good-recipes.com/recipes/not_perfect_guacamole/");

            recipes.add(guacamole);

        } else {
            throw new RuntimeException("Missing elements for recipe");
        }

        return recipes;
    }

}