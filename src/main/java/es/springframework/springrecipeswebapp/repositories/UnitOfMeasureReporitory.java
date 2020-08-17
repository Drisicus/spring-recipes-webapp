package es.springframework.springrecipeswebapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import es.springframework.springrecipeswebapp.domain.UnitOfMeasure;

public interface UnitOfMeasureReporitory extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
