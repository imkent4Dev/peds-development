package com.lyhorng.pedssystem.service.branchRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.RequestFor;
import com.lyhorng.pedssystem.repository.branchRequest.RequestForRepository;

@Service
public class RequestForService {
    
    @Autowired
    public RequestForRepository requestForRespository;

    public List<RequestFor> listAllRequestFor() {
        return requestForRespository.findAll();
    }
}
