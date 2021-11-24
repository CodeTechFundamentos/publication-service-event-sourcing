package com.nutrix.query.api;

import com.nutrix.command.infra.Recipe;
import com.nutrix.command.infra.Recommendation;
import com.nutrix.query.models.CreateRecommendationModel;
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
import queries.GetRecommendationsQuery;
import result.RecommendationResult;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@Api(tags="Recommendation", value = "Servicio Web RESTFul de Recommendation")
public class RecommendationQueryController {
    private final QueryGateway queryGateway;

    @Autowired
    public RecommendationQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Búsqueda de todos los Recommendation", notes ="Método que busca a todos los Recommendation" )
    @ApiResponses({
            @ApiResponse(code=200, message = "La operación fue exitosa", response = Recommendation.class),
            @ApiResponse(code=201, message = "Recommendations encontrados", response = Recommendation.class),
            @ApiResponse(code=401, message = "Es necesario autenticar para obtener la respuesta solicitada"),
            @ApiResponse(code=403, message = "El cliente no posee los permisos necesarios"),
            @ApiResponse(code=404, message = "Recommendations no encontrados")
    })
    public ResponseEntity<List<RecommendationResult>> getAll(){
        try{
            GetRecommendationsQuery getRecommendationsQuery = new GetRecommendationsQuery();
            List<RecommendationResult> recommendations = queryGateway.query(getRecommendationsQuery,
                    ResponseTypes.multipleInstancesOf(RecommendationResult.class))
                    .join();
            return new ResponseEntity<>(recommendations, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
