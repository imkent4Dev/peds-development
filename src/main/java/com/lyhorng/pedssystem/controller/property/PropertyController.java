package com.lyhorng.pedssystem.controller.property;

import com.lyhorng.pedssystem.dto.property.PropertyRequestDto;
import com.lyhorng.pedssystem.dto.property.PropertyResponseDto;
import com.lyhorng.pedssystem.enums.EvaStatus;
import com.lyhorng.pedssystem.service.property.PropertyService;
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
    public ResponseEntity<Map<String, Object>> createProperty(@Valid @RequestBody PropertyRequestDto requestDto) {
        log.info("REST request to create property");
        
        try {
            PropertyResponseDto createdProperty = propertyService.createProperty(requestDto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Property created successfully");
            response.put("data", createdProperty);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            log.error("Error creating property: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to create property: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // ==================== UPDATE PROPERTY ====================
    /**
     * Update an existing property
     * PUT /api/v1/properties/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody PropertyRequestDto requestDto) {
        
        log.info("REST request to update property with id: {}", id);
        
        try {
            PropertyResponseDto updatedProperty = propertyService.updateProperty(id, requestDto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Property updated successfully");
            response.put("data", updatedProperty);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error updating property: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to update property: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // ==================== GET PROPERTY BY ID ====================
    /**
     * Get property by ID
     * GET /api/v1/properties/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPropertyById(@PathVariable Long id) {
        log.info("REST request to get property with id: {}", id);
        
        try {
            PropertyResponseDto property = propertyService.getPropertyById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Property retrieved successfully");
            response.put("data", property);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error retrieving property: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve property: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // ==================== GET ALL PROPERTIES ====================
    /**
     * Get all properties
     * GET /api/v1/properties
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProperties() {
        log.info("REST request to get all properties");
        
        try {
            List<PropertyResponseDto> properties = propertyService.getAllProperties();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Properties retrieved successfully");
            response.put("data", properties);
            response.put("count", properties.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve properties: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ==================== DELETE PROPERTY ====================
    /**
     * Delete property by ID
     * DELETE /api/v1/properties/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProperty(@PathVariable Long id) {
        log.info("REST request to delete property with id: {}", id);
        
        try {
            propertyService.deleteProperty(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Property deleted successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error deleting property: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to delete property: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // ==================== GET PROPERTY BY APPLICATION CODE ====================
    /**
     * Get property by application code
     * GET /api/v1/properties/application-code/{code}
     */
    @GetMapping("/application-code/{code}")
    public ResponseEntity<Map<String, Object>> getPropertyByApplicationCode(@PathVariable String code) {
        log.info("REST request to get property with application code: {}", code);
        
        try {
            PropertyResponseDto property = propertyService.getPropertyByApplicationCode(code);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Property retrieved successfully");
            response.put("data", property);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error retrieving property: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve property: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // ==================== GET PROPERTIES BY EVALUATION STATUS ====================
    /**
     * Get properties by evaluation status
     * GET /api/v1/properties/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getPropertiesByEvaStatus(@PathVariable EvaStatus status) {
        log.info("REST request to get properties with status: {}", status);
        
        try {
            List<PropertyResponseDto> properties = propertyService.getPropertiesByEvaStatus(status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Properties retrieved successfully");
            response.put("data", properties);
            response.put("count", properties.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve properties: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ==================== GET PROPERTIES BY OWNER ====================
    /**
     * Get properties by owner ID
     * GET /api/v1/properties/owner/{ownerId}
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Map<String, Object>> getPropertiesByOwner(@PathVariable Long ownerId) {
        log.info("REST request to get properties for owner id: {}", ownerId);
        
        try {
            List<PropertyResponseDto> properties = propertyService.getPropertiesByOwnerId(ownerId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Properties retrieved successfully");
            response.put("data", properties);
            response.put("count", properties.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error retrieving properties: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to retrieve properties: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ==================== HEALTH CHECK ====================
    /**
     * Health check endpoint
     * GET /api/v1/properties/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Property API is running");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}