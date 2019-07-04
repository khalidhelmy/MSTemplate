package com.tsse.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Generic Exception for reader entity
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A functional exception is thrown related to reader")
public class EmployeeFunctionalException extends RuntimeException
{

	private static final long serialVersionUID = -1736806492657679220L;

	public EmployeeFunctionalException(String message)
    {
        super(message);
    }

}
