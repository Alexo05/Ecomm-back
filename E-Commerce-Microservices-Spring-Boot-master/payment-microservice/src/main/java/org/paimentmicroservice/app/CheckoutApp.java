package org.paimentmicroservice.app;

import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.paimentmicroservice.client.OrderServiceClient;
import org.paimentmicroservice.client.UserServiceClient;
import org.paimentmicroservice.dto.Order;
import org.paimentmicroservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutApp {
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;

    public Pair<Order, UserDTO> onSuccess(Order order, String email) {
        UserDTO user = userServiceClient.getUserByEmail(email).getBody();
        if (user != null) {
            order.setUserId(user.getUserId());
            ResponseEntity<Order> savedOrder = orderServiceClient.handleSave(order);
            return new Pair<>(savedOrder.getBody(), user);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

}
