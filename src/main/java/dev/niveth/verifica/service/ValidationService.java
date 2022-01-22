package dev.niveth.verifica.service;

import dev.niveth.verifica.model.Response;

public interface ValidationService {

	public Response verifyAndPopulateResponse(String email);

    public Response verifyDomainAndPopulateResponse(String domain);
}
