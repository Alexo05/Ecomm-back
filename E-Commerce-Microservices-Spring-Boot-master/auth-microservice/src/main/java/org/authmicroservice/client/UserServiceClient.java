package org.authmicroservice.client;

import jakarta.mail.MessagingException;
import org.authmicroservice.dto.ChangePasswordDTO;
import org.authmicroservice.dto.RegisterRequestDTO;
import org.authmicroservice.dto.RegisterResponseDTO;
import org.authmicroservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-microservice")
public interface UserServiceClient {

    @PostMapping("/users/register")
    ResponseEntity<RegisterResponseDTO> save(@RequestBody RegisterRequestDTO request);

    @GetMapping("/users/getUserByEmail/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);

    @GetMapping("/users/existsByEmail/{email}")
    boolean existsByEmail(@PathVariable String email);

    @GetMapping(value = "/users/confirm-account", produces = "application/json")
    ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken);

    @PostMapping("/users/reset-password")
    ResponseEntity<String> handleResetPassword(@RequestBody String email) throws MessagingException;

    @PostMapping("/users/changer-mot-de-passe")
    ResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO);

}
