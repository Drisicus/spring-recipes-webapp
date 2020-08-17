package es.springframework.springrecipeswebapp.services;

import java.util.Set;

import es.springframework.springrecipeswebapp.domain.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
