package com.nutrix.command.api;

import com.nutrix.command.application.dto.ErrorResponseDto;
import com.nutrix.command.infra.Recipe;
import com.nutrix.command.infra.Recommendation;
import com.nutrix.query.models.CreateRecommendationModel;
import com.nutrix.query.models.UpdateRecommendationModel;
import command.CreateRecommendationC;
import command.DeleteRecommendationC;
import command.UpdateRecommendationC;
import io.swagger.annotations.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.RecommendationResult;
import result.RecommendationUpdateResult;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/recommendation")
@Api(tags="Recommendation", value = "Servicio Web RESTFul de Recommendation")
public class RecommendationCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public RecommendationCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    //Event Sourcing Post
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Recommendation de un Nutritionist", notes ="Método que registra un Recommendation" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recommendation.class),
            @ApiResponse(code=201, message = "Recommendation creado", response = Recommendation.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para ejecutar la solicitud"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recommendation no fue creado")
    })
    public ResponseEntity<Object> insertRecommendation(@Validated @RequestBody CreateRecommendationModel recommendation){
        String id = UUID.randomUUID().toString();
        CreateRecommendationC createRecommendationC = new CreateRecommendationC(
                id,
                recommendation.getName(),
                recommendation.getDescription(),
                recommendation.getCreatedAt(),
                recommendation.getLastModification(),
                recommendation.getNutritionistId()
        );
        CompletableFuture<Object> future = commandGateway.send(createRecommendationC);
        CompletableFuture<Object> futureResponse = future.handle((ok, ex) -> {
            if (ex != null) {
                return new ErrorResponseDto(ex.getMessage());
            }
            return new RecommendationResult(
                    createRecommendationC.getId(),
                    createRecommendationC.getName(),
                    createRecommendationC.getDescription(),
                    createRecommendationC.getCreatedAt(),
                    createRecommendationC.getLastModification(),
                    createRecommendationC.getNutritionistId()
            );
        });
        try {
            Object response = futureResponse.get();
            if (response instanceof RecommendationResult) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Event Sourcing Put
    @PutMapping(path = "/{recommendationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modificación de un Recommendation de un Nutritionist", notes ="Método que modifica un Recipe" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recommendation.class),
            @ApiResponse(code=201, message = "Recommendation modificado", response = Recommendation.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para ejecutar la solicitud"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recommendation no fue modificado")
    })
    public ResponseEntity<Object> updateRecommendation(@PathVariable("recommendationId") String recommendationId, @RequestBody UpdateRecommendationModel recommendation){
        UpdateRecommendationC updateRecommendationC = new UpdateRecommendationC(
                recommendationId,
                recommendation.getName(),
                recommendation.getDescription(),
                recommendation.getCreatedAt(),
                recommendation.getLastModification(),
                recommendation.getNutritionistId()
        );
        CompletableFuture<Object> future = commandGateway.send(updateRecommendationC);
        CompletableFuture<Object> futureResponse = future.handle((ok, ex) -> {
            if (ex != null) {
                return new ErrorResponseDto(ex.getMessage());
            }
            return new RecommendationUpdateResult(
                    updateRecommendationC.getId(),
                    updateRecommendationC.getName(),
                    updateRecommendationC.getDescription(),
                    updateRecommendationC.getCreatedAt(),
                    updateRecommendationC.getLastModification(),
                    updateRecommendationC.getNutritionistId()
            );
        });
        try {
            Object response = futureResponse.get();
            if (response instanceof RecommendationUpdateResult) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Event Sourcing Delete
    @DeleteMapping(path = "/{recommendationId}")
    @ApiOperation(value = "Eliminación de un Recommendation de un Nutritionist", notes ="Método que elimina un Recommendation" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recommendation.class),
            @ApiResponse(code=201, message = "Recommendation eliminado", response = Recommendation.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para ejecutar la solicitud"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recommendation no fue eliminado")
    })
    public CompletableFuture<String> deleteRecommendation(@PathVariable("recommendationId") String recommendationId){
        return commandGateway.send(new DeleteRecommendationC(recommendationId));
    }
}