package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.commands.RecipeCommand;
import es.springframework.springrecipeswebapp.converters.RecipeCommandToRecipe;
import es.springframework.springrecipeswebapp.converters.RecipeToRecipeCommand;
import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImplementation(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting recipes from RecipeServiceImpl");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id){
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe not found");
        }
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command){
        Recipe convertedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(convertedRecipe);
        log.debug("Saved recipe id:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        recipeRepository.deleteById(id);
    }

}
