package org.paimentmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Order {
    private String id;
    private Long userId;
    private Address deliveryAddress;
    private List<OrderItem> items;
    private Date date;
    private double totalPrice;

    public double getTotalAmount(){
        double total = 0.0;
        if (items != null) {
            for (OrderItem item : items) {
                total += Double.parseDouble(item.getPrice()) * item.getQty();
            }
        }
        return total;
    }
}
