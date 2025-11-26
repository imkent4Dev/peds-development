package com.lyhorng.pedssystem.controller.property.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.dto.location.LocationHierarchyDTO;
import com.lyhorng.pedssystem.model.property.location.Commune;
import com.lyhorng.pedssystem.model.property.location.District;
import com.lyhorng.pedssystem.model.property.location.Province;
import com.lyhorng.pedssystem.model.property.location.Village;
import com.lyhorng.pedssystem.service.property.LocationService;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/provinces")
    public ResponseEntity<ApiResponse<List<Province>>> getAllProvinces() {
        try {
            List<Province> provinces = locationService.getAllProvinces();
            return ResponseEntity.ok(
                    ApiResponse.success("Provinces retrieved successfully", provinces));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve provinces: " + e.getMessage(), "PROVINCE_ERROR"));
        }
    }

    @GetMapping("/districts")
    public ResponseEntity<ApiResponse<List<District>>> getDistricts(
            @RequestParam(required = false) Long provinceId) {
        try {
            List<District> districts;
            if (provinceId != null) {
                // Get districts filtered by province
                districts = locationService.getDistrictsByProvinceId(provinceId);
            } else {
                // Get all districts
                districts = locationService.getAllDistricts();
            }
            return ResponseEntity.ok(
                    ApiResponse.success("Districts retrieved successfully", districts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve districts: " + e.getMessage(), "DISTRICT_ERROR"));
        }
    }

    @GetMapping("/communes")
    public ResponseEntity<ApiResponse<List<Commune>>> getCommunes(
            @RequestParam(required = false) Long districtId) {
        try {
            List<Commune> communes;
            if (districtId != null) {
                // Get communes filtered by district
                communes = locationService.getCommunesByDistrictId(districtId);
            } else {
                // Get all communes
                communes = locationService.getAllCommunes();
            }
            return ResponseEntity.ok(
                    ApiResponse.success("Communes retrieved successfully", communes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve communes: " + e.getMessage(), "COMMUNE_ERROR"));
        }
    }

    @GetMapping("/villages")
    public ResponseEntity<ApiResponse<List<Village>>> getVillages(
            @RequestParam(required = false) Long communeId) {
        try {
            List<Village> villages;
            if (communeId != null) {
                // Get villages filtered by commune
                villages = locationService.getVillagesByCommuneId(communeId);
            } else {
                // Get all villages
                villages = locationService.getAllVillages();
            }
            return ResponseEntity.ok(
                    ApiResponse.success("Villages retrieved successfully", villages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve villages: " + e.getMessage(), "VILLAGE_ERROR"));
        }
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<LocationHierarchyDTO>> getLocationHierarchy(
            @RequestParam(required = false) Long provinceId,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long communeId) {
        try {
            LocationHierarchyDTO hierarchy = new LocationHierarchyDTO();
            
            // Always get all provinces
            hierarchy.setProvinces(locationService.getAllProvinces());
            
            // Get districts if province is selected
            if (provinceId != null) {
                hierarchy.setDistricts(locationService.getDistrictsByProvinceId(provinceId));
            }
            
            // Get communes if district is selected
            if (districtId != null) {
                hierarchy.setCommunes(locationService.getCommunesByDistrictId(districtId));
            }
            
            // Get villages if commune is selected
            if (communeId != null) {
                hierarchy.setVillages(locationService.getVillagesByCommuneId(communeId));
            }
            
            return ResponseEntity.ok(
                    ApiResponse.success("Location hierarchy retrieved successfully", hierarchy));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve location hierarchy: " + e.getMessage(), "HIERARCHY_ERROR"));
        }
    }

}