package com.lyhorng.pedssystem.controller.property;

import com.lyhorng.pedssystem.dto.property.PropertyRequestDto;
import com.lyhorng.pedssystem.dto.property.PropertyResponseDto;
import com.lyhorng.pedssystem.enums.EvaStatus;
import com.lyhorng.pedssystem.service.property.PropertyService;
import com.lyhorng.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyResponseDto>> createProperty(@Valid @RequestBody PropertyRequestDto requestDto) {
        log.info("REST request to create property");

        try {
            PropertyResponseDto createdProperty = propertyService.createProperty(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Property created successfully", createdProperty));
        } catch (Exception e) {
            log.error("Error creating property: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Failed to create property: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyResponseDto>> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody PropertyRequestDto requestDto) {
        
        log.info("REST request to update property with id: {}", id);
        
        try {
            PropertyResponseDto updatedProperty = propertyService.updateProperty(id, requestDto);
            return ResponseEntity.ok(ApiResponse.success("Property updated successfully", updatedProperty));
        } catch (Exception e) {
            log.error("Error updating property: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Failed to update property: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyResponseDto>> getPropertyById(@PathVariable Long id) {
        log.info("REST request to get property with id: {}", id);

        try {
            PropertyResponseDto property = propertyService.getPropertyById(id);
            return ResponseEntity.ok(ApiResponse.success("Property retrieved successfully", property));
        } catch (Exception e) {
            log.error("Error retrieving property: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Failed to retrieve property: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyResponseDto>>> getAllProperties() {
        log.info("REST request to get all properties");

        try {
            List<PropertyResponseDto> properties = propertyService.getAllProperties();
            return ResponseEntity.ok(ApiResponse.success("Properties retrieved successfully", properties));
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Failed to retrieve properties: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProperty(@PathVariable Long id) {
        log.info("REST request to delete property with id: {}", id);

        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.ok(ApiResponse.success("Property deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting property: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Failed to delete property: " + e.getMessage()));
        }
    }

    @GetMapping("/application-code/{code}")
    public ResponseEntity<ApiResponse<PropertyResponseDto>> getPropertyByApplicationCode(@PathVariable String code) {
        log.info("REST request to get property with application code: {}", code);

        try {
            PropertyResponseDto property = propertyService.getPropertyByApplicationCode(code);
            return ResponseEntity.ok(ApiResponse.success("Property retrieved successfully", property));
        } catch (Exception e) {
            log.error("Error retrieving property: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Failed to retrieve property: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PropertyResponseDto>>> getPropertiesByEvaStatus(@PathVariable EvaStatus status) {
        log.info("REST request to get properties with status: {}", status);

        try {
            List<PropertyResponseDto> properties = propertyService.getPropertiesByEvaStatus(status);
            return ResponseEntity.ok(ApiResponse.success("Properties retrieved successfully", properties));
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Failed to retrieve properties: " + e.getMessage()));
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PropertyResponseDto>>> getPropertiesByOwner(@PathVariable Long ownerId) {
        log.info("REST request to get properties for owner id: {}", ownerId);

        try {
            List<PropertyResponseDto> properties = propertyService.getPropertiesByOwnerId(ownerId);
            return ResponseEntity.ok(ApiResponse.success("Properties retrieved successfully", properties));
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Failed to retrieve properties: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("success", true);
        healthData.put("message", "Property API is running");
        healthData.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(ApiResponse.success("Health check successful", healthData));
    }
}
