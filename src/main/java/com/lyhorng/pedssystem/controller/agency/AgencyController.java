package com.lyhorng.pedssystem.controller.agency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.service.agency.AgencyService;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping("/list") 
    public ResponseEntity<ApiResponse<List<Agency>>> listAllAgency() {
        List<Agency> agencyList = agencyService.getAllAgencies();
        return ResponseEntity.ok(ApiResponse.success("All Agency fetched successfull", agencyList));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Agency>>> getAgenciesBySourceType(@RequestParam String sourceTypeCode) {
        try {
            List<Agency> agencies = agencyService.getAgenciesBySourceType(sourceTypeCode);

            if (agencies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("No agencies found for the given source type code: " + sourceTypeCode));
            }
            return ResponseEntity.ok(ApiResponse.success("Agencies fetched successfully.", agencies));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred while fetching the agencies."));
        }
    }
}
