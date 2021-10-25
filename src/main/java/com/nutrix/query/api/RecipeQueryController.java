package com.nutrix.query.api;

import com.nutrix.query.models.CreateRecipeModel;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queries.GetRecipesQuery;

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
            @ApiResponse(code=201, message = "Recipe encontrados"),
            @ApiResponse(code=404, message = "Recipe no encontrados")
    })
    public ResponseEntity<List<CreateRecipeModel>> getAll(){
        try{
            GetRecipesQuery getRecipesQuery = new GetRecipesQuery();
            List<CreateRecipeModel> recipeModels = queryGateway.query(getRecipesQuery,
                    ResponseTypes.multipleInstancesOf(CreateRecipeModel.class))
                    .join();
            return new ResponseEntity<>(recipeModels, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
