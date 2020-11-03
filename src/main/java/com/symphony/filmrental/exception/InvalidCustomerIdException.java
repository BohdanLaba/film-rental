package com.symphony.filmrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot find customer by given cutsomerId")
public class InvalidCustomerIdException extends RuntimeException {

}
