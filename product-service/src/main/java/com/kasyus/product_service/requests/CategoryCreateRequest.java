package com.kasyus.product_service.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for creating a new category")
public record CategoryCreateRequest(

        @Schema(description = "ID of the category", example = "1")
        @NotNull(message = "Category ID is required")
        Long id,

        @Schema(description = "Name of the category", example = "Electronics")
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must be at most 100 characters")
        String name
) {}
