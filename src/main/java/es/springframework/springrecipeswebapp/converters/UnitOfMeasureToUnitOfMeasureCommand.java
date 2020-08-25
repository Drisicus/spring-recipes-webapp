package es.springframework.springrecipeswebapp.converters;

import com.sun.istack.Nullable;
import es.springframework.springrecipeswebapp.commands.UnitOfMeasureCommand;
import es.springframework.springrecipeswebapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {


    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        Function<UnitOfMeasure, UnitOfMeasureCommand> conversion = src -> {
            final UnitOfMeasureCommand output = new UnitOfMeasureCommand();
            output.setId(src.getId());
            output.setDescription(src.getDescription());
            return output;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
