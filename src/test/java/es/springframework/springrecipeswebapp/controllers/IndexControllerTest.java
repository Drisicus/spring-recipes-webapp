package es.springframework.springrecipeswebapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import es.springframework.springrecipeswebapp.services.RecipeService;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        // verify that getIndexPage return template name "index"
        assertEquals(indexController.getIndexPage(model), "index");

        // verify that when indexController.getIndexPage is called the method "getRecipes" from recipeService is called once
        verify(recipeService, times(1)).getRecipes();
        // verify that when indexController.getIndexPage is called the method "addAttribute" from Model is called once with name "recipes" and any set
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        // verify that when indexController.getIndexPage is called the method "getAttribute" from Model is never called with any string
        verify(model, times(0)).getAttribute(anyString());

    }
}