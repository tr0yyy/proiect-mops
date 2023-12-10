package com.example.demo.dto;

public class ArticolResponseDTO {
	
	private boolean success;
	private Object message;
	
	public ArticolResponseDTO(boolean success, Object message) {
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Object getMessage() {
		return message;
	}
	
	public void setMessage(Object message) {
		this.message = message;
	}
}
