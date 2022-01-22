package dev.niveth.verifica.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"email","username","domain","isValidSyntax","domainStatus","isDisposable","gravatarUrl","mxRecords","message","error"})
public class Response {

    @JsonProperty("email")
    private String email;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("username")
    private String username;
    @JsonProperty("isValidSyntax")
    private boolean isValidSyntax;
    @JsonProperty("domainStatus")
    private String domainStatus;
    @JsonProperty("isDisposable")
    private String isDisposable;
    @JsonProperty("gravatarUrl")
    private String gravatarUrl;
    @JsonProperty("mxRecords")
    private List<String> mxRecords;
    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<String> error;

}
