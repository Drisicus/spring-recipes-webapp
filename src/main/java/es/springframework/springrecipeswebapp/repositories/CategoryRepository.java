package es.springframework.springrecipeswebapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import es.springframework.springrecipeswebapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
