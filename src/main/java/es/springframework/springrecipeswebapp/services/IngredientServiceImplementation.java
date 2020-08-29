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

import javax.transaction.Transactional;
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
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        IngredientCommand returnCommand = new IngredientCommand();

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

            if(ingredientOptional.isPresent()){
                ingredientOptional.get().setDescription(ingredientCommand.getDescription());
                ingredientOptional.get().setAmount(ingredientCommand.getAmount());
                ingredientOptional.get().setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("Unit of measure not found")));
            } else{
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            // Id for the new ingredient is created in the "save" operation if is new
            Recipe savedRecipe = recipeRepository.save(recipe);

            Ingredient ingredientToConvert = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> Objects.equals(recipeIngredient.getDescription(), ingredientCommand.getDescription())
                            && Objects.equals(recipeIngredient.getAmount(), ingredientCommand.getAmount())
                            && Objects.equals(recipeIngredient.getUnitOfMeasure().getId(), ingredientCommand.getUnitOfMeasure().getId())
                    ).findFirst().get();

            returnCommand = ingredientToIngredientCommand.convert(ingredientToConvert);
        }

        return returnCommand;
    }

    @Override
    @Transactional
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId){
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isPresent()){
            Optional<Ingredient> ingredient = recipe.get().getIngredients().stream().filter(ing -> ing.getId().equals(ingredientId)).findFirst();
            if(ingredient.isPresent()){
                ingredient.get().setRecipe(null);
                recipe.get().getIngredients().remove(ingredient.get());
                recipeRepository.save(recipe.get());
            }
        }
    }

}
