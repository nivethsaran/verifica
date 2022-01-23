package dev.niveth.verifica.service;

import dev.niveth.verifica.model.Response;
import dev.niveth.verifica.service.impl.ValidationServiceImpl;
import dev.niveth.verifica.util.RestClientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidationServiceImplTests {

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Mock
    private RestClientUtil restClientUtil;

    @BeforeEach
    public void init()
    {
        ReflectionTestUtils.setField(validationService,"detaUrl","xxxx");
        ReflectionTestUtils.setField(validationService,"detaSecretKey","xxxx");
        ReflectionTestUtils.setField(validationService,"gravatarUrl","xxxx");
    }

    @Test
    public void verifyAndPopulateResponseSuccess() throws URISyntaxException {
        String email = "xyz@abc.com";
        when(restClientUtil.getRequestDispatcherForString(Mockito.any(), Mockito.any())).thenReturn(new ResponseEntity<>("", HttpStatus.OK));
        Response response = validationService.verifyAndPopulateResponse(email);
        assertTrue(response.isValidSyntax());
    }
}
