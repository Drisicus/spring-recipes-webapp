package es.springframework.springrecipeswebapp.converters;

import com.sun.istack.Nullable;
import es.springframework.springrecipeswebapp.commands.CategoryCommand;
import es.springframework.springrecipeswebapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        Function<Category, CategoryCommand> convertCategory = src -> {
            final CategoryCommand output = new CategoryCommand();
            output.setId(src.getId());
            output.setDescription(src.getDescription());
            return output;
        };
        return Objects.nonNull(source) ? convertCategory.apply(source) : null;
    }
}
