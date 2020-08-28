package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.commands.IngredientCommand;
import es.springframework.springrecipeswebapp.converters.IngredientToIngredientCommand;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;


    public IngredientServiceImplementation(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
         Optional<IngredientCommand> ingredientCommand = recipeRepository.findById(recipeId).stream()
                 .flatMap(recipe -> recipe.getIngredients().stream())
                 .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();
        return ingredientCommand.orElse(null);
    }
}
