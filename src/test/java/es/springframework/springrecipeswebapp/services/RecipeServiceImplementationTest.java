package es.springframework.springrecipeswebapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;

class RecipeServiceImplementationTest {

    RecipeServiceImplementation recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImplementation(recipeRepository);
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
    }

}