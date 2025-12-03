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
import com.lyhorng.pedssystem.model.property.land.TypeOfLot;
import com.lyhorng.pedssystem.service.property.land.TypeOfLotService;

@RequestMapping("/api/lottype")
@RestController
public class LotOfTypeController {

    @Autowired
    public TypeOfLotService typeOfLotService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<TypeOfLot>>> listAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TypeOfLot> typeOfLotPage = typeOfLotService.getAll(page, size);
        PageResponse<TypeOfLot> pageResponse = PageResponse.of(
                typeOfLotPage.getContent(),
                typeOfLotPage.getNumber() + 1,
                typeOfLotPage.getSize(),
                typeOfLotPage.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Land Shape fetch success!", pageResponse));
    }
}
