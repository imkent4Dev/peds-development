package com.lyhorng.pedssystem.controller.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.property.PropertyTitleType;
import com.lyhorng.pedssystem.service.property.PropertyTittleTypeService;

@RestController
@RequestMapping("/api/propertytitletypes")
public class PropertyTittleTypeController {

    @Autowired
    private PropertyTittleTypeService propertyTittleTypeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<PropertyTitleType>>> listAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<PropertyTitleType> propertyTittleTypes = propertyTittleTypeService.getAllPropertyTitleTypes(page, size);
        PageResponse<PropertyTitleType> pageResponse = PageResponse.of(
            propertyTittleTypes.getContent(), 
            propertyTittleTypes.getNumber() + 1, 
            propertyTittleTypes.getSize(), 
            propertyTittleTypes.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Property Title Types fetched successfully.", pageResponse));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PropertyTitleType>> create(
            @RequestParam("titleType") String titleType,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            PropertyTitleType propertyTittleType = propertyTittleTypeService.createPropertyTittleType(titleType);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Property Title Type created successfully.", propertyTittleType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Failed to create Property Title Type: " + e.getMessage(), null));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<PropertyTitleType>> update(
            @RequestParam("id") Long id,
            @RequestParam("titleType") String titleType,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            PropertyTitleType propertyTittleType = propertyTittleTypeService.updatePropertyTittleType(id, titleType, file);
            return ResponseEntity.ok(ApiResponse.success("Property Title Type updated successfully.", propertyTittleType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Failed to update Property Title Type: " + e.getMessage(), null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestParam("id") Long id) {
        try {
            propertyTittleTypeService.deletePropertyTittleType(id);
            return ResponseEntity.ok(ApiResponse.success("Property Title Type deleted successfully.", "Deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Failed to delete Property Title Type: " + e.getMessage(), null));
        }
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse<PropertyTitleType>> view(@RequestParam("id") Long id) {
        try {
            PropertyTitleType propertyTittleType = propertyTittleTypeService.getPropertyTittleTypeById(id);
            return ResponseEntity.ok(ApiResponse.success("Property Title Type details fetched successfully.", propertyTittleType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Property Title Type not found: " + e.getMessage(), null));
        }
    }
}
