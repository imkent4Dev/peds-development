package com.lyhorng.pedssystem.controller.branchRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.common.response.PageResponse;
import com.lyhorng.pedssystem.model.branchRequest.Currency;
import com.lyhorng.pedssystem.service.branchRequest.CurrencyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    
    private final CurrencyService currencyService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageResponse<Currency>>> listAllCurrency(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Currency> currencyList = currencyService.getAllCurrency(page, size);
        
        PageResponse<Currency> pageResponse = PageResponse.of(
            currencyList.getContent(),
            currencyList.getNumber() + 1,
            currencyList.getSize(),
            currencyList.getTotalElements());

        // Check if no currencies were found and return a 404 if necessary
        if (currencyList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("No currencies found", null));
        }

        return ResponseEntity.ok(ApiResponse.success("Currency fetch success!", pageResponse));
    }
}
