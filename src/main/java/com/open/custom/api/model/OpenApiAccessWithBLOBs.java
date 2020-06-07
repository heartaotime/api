package com.open.custom.api.model;

public class OpenApiAccessWithBLOBs extends OpenApiAccess {
    private String apiRequest;

    private String apiResponse;

    public String getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(String apiRequest) {
        this.apiRequest = apiRequest == null ? null : apiRequest.trim();
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse == null ? null : apiResponse.trim();
    }
}