package com.lyhorng.pedssystem.controller.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.property.PropertySpecific;
import com.lyhorng.pedssystem.service.property.PropertySpecificService;

@RestController
@RequestMapping("/api/propertyspecific/")
public class PropertySpecificController {
    
    @Autowired
    public PropertySpecificService propertySpecificService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<PropertySpecific>>> listAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<PropertySpecific> propertySpecificListPage = propertySpecificService.getAllList(page, size);

        PageResponse<PropertySpecific> pageResponse = PageResponse.of(
            propertySpecificListPage.getContent(), 
            propertySpecificListPage.getNumber() + 1, 
            propertySpecificListPage.getSize(), 
            propertySpecificListPage.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Propery Specifice fetch Success!!", pageResponse));
    }
}
