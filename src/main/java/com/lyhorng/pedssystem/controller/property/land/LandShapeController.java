package com.lyhorng.pedssystem.controller.property.land;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.property.land.LandShape;
import com.lyhorng.pedssystem.service.property.land.LandShapeService;

@RequestMapping("/api/landshape")
@RestController
public class LandShapeController {

    @Autowired
    public LandShapeService landShapeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<LandShape>>> listAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LandShape> landShapeListPage = landShapeService.getAll(page, size);
        PageResponse<LandShape> pageResponse = PageResponse.of(
                landShapeListPage.getContent(),
                landShapeListPage.getNumber() + 1,
                landShapeListPage.getSize(),
                landShapeListPage.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Land Shape fetch success!", pageResponse));
    }
}
