package dev.niveth.verifica.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class RestClientUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(RestClientUtil.class);
    public final RestTemplate restTemplate = new RestTemplate();
    
    public ResponseEntity<Object> postRequestDispatcher(String url, HttpHeaders headers, String requestBody) throws URISyntaxException {
        
        HttpEntity<String> payload = new HttpEntity<>(requestBody, headers);
        try {
            return restTemplate.postForEntity(new URI(url), payload, Object.class);
        }
        catch(HttpClientErrorException e)
        {
            LOGGER.info("HTTP Client Exception {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> getRequestDispatcherForString(String url, HttpHeaders headers) throws URISyntaxException {
        try {
            return restTemplate.exchange(new URI(url), HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        }
        catch(HttpClientErrorException e)
        {
            LOGGER.info("HTTP Client Exception {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
