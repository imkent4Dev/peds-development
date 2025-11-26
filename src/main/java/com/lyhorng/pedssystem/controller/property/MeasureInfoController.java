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
import com.lyhorng.pedssystem.model.property.PropertyMeasureInfo;
import com.lyhorng.pedssystem.service.property.MeasureInfoService;

@RestController
@RequestMapping("/api/measureinfo/")
public class MeasureInfoController {

    @Autowired
    public MeasureInfoService measureInfoService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<PropertyMeasureInfo>>> listAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PropertyMeasureInfo> measureInfoListPage = measureInfoService.getAllList(page, size);
        PageResponse<PropertyMeasureInfo> pageResponse = PageResponse.of(
                measureInfoListPage.getContent(),
                measureInfoListPage.getNumber() + 1,
                measureInfoListPage.getSize(),
                measureInfoListPage.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Measure Info fetch Success!!", pageResponse));

    }
}
