package com.kasyus.authservice.general;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "RestResponse",
        description = "Generic response model for API responses"
)
public class RestResponse<T> {

    @Schema(
            description = "The data payload of the response",
            example = "{ \"id\": 1, \"name\": \"Sample User\" }"
    )
    private T data;

    @Schema(
            description = "The timestamp when the response was generated",
            example = "2024-12-11T10:15:30"
    )
    private LocalDateTime responseDate;

    @Schema(
            description = "Indicates whether the operation was successful",
            example = "true"
    )
    private boolean isSuccess;

    @Schema(
            description = "A descriptive message about the response",
            example = "Operation completed successfully"
    )
    private String message;

    public RestResponse(T data, boolean isSuccess, String message) {
        this.data = data;
        this.responseDate = LocalDateTime.now();
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public RestResponse(T data, boolean isSuccess) {
        this.data = data;
        this.responseDate = LocalDateTime.now();
        this.isSuccess = isSuccess;
    }

    public static <T> RestResponse<T> of(T data) {
        return new RestResponse<>(data, true, null);
    }

    public static <T> RestResponse<T> of(T data, String message) {
        return new RestResponse<>(data, true, message);
    }

    public static <T> RestResponse<T> error(T data) {
        return new RestResponse<>(null, false);
    }
    public static <T> RestResponse<T> error(T data, String message) {
        return new RestResponse<>(null, false, message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
