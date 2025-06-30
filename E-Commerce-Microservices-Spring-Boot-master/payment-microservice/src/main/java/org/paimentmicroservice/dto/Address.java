package org.paimentmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Address {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    private String email;
    private String zipCode;
    private String phone;
    private String comment;
}
