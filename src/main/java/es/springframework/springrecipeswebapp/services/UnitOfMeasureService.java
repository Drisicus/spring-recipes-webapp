package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
