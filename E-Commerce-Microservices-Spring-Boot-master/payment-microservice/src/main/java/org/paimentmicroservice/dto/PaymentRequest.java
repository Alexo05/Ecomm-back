package org.paimentmicroservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String method;
    private String amount;
    private String currency;
    private String description;
}
