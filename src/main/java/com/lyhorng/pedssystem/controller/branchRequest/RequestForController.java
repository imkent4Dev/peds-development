package com.lyhorng.pedssystem.controller.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.branchRequest.RequestFor;
import com.lyhorng.pedssystem.service.branchRequest.RequestForService;

@RestController
@RequestMapping("/api/requestfor")
public class RequestForController {
    
    @Autowired
    public RequestForService requestForService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<RequestFor>>> getall(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<RequestFor> requestForListPage = requestForService.listAllRequestFor(page, size);

        PageResponse<RequestFor> pageResponse = PageResponse.of(
            requestForListPage.getContent(), 
            requestForListPage.getNumber() + 1, 
            requestForListPage.getSize(), 
            requestForListPage.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success("Request for fetch Success", pageResponse));
    }
}
