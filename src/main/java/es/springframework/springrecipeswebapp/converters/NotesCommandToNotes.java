package es.springframework.springrecipeswebapp.converters;

import com.sun.istack.Nullable;
import es.springframework.springrecipeswebapp.commands.NotesCommand;
import es.springframework.springrecipeswebapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        Function<NotesCommand, Notes> conversion = src -> {
            final Notes output = new Notes();
            output.setId(src.getId());
            output.setRecipeNotes(src.getRecipeNotes());
            return output;
        };
        return Objects.nonNull(source) ? conversion.apply(source) : null;
    }
}
