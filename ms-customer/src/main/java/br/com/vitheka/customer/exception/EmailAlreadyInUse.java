package br.com.vitheka.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyInUse extends ResponseStatusException {
    public EmailAlreadyInUse(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
