package es.springframework.springrecipeswebapp.controllers;

import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

        // Argument capture
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // When this call is made (indexController.getIndexPage(model)) the verify information is captured
        // verify that getIndexPage return template name "index"
        assertEquals(indexController.getIndexPage(model), "index");

        // verify that when indexController.getIndexPage is called the method "getRecipes" from recipeService is called once
        verify(recipeService, times(1)).getRecipes();
        // verify that when indexController.getIndexPage is called the method "addAttribute" from Model is called once with name "recipes" and any set
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        // verify that when indexController.getIndexPage is called the method "getAttribute" from Model is never called with any string
        verify(model, times(0)).getAttribute(anyString());

        // Use Mockito argumentCaptor to obtain the arguments used in the call "mode.addAttribute"
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    void testMockMVC() throws Exception {
        // Set up indexController and get url "/", then check response is ok and template name received is "index"
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

}