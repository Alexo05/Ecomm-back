package org.usermicroservice.mappers;

import org.springframework.stereotype.Component;
import org.usermicroservice.dtos.RoleDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.Role;
import org.usermicroservice.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapper {
    public static UserDTO userToDto(User user){
        if(user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setNumberPhone(user.getNumberPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setConfirmationToken(user.getConfirmationToken());
        userDTO.setResetPasswordToken(user.getResetPasswordToken());

        // Handle the case where roles might be null
        List<Role> roles = (List<Role>) user.getRoles();
        if (roles != null) {
            List<RoleDTO> roleDTOs = roles.stream()
                    .map(RoleMapper::toDTO)
                    .collect(Collectors.toList());
            userDTO.setRoles(roleDTOs);
        } else {
            userDTO.setRoles(Collections.emptySet());
        }

        return userDTO;
    }

    public static User dtoToUser(UserDTO userDTO){
        if(userDTO == null) return null;

        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setNumberPhone(userDTO.getNumberPhone());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIsActive(userDTO.getIsActive());
        user.setEnabled(userDTO.isEnabled());
        user.setConfirmationToken(userDTO.getConfirmationToken());
        user.setResetPasswordToken(userDTO.getResetPasswordToken());

        // Handle the case where roles might be null
        List<RoleDTO> roleDTOs = (List<RoleDTO>) userDTO.getRoles();
        if (roleDTOs != null) {
            List<Role> roles = roleDTOs.stream()
                    .map(RoleMapper::toRole)
                    .collect(Collectors.toList());
            user.setRoles(roles);
        } else {
            user.setRoles(Collections.emptySet());
        }

        return user;
    }

    public static List<User> usersDtoToUsers(List<UserDTO> userDTOList) {
        if(userDTOList == null || userDTOList.isEmpty()) return null;
        return userDTOList.stream()
                .map(UserMapper::dtoToUser)
                .collect(Collectors.toList());
    }

    public static List<UserDTO> usersToUsersDto(List<User> users){
        if(users == null || users.isEmpty()) return null;
        return users.stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }
}

