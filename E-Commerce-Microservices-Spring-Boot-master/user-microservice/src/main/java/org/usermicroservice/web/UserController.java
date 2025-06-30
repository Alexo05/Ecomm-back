package org.usermicroservice.web;

import jakarta.mail.MessagingException;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.usermicroservice.converter.RoleEnumConverter;
import org.usermicroservice.dtos.AddRoleRequestDTO;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.PageRequestDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.ERole;
import org.usermicroservice.services.IUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final IUserService iUserService;
    private final RoleEnumConverter roleEnumConverter;

    /**
     * path : (GET) --> http://localhost:8081/users/
     */

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(iUserService.getAllUsers());
    }

    /**
     * path : (GET) --> http://localhost:8081/users/{id}
     */


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(iUserService.getUserById(id));
    }

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void createUser(@RequestBody UserDTO user) throws MessagingException {
        iUserService.registerUser(user);
    }

    /**
     * path : (GET) --> http://localhost:8081/users/getUserByEmail/{email}
     */

    @GetMapping(value = "/getUserByEmail/{email}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(iUserService.getUserByEmail(email));
    }

    @GetMapping(value = "/{pageNumber}/{pageSize}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private PageRequestDTO<User> pagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, String sort) {
        return iUserService.getUsers(pageNumber, pageSize, sort);
    }

    /**
     * path : (GET) --> http://localhost:8081/users/existsByEmail/{email}
     */
    @GetMapping(value = "/existsByEmail/{email}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean existsByEmail(@PathVariable String email) {
        return iUserService.existsByEmail(email);
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return iUserService.confirmEmail(confirmationToken);
    }

    @PostMapping(value = "/reset-password", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> handleResetPassword(@RequestBody String email) throws MessagingException {
        iUserService.resetPassword(email);
        return ResponseEntity.ok("Un email de réinitialisation a été envoyé à " + email);
    }

    @PostMapping(value = "/changer-mot-de-passe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws MessagingException {
        iUserService.changePassword(changePasswordDTO);
        return ResponseEntity.ok("Le mot de passe a été changé avec succès");
    }

    /**
     * -------------------------- ADMIN -----------------------------
     */
    @GetMapping(value = "/admin/Active", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDTO>> getAllUsersActive() {
        return ResponseEntity.ok(iUserService.getAllUsersActive());
    }

    @GetMapping(value = "/admin/InActive", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDTO>> getAllUsersInActive() {
        return ResponseEntity.ok(iUserService.getAllUserInActive());
    }

    @PutMapping(value = "/admin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO user) throws MessagingException {
        iUserService.updateUser(id, user);
    }

    @PostMapping(value = "/admin/add-role", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addRoleToUserByEmail(@RequestBody AddRoleRequestDTO addRoleRequestDTO) {
        ERole role =roleEnumConverter.convert(addRoleRequestDTO.getERole());
        iUserService.addRoleToUserByEmail(role, addRoleRequestDTO.getEmail());
        return ResponseEntity.ok("Role added successfully.");
    }

    @PutMapping(value = "/admin/{id}/activate", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        iUserService.activeUser(id);
        return ResponseEntity.ok("User activated successfully.");
    }

    @PutMapping(value = "/admin/{id}/deactivate", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        iUserService.inActiveUser(id);
        return ResponseEntity.ok("User deactivated successfully.");
    }

    /**
     * path : (Delete) --> http://localhost:8081/users/admin/{id}
     */
    @DeleteMapping(value = "/admin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void deleteUserById(@PathVariable Long id) {
        iUserService.deleteUserById(id);
    }

}
