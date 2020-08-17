package es.springframework.springrecipeswebapp.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.springframework.springrecipeswebapp.domain.Category;
import es.springframework.springrecipeswebapp.domain.UnitOfMeasure;
import es.springframework.springrecipeswebapp.repositories.CategoryRepository;
import es.springframework.springrecipeswebapp.repositories.UnitOfMeasureReporitory;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureReporitory unitOfMeasureReporitory;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureReporitory unitOfMeasureReporitory){
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureReporitory = unitOfMeasureReporitory;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){

        Optional<Category> category = categoryRepository.findByDescription("Spanish");
        category.ifPresent(val -> System.out.println(val.getId() + " " + val.getDescription()));

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureReporitory.findByDescription("Teaspoon");
        unitOfMeasure.ifPresent(val -> System.out.println(val.getId() + " " + val.getDescription()));

        return "index";
    }

}
