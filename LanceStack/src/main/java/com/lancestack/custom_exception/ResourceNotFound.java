package com.lancestack.custom_exception;

public class ResourceNotFound extends RuntimeException{
	public ResourceNotFound(String msg) {
		super(msg);
	}
}
