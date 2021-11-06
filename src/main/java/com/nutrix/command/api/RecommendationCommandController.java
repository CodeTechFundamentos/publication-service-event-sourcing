package com.nutrix.command.api;

import com.nutrix.command.application.dto.ErrorResponseDto;
import com.nutrix.query.models.CreateRecommendationModel;
import command.CreateRecommendationC;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.RecommendationResult;

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
            @ApiResponse(code=201, message = "Recommendation creado"),
            @ApiResponse(code=404, message = "Recommendation no creado")
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
}