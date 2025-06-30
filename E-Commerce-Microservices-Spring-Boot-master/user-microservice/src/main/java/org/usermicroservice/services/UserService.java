package org.usermicroservice.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.PageRequestDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.emails.IMailService;
import org.usermicroservice.entities.Role;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.CustomerEmailMessage;
import org.usermicroservice.enums.CustomerMessageError;
import org.usermicroservice.enums.ERole;
import org.usermicroservice.exceptions.DataNotValidException;
import org.usermicroservice.exceptions.RoleNotFoundException;
import org.usermicroservice.exceptions.UserAlreadyConsistRoleException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.mappers.UserMapper;
import org.usermicroservice.repositories.RoleRepository;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.utils.TokenGenerator;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IMailService iMailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Get all users");
        return UserMapper.usersToUsersDto(userRepository.findAll());
    }

    @Override
    public PageRequestDTO<User> getUsers(Integer pageNumber, Integer pageSize, String sort) {
        Pageable pageable;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        Page<User> productPage = userRepository.findAll(pageable);
        return new PageRequestDTO<>(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getTotalPages(),
                (int) productPage.getTotalElements()
        );
    }

    @Override
    public List<UserDTO> getAllUsersActive() {
        log.info("Get All Users Active  ");
        return UserMapper.usersToUsersDto(userRepository.findAll()
                .stream()
                .filter(user -> user.getIsActive().equals(Active.ACTIVE))
                .collect(Collectors.toList()));
    }

    @Override
    public List<UserDTO> getAllUserInActive() {
        log.info("Get All Users InActive ");
        return UserMapper.usersToUsersDto(userRepository.findAll()
                .stream()
                .filter(user -> user.getIsActive().equals(Active.INACTIVE))
                .collect(Collectors.toList()));
    }

    @Override
    public void registerUser(UserDTO userDTO) throws MessagingException {
        log.info("Creating new user with email : {}", userDTO.getEmail());
        Role userRole = roleRepository.findByRole(ERole.valueOf(ERole.USER.name())).orElseThrow(
                () -> new RoleNotFoundException(CustomerMessageError.ROLE_NOT_FOUND_WITH_ROLE_EQUALS.getMessage() + ERole.USER.name()));
        User toSave = User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .numberPhone(userDTO.getNumberPhone())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(List.of(userRole))
                .isActive(Active.ACTIVE)
                .isEnabled(true)
                .confirmationToken(TokenGenerator.generateToken())
                .build();
        userRepository.save(toSave);
        iMailService.sendConfirmationEmail(toSave, "ecommercemicroservice2024@gmail.com");
        ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        return UserMapper.userToDto(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user by email : {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new
                        UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + email));
        return UserMapper.userToDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Deactivate user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        user.setIsActive(Active.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, UserDTO user) throws MessagingException {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setNumberPhone(user.getNumberPhone());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        //this.registerUser(UserMapper.userToDto(existingUser));
        log.info("User with id {} updated successfully", id);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.info("Check existing user by email: {}", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<String> confirmEmail(String confirmationToken) {
        log.info("Confirmation Email ");
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token!"));
        user.setVerifiedAt(new Date());
        user.setEnabled(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
        return ResponseEntity.ok("Your Email confirmed successfully.");
    }

    @Override
    public void resetPassword(String email) throws MessagingException {
        log.info("Reset Password  ");
        if (email == null) throw new
                DataNotValidException(CustomerMessageError.EMAIL_IS_REQUIRED.getMessage());
        User user = userRepository.findByEmail(email.toLowerCase()).orElseThrow(null);
        if (user != null && user.getVerifiedAt() != null && user.isEnabled()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date expiryDate = calendar.getTime();
            user.setResetPasswordTokenExpiryDate(expiryDate);
            user.setResetPasswordToken(TokenGenerator.generateToken());
            updateUser(user.getUserId(), UserMapper.userToDto(user));
            UserDTO userDTO = UserMapper.userToDto(user);
            iMailService.sendResetPasswordMail(user.getEmail(),
                    CustomerEmailMessage.RESET_PASSWORD_SUBJECT.getMessage(), userDTO);
        } else {
            throw new RuntimeException("No account found with this email address!");
        }
    }

    @Override
    public void changePassword(ChangePasswordDTO dto) throws MessagingException {
        log.info("Change password : {}", "********");
        User appUser = userRepository.findByResetPasswordToken(dto.getToken()).orElseThrow();
        if (appUser != null) {
            if (appUser.getResetPasswordTokenExpiryDate() != null &&
                    appUser.getResetPasswordTokenExpiryDate().before(new Date())) {
                throw new RuntimeException("Le jeton de réinitialisation de mot de passe a expiré.");
            }
            appUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            appUser.setResetPasswordToken(null);
            appUser.setResetPasswordTokenExpiryDate(null);
            updateUser(appUser.getUserId(), UserMapper.userToDto(appUser));
            UserMapper.userToDto(appUser);
        }
    }

    @Override
    public void addRoleToUserByEmail(ERole eRole, String email) {
        log.info("Add Role to  user with email : {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + email
        ));
        Role role = roleRepository.findByRole(eRole).orElseThrow(() -> new RoleNotFoundException(
                CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + eRole
        ));
        if( user.getRoles().contains(role))
            throw new UserAlreadyConsistRoleException(CustomerMessageError.USER_ALREADY_HAS_ROLE_ROLE.getMessage() + role.getRole());
        user.getRoles().add(role);
    }

    @Override
    public void activeUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException(CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        if (user.getIsActive() == Active.ACTIVE) {
            UserMapper.userToDto(user);
        } else {
            user.setIsActive(Active.INACTIVE);
            UserMapper.userToDto(user);
        }
    }

    @Override
    public void inActiveUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException(CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        if (user.getIsActive() == Active.INACTIVE) {
            UserMapper.userToDto(user);
        } else {
            user.setIsActive(Active.INACTIVE);
            UserMapper.userToDto(user);
        }
    }

}


