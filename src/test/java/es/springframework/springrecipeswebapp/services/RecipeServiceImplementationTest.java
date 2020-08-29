package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.commands.RecipeCommand;
import es.springframework.springrecipeswebapp.converters.RecipeCommandToRecipe;
import es.springframework.springrecipeswebapp.converters.RecipeToRecipeCommand;
import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RecipeServiceImplementationTest {

    RecipeServiceImplementation recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImplementation(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        // setup with Mockito to get recipesData object when recipeService.GetRecipes() is called
        when(recipeService.getRecipes()).thenReturn(recipesData);

        // verify that a recipe is returned
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);

        // verify that is called once
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void getRecipeById(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe recipeReturned = recipeService.findById(1L);
        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull(commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void testDeleteById() throws Exception {
        Long idToDelete = 2L;
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        recipeService.deleteById(idToDelete);
        verify(recipeRepository, times(1)).deleteById(any());
    }

}