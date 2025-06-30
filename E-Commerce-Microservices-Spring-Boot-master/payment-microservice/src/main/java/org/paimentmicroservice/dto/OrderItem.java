package org.paimentmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class OrderItem {
    private String productId;
    private String label;
    private String price;
    private int qty;
}
