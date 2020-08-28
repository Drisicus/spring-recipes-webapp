package es.springframework.springrecipeswebapp.controllers;

import es.springframework.springrecipeswebapp.commands.RecipeCommand;
import es.springframework.springrecipeswebapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {


    @Mock
    RecipeService recipeService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }


    @Test
    public void testListIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());

    }

}