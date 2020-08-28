package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.commands.IngredientCommand;
import es.springframework.springrecipeswebapp.converters.IngredientCommandToIngredient;
import es.springframework.springrecipeswebapp.converters.IngredientToIngredientCommand;
import es.springframework.springrecipeswebapp.domain.Ingredient;
import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import es.springframework.springrecipeswebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImplementation implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImplementation(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                           IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
         Optional<IngredientCommand> ingredientCommand = recipeRepository.findById(recipeId).stream()
                 .flatMap(recipe -> recipe.getIngredients().stream())
                 .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();
        return ingredientCommand.orElse(null);
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        IngredientCommand returnCommand = new IngredientCommand();

        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if(ingredientOptional.isPresent()){
                ingredientOptional.get().setDescription(command.getDescription());
                ingredientOptional.get().setAmount(command.getAmount());
                ingredientOptional.get().setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("Unit of measure not found")));
            } else{
                recipe.addIngredient(Objects.requireNonNull(ingredientCommandToIngredient.convert(command)));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            returnCommand = ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst().get());
        }

        return returnCommand;
    }
}
