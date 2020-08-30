package es.springframework.springrecipeswebapp.services;

import es.springframework.springrecipeswebapp.domain.Recipe;
import es.springframework.springrecipeswebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImplementation implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImplementation(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.info("Saving image for recipe " + recipeId);
        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int index = 0;
            for(byte b : file.getBytes()){
                byteObjects[index] = b;
                index++;
            }

            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);

        } catch (IOException exception){
            log.error("Error!", exception);
            exception.printStackTrace();
        }
    }
}
