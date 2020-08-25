package es.springframework.springrecipeswebapp.converters;

import com.sun.istack.Nullable;
import es.springframework.springrecipeswebapp.commands.IngredientCommand;
import es.springframework.springrecipeswebapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient,IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        Function<Ingredient, IngredientCommand> conversion = src -> {
            final IngredientCommand output = new IngredientCommand();
            output.setId(src.getId());
            output.setDescription(src.getDescription());
            output.setAmount(src.getAmount());
            output.setUnitOfMeasure(uomConverter.convert(src.getUnitOfMeasure()));
            return output;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
