package com.tsse.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_FAILURE, reason = "Some Technical failure occured ...")
public class GeneralExcpetion extends RuntimeException {

}
