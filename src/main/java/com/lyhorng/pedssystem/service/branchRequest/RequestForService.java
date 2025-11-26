package com.lyhorng.pedssystem.service.branchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.RequestFor;
import com.lyhorng.pedssystem.repository.branchRequest.RequestForRepository;

@Service
public class RequestForService {
    
    @Autowired
    public RequestForRepository requestForRespository;

    public Page<RequestFor> listAllRequestFor(int page, int size) {
        int pageIndex = Math.max(0, size -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return requestForRespository.findAll(pageable);
    }
}
