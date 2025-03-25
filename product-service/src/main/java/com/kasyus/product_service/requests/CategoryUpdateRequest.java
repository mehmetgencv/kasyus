package com.kasyus.product_service.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for updating an existing category")
public record CategoryUpdateRequest(

        @Schema(description = "ID of the category to update", example = "1")
        @NotNull(message = "Category ID is required")
        Long id,

        @Schema(description = "New name of the category", example = "Updated Electronics")
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must be at most 100 characters")
        String name
) {}
