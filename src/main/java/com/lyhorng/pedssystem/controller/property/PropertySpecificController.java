package com.lyhorng.pedssystem.controller.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.property.PropertySpecific;
import com.lyhorng.pedssystem.service.property.PropertySpecificService;

@RestController
@RequestMapping("/api/propertyspecific/")
public class PropertySpecificController {
    
    @Autowired
    public PropertySpecificService propertySpecificService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PropertySpecific>>> listAll() {
        List<PropertySpecific> propertySpecificList = propertySpecificService.getAllList();
        return ResponseEntity.ok(ApiResponse.success("Propery Specifice fetch Success!!", propertySpecificList));
    }
}
