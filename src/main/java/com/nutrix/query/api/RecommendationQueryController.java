package com.nutrix.query.api;

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
            @ApiResponse(code=201, message = "Recommendation encontrados"),
            @ApiResponse(code=404, message = "Recommendation no encontrados")
    })
    public ResponseEntity<List<CreateRecommendationModel>> getAll(){
        try{
            GetRecommendationsQuery getRecommendationsQuery = new GetRecommendationsQuery();
            List<CreateRecommendationModel> recommendationModels = queryGateway.query(getRecommendationsQuery,
                    ResponseTypes.multipleInstancesOf(CreateRecommendationModel.class))
                    .join();
            return new ResponseEntity<>(recommendationModels, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
