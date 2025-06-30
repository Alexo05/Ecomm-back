package org.authmicroservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.authmicroservice.enums.ERole;

@Data
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private ERole role;

    public RoleDTO(ERole role) {
        this.role = role;
    }
}
