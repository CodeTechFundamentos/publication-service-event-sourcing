package com.nutrix.command.api;

import com.nutrix.command.application.dto.ErrorResponseDto;
import com.nutrix.query.models.CreateRecipeModel;
import command.CreateRecipeC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/recipe")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public RecipeCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    //Event Sourcing Post
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Recipe de un Nutritionist", notes ="Método que registra un Recipe" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Recipe creado"),
            @ApiResponse(code=404, message = "Recipe no creado")
    })
    public ResponseEntity<Object> insertRecipe(@Validated @RequestBody CreateRecipeModel recipe){
        String id = UUID.randomUUID().toString();
        CreateRecipeC createRecipeC = new CreateRecipeC(
                id,
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPreparation(),
                recipe.getIngredients(),
                recipe.getFavorite(),
                recipe.getCreatedAt(),
                recipe.getLastModification(),
                recipe.getNutritionistId()
        );
        CompletableFuture<Object> future = commandGateway.send(createRecipeC);
        CompletableFuture<Object> futureResponse = future.handle((ok, ex) -> {
            if (ex != null) {
                return new ErrorResponseDto(ex.getMessage());
            }
            return new CreateRecipeModel(
                    createRecipeC.getId(),
                    createRecipeC.getName(),
                    createRecipeC.getDescription(),
                    createRecipeC.getPreparation(),
                    createRecipeC.getIngredients(),
                    createRecipeC.getFavorite(),
                    createRecipeC.getCreatedAt(),
                    createRecipeC.getLastModification(),
                    createRecipeC.getNutritionistId()
            );
        });
        try {
            Object response = futureResponse.get();
            if (response instanceof CreateRecipeModel) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}