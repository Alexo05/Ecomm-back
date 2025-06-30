package org.paimentmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class PaymentResponseDTO {
    private String clientSecret;
}
