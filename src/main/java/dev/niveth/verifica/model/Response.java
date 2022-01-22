package dev.niveth.verifica.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"email","username","domain","isValidSyntax","domainStatus","isDisposable","gravatarUrl","mxRecords","message","error"})
@ApiModel(value = "Verifica Response")
public class Response {

    @ApiModelProperty(value = "Email Address" ,position = 1, example = "xyz@abc.com")
    @JsonProperty("email")
    private String email;
    @ApiModelProperty(value = "Status of Domain",  position = 3, example = "abc.com")
    @JsonProperty("domain")
    private String domain;
    @ApiModelProperty(value = "Username",  position = 2, example = "xyz")
    @JsonProperty("username")
    private String username;
    @ApiModelProperty(value = "Whether email is syntactically valid", position = 4, example = "true")
    @JsonProperty("isValidSyntax")
    private boolean isValidSyntax;
    @ApiModelProperty(value = "domainStatus",  position = 5, example = "VALID", allowableValues = "VALID, INVALID")
    @JsonProperty("domainStatus")
    private String domainStatus;
    @ApiModelProperty(value = "isDisposable",  position = 6, example = "NON DISPOSABLE", allowableValues = "DISPOSABLE, NON DISPOSABLE, UNKNOWN")
    @JsonProperty("isDisposable")
    private String isDisposable;
    @ApiModelProperty(value = "gravatarUrl",  position = 7, example = "UNAVAILABLE")
    @JsonProperty("gravatarUrl")
    private String gravatarUrl;
    @ApiModelProperty(value = "mxRecords", position = 8, example = "[]")
    @JsonProperty("mxRecords")
    private List<String> mxRecords;
    @ApiModelProperty(value = "message",  position = 9, example = "")
    @JsonProperty("message")
    private String message;
    @ApiModelProperty(value = "errors",  position = 10, example = "[]")
    @JsonProperty("errors")
    private List<String> error;

}
