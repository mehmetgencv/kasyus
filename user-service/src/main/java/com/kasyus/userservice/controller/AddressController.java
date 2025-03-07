package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.AddressCreateRequest;
import com.kasyus.userservice.dto.requests.AddressUpdateRequest;
import com.kasyus.userservice.dto.responses.AddressResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.AddressService;
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

    @PostMapping
    public ResponseEntity<RestResponse<String>> addAddress(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody AddressCreateRequest request) {
        String addressId = addressService.addAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(addressId, "Address added successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<Set<AddressResponse>>> getAddresses(
            @RequestHeader(USER_ID_HEADER) String userId) {
        Set<AddressResponse> addresses = addressService.getAddresses(userId);
        return ResponseEntity.ok(RestResponse.of(addresses, "Addresses retrieved successfully"));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<RestResponse<String>> updateAddress(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable String addressId,
            @RequestBody AddressUpdateRequest request) {
        addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(RestResponse.of(addressId, "Address updated successfully"));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<RestResponse<String>> deleteAddress(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable String addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(RestResponse.of(null, "Address deleted successfully"));
    }
}