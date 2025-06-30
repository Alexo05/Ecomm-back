package org.authmicroservice.service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.authmicroservice.client.UserServiceClient;
import org.authmicroservice.dto.*;
import org.authmicroservice.enums.CustomerMessageError;
import org.authmicroservice.enums.CustomerMessageValidator;
import org.authmicroservice.exceptions.DataNotValidException;
import org.authmicroservice.exceptions.EmailOrPasswordIncorrectException;
import org.authmicroservice.utils.InputValidatorRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO request) {
        UserDTO userDTO = userServiceClient.getUserByEmail(request.getEmail().toLowerCase()).getBody();
        if (userDTO != null && passwordEncoder.matches(request.getPassword(), userDTO.getPassword())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                return LoginResponseDTO.builder()
                        .email(userDTO.getEmail())
                        .accessToken(jwtService.generateToken(request.getEmail().toLowerCase()))
                        .refreshToken(jwtService.generateToken(request.getEmail().toLowerCase()))
                        .roles(jwtService.getRoles(request.getEmail().toLowerCase()))
                        .build();
            } else {
                throw new EmailOrPasswordIncorrectException("User is not Authenticated");
            }
        } else {
            throw new EmailOrPasswordIncorrectException("Email or password is incorrect");
        }
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        String errorMessage =
                InputValidatorRegister.isValidPassword(request.getPassword()) ? null : CustomerMessageError.PASSWORD_LENGTH_ERROR.getMessage();
        errorMessage = errorMessage != null ? errorMessage :
                InputValidatorRegister.isValidMoroccanPhoneNumber(request.getNumberPhone()) ? null : CustomerMessageError.PHONE_NUMBER_NOT_VALID.getMessage();
        errorMessage = errorMessage != null ? errorMessage :
                InputValidatorRegister.isValidEmail(request.getEmail()) ? null : CustomerMessageError.EMAIL_IS_INVALID.getMessage();
        errorMessage = errorMessage != null ? errorMessage :
                InputValidatorRegister.isNull(request.getFirstname()) ? CustomerMessageError.FIRSTNAME_IS_REQUIRED.getMessage() : null;
        errorMessage = errorMessage != null ? errorMessage :
                InputValidatorRegister.isNull(request.getLastname()) ? CustomerMessageError.LASTNAME_IS_REQUIRED.getMessage() : null;
        errorMessage = errorMessage != null ? errorMessage :
                userServiceClient.existsByEmail(request.getEmail()) ? CustomerMessageError.EMAIL_ALREADY_EXIST.getMessage() : null;

        if (errorMessage != null) {
            throw new DataNotValidException(errorMessage);
        } else {
            userServiceClient.save(request).getBody();
            return RegisterResponseDTO.builder()
                    .message(CustomerMessageValidator.SAVED_SUCCESSFULLY.getMessage() + " please " +
                            CustomerMessageValidator.CHECK_EMAIL_FOR_VALIDATION_YOUR_MAIL.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseEntity<String> confirmEmail(String confirmationToken) {
        return userServiceClient.confirmUserAccount(confirmationToken);
    }

    @Override
    public ResponseEntity<String> handleResetPassword(String email) throws MessagingException {
        return userServiceClient.handleResetPassword(email.toLowerCase());
    }

    @Override
    public ResponseEntity<String> handleChangePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getToken() == null || changePasswordDTO.getToken().isEmpty())
            throw new DataNotValidException(CustomerMessageError.INVALID_REQUEST.getMessage());
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getMatchPassword()))
            throw new RuntimeException(CustomerMessageError.PASSWORD_MATCH_ERROR.getMessage());
        return userServiceClient.handleChangePassword(changePasswordDTO);
    }

}
