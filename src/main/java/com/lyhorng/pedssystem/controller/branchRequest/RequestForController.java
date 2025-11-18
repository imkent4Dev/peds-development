package com.lyhorng.pedssystem.controller.branchRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.branchRequest.RequestFor;
import com.lyhorng.pedssystem.service.branchRequest.RequestForService;

@RestController
@RequestMapping("/api/requestfor")
public class RequestForController {
    
    @Autowired
    public RequestForService requestForService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<RequestFor>>> getall() {
        List<RequestFor> requestForList = requestForService.listAllRequestFor();
        return ResponseEntity.ok(ApiResponse.success("Request for fetch Success", requestForList));
    }
}
