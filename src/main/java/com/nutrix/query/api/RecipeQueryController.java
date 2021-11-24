package com.nutrix.query.api;

import com.nutrix.command.infra.Recipe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queries.GetRecipesQuery;
import result.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeQueryController {
    private final QueryGateway queryGateway;

    @Autowired
    public RecipeQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Búsqueda de todos los Recipe", notes ="Método que busca a todos los Recipe" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recipe.class),
            @ApiResponse(code=201, message = "Recipes encontrados", response = Recipe.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para obtener la respuesta solicitada"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recipes no encontrados")
    })
    public ResponseEntity<List<RecipeResult>> getAll(){
        try{
            GetRecipesQuery getRecipesQuery = new GetRecipesQuery();
            List<RecipeResult> recipes = queryGateway.query(getRecipesQuery,
                    ResponseTypes.multipleInstancesOf(RecipeResult.class))
                    .join();
            return new ResponseEntity<>(recipes, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //get recipe by id
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Búsqueda de un Recipe por id", notes ="Método que busca un Recipe por id" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recipe.class),
            @ApiResponse(code=201, message = "Recipes encontrados", response = Recipe.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para obtener la respuesta solicitada"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recipes no encontrados")
    })
    public ResponseEntity<RecipeResult> getById(@PathVariable String id){
        try{
            GetRecipesQuery getRecipesQuery = new GetRecipesQuery();
            List<RecipeResult> recipes= queryGateway.query(getRecipesQuery,
                    ResponseTypes.multipleInstancesOf(RecipeResult.class))
                    .join();
            for (RecipeResult recipe : recipes) {
                if (recipe.getId().equals(id)) {
                    return new ResponseEntity<>(recipe, HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
