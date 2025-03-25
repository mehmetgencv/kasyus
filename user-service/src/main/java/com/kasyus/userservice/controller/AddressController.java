package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.AddressCreateRequest;
import com.kasyus.userservice.dto.requests.AddressUpdateRequest;
import com.kasyus.userservice.dto.responses.AddressResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.AddressService;
import com.kasyus.userservice.swagger.BadRequestApiResponse;
import com.kasyus.userservice.swagger.NotFoundApiResponse;
import com.kasyus.userservice.swagger.address.SuccessApiAddressAddResponse;
import com.kasyus.userservice.swagger.address.SuccessApiAddressDeleteResponse;
import com.kasyus.userservice.swagger.address.SuccessApiAddressListResponse;
import com.kasyus.userservice.swagger.address.SuccessApiAddressUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/me/addresses")
public class AddressController {

    private static final String USER_ID_HEADER = "X-User-Id";
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Add a new address for the current user")
    @SuccessApiAddressAddResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @PostMapping
    public ResponseEntity<RestResponse<String>> addAddress(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Valid @RequestBody AddressCreateRequest request) {
        String addressId = addressService.addAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(addressId, "Address added successfully"));
    }

    @Operation(summary = "Get all addresses for the current user")
    @SuccessApiAddressListResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @GetMapping
    public ResponseEntity<RestResponse<Set<AddressResponse>>> getAddresses(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        Set<AddressResponse> addresses = addressService.getAddresses(userId);
        return ResponseEntity.ok(RestResponse.of(addresses, "Addresses retrieved successfully"));
    }

    @Operation(summary = "Update an existing address for the current user")
    @SuccessApiAddressUpdateResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @PutMapping("/{addressId}")
    public ResponseEntity<RestResponse<String>> updateAddress(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the address to update", required = true)
            @PathVariable String addressId,

            @Valid @RequestBody AddressUpdateRequest request) {
        addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(RestResponse.of(addressId, "Address updated successfully"));
    }

    @Operation(summary = "Delete an address for the current user")
    @SuccessApiAddressDeleteResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @DeleteMapping("/{addressId}")
    public ResponseEntity<RestResponse<String>> deleteAddress(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the address to delete", required = true)
            @PathVariable String addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(RestResponse.of(null, "Address deleted successfully"));
    }
}