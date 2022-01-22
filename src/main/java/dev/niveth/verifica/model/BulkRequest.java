package dev.niveth.verifica.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Bulk Request Model")
public class BulkRequest {

    @ApiModelProperty(name = "type", required = true, position = 1, example = "email" ,allowableValues = "email, domain")
    private String type;
    @ApiModelProperty(name = "entities", required = true, position = 2, example = "[\"gmail.com\"]")
    private List<String> entities;

}
