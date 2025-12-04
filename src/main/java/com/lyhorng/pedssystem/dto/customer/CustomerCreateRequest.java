package com.lyhorng.pedssystem.dto.customer;

import lombok.Data;

@Data
public class CustomerCreateRequest {
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String nid;
}