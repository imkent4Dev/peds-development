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
import com.lyhorng.pedssystem.model.branchRequest.RelationshipType;
import com.lyhorng.pedssystem.service.branchRequest.RelationshipTypeService;

@RestController
@RequestMapping("/api/relationship-type")
public class RelationshipTypeController {

    @Autowired
    public RelationshipTypeService relationshipTypeService;

    @GetMapping("list")
    public ResponseEntity<ApiResponse<PageResponse<RelationshipType>>> listAllRelationshipType(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<RelationshipType> relationshipTypeListPage = relationshipTypeService.getAll(page, size);
        PageResponse<RelationshipType> pageResponse = PageResponse.of(
            relationshipTypeListPage.getContent(),
            relationshipTypeListPage.getNumber() + 1,
            relationshipTypeListPage.getSize(),
            relationshipTypeListPage.getTotalElements());

            return ResponseEntity.ok(ApiResponse.success("Relationship Type fetch successfull!", pageResponse));
    }

}
