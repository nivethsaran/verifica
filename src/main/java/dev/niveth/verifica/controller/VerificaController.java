package dev.niveth.verifica.controller;

import dev.niveth.verifica.model.BulkRequest;
import dev.niveth.verifica.model.Response;
import dev.niveth.verifica.service.ValidationService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"Verifica"})
@RestController
@RequestMapping("/v1")
public class VerificaController {

    @Autowired
    private ValidationService validationService;

    public static final Logger LOGGER = LoggerFactory.getLogger(VerificaController.class);

    @ApiOperation(value = "Validate email address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Response.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiImplicitParam(name = "email", paramType = "query")
    @GetMapping(value = "/verify/email", params = "email")
    public ResponseEntity<Response> verifyEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(validationService.verifyAndPopulateResponse(email), HttpStatus.OK);
        } catch (Exception e) {
            return populateErrorResponse(e);
        }
    }
    
    @ApiOperation(value = "Validate domain name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Response.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiImplicitParam(name = "domain", paramType = "query")
    @GetMapping(value = "/verify/domain", params = "domain")
    public ResponseEntity<Response> verifyDomain(@RequestParam String domain) {
        try {
            return new ResponseEntity<>(validationService.verifyDomainAndPopulateResponse(domain), HttpStatus.OK);
        } catch (Exception e) {
            return populateErrorResponse(e);
        }

    }

    @ApiOperation(value = "Validate email addresses or domains in bulk")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Response.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/verify/bulk", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Response>> bulkVerifyDomains(@RequestBody BulkRequest request) {
        List<Response> responses = new ArrayList<>();
        for (String entity : request.getEntities()) {
            try {
                if (request.getType().equals("email")) {
                    responses.add(validationService.verifyAndPopulateResponse(entity));
                } else if (request.getType().equals("domain")) {
                    responses.add(validationService.verifyDomainAndPopulateResponse(entity));
                }
            } catch (Exception e) {
                responses.add(populateErrorResponse(e).getBody());
            }
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    private ResponseEntity<Response> populateErrorResponse(Exception e) {
        Response response = new Response();
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        response.setError(errors);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
