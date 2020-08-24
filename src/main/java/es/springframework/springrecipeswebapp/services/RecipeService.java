package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
}
