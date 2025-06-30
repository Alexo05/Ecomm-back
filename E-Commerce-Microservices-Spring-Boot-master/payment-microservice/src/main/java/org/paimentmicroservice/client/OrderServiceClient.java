package org.paimentmicroservice.client;

import org.paimentmicroservice.dto.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-microservice")
public interface OrderServiceClient {

    @PostMapping("/order/save")
    ResponseEntity<Order> handleSave(@RequestBody Order order);
}
