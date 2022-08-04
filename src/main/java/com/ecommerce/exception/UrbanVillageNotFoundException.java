package com.ecommerce.exception;

import com.ecommerce.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.Transient;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrbanVillageNotFoundException extends RuntimeException {

    @Transient
    private transient ApiResponse apiResponse;

    private final String resourceName = "Urban Village";
    private String fieldName;
    private Object fieldValue;

    public UrbanVillageNotFoundException(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    private void setApiResponse() {
        String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}
