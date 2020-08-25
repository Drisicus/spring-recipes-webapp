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
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        Function<UnitOfMeasureCommand, UnitOfMeasure> conversion = src -> {
            final UnitOfMeasure output = new UnitOfMeasure();
            output.setId(src.getId());
            output.setDescription(src.getDescription());
            return output;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
