package es.springframework.springrecipeswebapp.repositories;

import org.springframework.data.repository.CrudRepository;

import es.springframework.springrecipeswebapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
