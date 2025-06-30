package org.paimentmicroservice.client;

import org.paimentmicroservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice")
public interface UserServiceClient {

    @GetMapping("/users/getUserByEmail/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);
}
