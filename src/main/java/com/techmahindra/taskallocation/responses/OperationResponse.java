package com.techmahindra.taskallocation.responses;

public class OperationResponse {
	
	private String operValidity;
	private String description;
	private Object result;
	
	public OperationResponse() {
		
	}
	
	public OperationResponse(String operValidity, String description) {
		super();
		this.operValidity = operValidity;
		this.description = description;
	}
	
	

	public OperationResponse(String operValidity, String description, Object result) {
		super();
		this.operValidity = operValidity;
		this.description = description;
		this.result = result;
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
