package org.usermicroservice.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.PageRequestDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.ERole;
import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    PageRequestDTO<User> getUsers(Integer pageNumber, Integer pageSize, String sort);
    List<UserDTO> getAllUsersActive();
    List<UserDTO> getAllUserInActive();
    void registerUser(UserDTO userDto) throws MessagingException;
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    void deleteUserById(Long id);
    void updateUser(Long id,UserDTO user) throws MessagingException;
    boolean existsByEmail(String email);
    ResponseEntity<String> confirmEmail(String confirmationToken);
    void resetPassword(String email) throws MessagingException;
    void changePassword(ChangePasswordDTO dto) throws MessagingException;
    void addRoleToUserByEmail(ERole eRole, String email);
    void activeUser(Long id);
    void inActiveUser(Long id);
}
