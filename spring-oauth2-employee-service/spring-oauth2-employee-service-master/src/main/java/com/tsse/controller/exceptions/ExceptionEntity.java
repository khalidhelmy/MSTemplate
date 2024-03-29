package com.tsse.controller.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionEntity {

	String type;
	String message;
	String statusCode;
	String errorCode;
	
}
