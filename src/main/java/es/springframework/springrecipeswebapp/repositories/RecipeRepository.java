package es.springframework.springrecipeswebapp.repositories;

import org.springframework.data.repository.CrudRepository;

import es.springframework.springrecipeswebapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
