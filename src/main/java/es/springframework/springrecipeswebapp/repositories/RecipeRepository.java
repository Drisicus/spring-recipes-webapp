package es.springframework.springrecipeswebapp.repositories;

import es.springframework.springrecipeswebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Optional<Recipe> findById(Long id);
}
