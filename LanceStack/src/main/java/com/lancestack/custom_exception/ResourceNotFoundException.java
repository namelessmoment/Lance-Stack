package com.lancestack.custom_exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String msg;
	public ResourceNotFoundException(HttpStatus status, String msg) {
		super(msg);
		this.status = status;
	}
	public HttpStatus getStatus() {
        return status;
    }
}
