package dev.niveth.verifica.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class VerificaController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(VerificaController.class);

	@GetMapping("/verify")
	public ResponseEntity<String> validateEmail1()
	{
		return new ResponseEntity<String>("Something1",HttpStatus.OK);
	}
	
	@GetMapping("/bulk-verify")
	public ResponseEntity<String> validateEmail2()
	{
		return new ResponseEntity<String>("Something2",HttpStatus.OK);
	}
	
	@GetMapping("/domain-verify")
	public ResponseEntity<String> validateEmail3()
	{
		return new ResponseEntity<String>("Something3",HttpStatus.OK);
	}
	
}
