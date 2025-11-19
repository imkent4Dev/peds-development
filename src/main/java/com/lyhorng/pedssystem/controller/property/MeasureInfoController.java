package com.lyhorng.pedssystem.controller.property;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.property.PropertyMeasureInfo;
import com.lyhorng.pedssystem.service.property.MeasureInfoService;

@RestController
@RequestMapping("/api/measureinfo/")
public class MeasureInfoController {
    
    @Autowired
    public MeasureInfoService measureInfoService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PropertyMeasureInfo>>> listAll() {
        List<PropertyMeasureInfo> measureInfoList = measureInfoService.getAllList();
        return ResponseEntity.ok(ApiResponse.success("Measure Info fetch Success!!", measureInfoList));

    }
}
