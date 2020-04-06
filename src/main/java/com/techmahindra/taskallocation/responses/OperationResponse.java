package com.techmahindra.taskallocation.responses;

public class OperationResponse {
	
	private String operValidity;
	private String description;
	
	public OperationResponse() {
		
	}
	
	public OperationResponse(String operValidity, String description) {
		super();
		this.operValidity = operValidity;
		this.description = description;
	}

	public String getOperValidity() {
		return operValidity;
	}

	public void setOperValidity(String operValidity) {
		this.operValidity = operValidity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	

}
