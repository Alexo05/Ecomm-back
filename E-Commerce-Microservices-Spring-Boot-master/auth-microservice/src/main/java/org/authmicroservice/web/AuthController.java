package org.authmicroservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.authmicroservice.dto.*;
import org.authmicroservice.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {return ResponseEntity.ok(authService.login(request));}

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {return ResponseEntity.ok(authService.register(request));}

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> validationEmail(@RequestParam("token") String confirmationToken) {
        return authService.confirmEmail(confirmationToken);
    }

    /**
     * Endpoint to handle password reset request.
     * @param email The email address of the user requesting the password reset.
     * @return ResponseEntity indicating the result of the password reset request.
     * @throws MessagingException If there is an error sending the reset email.
     */

    @PostMapping(value = "/reset-password", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> handleResetPassword(@RequestBody String email) throws MessagingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode emailNode = mapper.readTree(email);
        String test = emailNode.get("email").asText();
        return authService.handleResetPassword(test);
    }

    /**
     * Endpoint to handle password change request.
     * @param token The token received by the user for password reset.
     * @param changePasswordDTO The DTO containing the new password and the token.
     * @return ResponseEntity indicating the result of the password change.
     */

    @PostMapping(value = "/change-password", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.handleChangePassword(changePasswordDTO);
        return ResponseEntity.ok("Password changed successfully");
    }

}
