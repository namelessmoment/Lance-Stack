package com.lancestack.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
	private String message;
	private LocalDateTime timeStamp;
	
	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(String message) {
		super();
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}
}
