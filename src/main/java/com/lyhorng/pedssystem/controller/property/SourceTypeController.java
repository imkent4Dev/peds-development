package com.lyhorng.pedssystem.controller.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;
import com.lyhorng.pedssystem.service.property.SourceTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/source-types")
@RequiredArgsConstructor
public class SourceTypeController {
    
    @Autowired
    private final SourceTypeService sourceTypeService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<BuildingSourceType>>> getAllSourceTypes() {
        List<BuildingSourceType> sourceTypesList = sourceTypeService.getAllSourceTypes();
        return ResponseEntity.ok(ApiResponse.success("Source type fetch successfull!", sourceTypesList));
    }
    
    @GetMapping("/{id}/agencies")
    public ResponseEntity<List<Agency>> getAgenciesBySourceType(@PathVariable Long id) {
        return ResponseEntity.ok(sourceTypeService.getAgenciesBySourceType(id));
    }
}