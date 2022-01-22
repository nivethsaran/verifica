package dev.niveth.verifica.model;

import lombok.Data;

import java.util.List;

@Data
public class BulkRequest {

    private String type;
    private List<String> entities;

}
