package es.springframework.springrecipeswebapp.bootstrap;

import es.springframework.springrecipeswebapp.domain.*;
import es.springframework.springrecipeswebapp.repositories.CategoryRepository;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import es.springframework.springrecipeswebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    RecipeRepository recipeRepository;
    CategoryRepository categoryRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository){
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(createRecipes());
    }

    private List<Recipe> createRecipes(){

        log.debug("Creating base data...");
        List<Recipe> recipes = new ArrayList<>();

        Optional<Category> mexicanCategory = categoryRepository.findByDescription("Mexican");

        Optional<UnitOfMeasure> tablespoonUOM = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> teaspoonUOM = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> dashUOM = unitOfMeasureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> unitUOM = unitOfMeasureRepository.findByDescription("Unit");

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
            guacamole.setSource("Source");
            guacamole.setUrl("https://www.not-good-recipes.com/recipes/not_perfect_guacamole/");

            recipes.add(guacamole);
            log.debug("... base data created");
        } else {
            throw new RuntimeException("Missing elements for recipe");
        }

        return recipes;
    }

}