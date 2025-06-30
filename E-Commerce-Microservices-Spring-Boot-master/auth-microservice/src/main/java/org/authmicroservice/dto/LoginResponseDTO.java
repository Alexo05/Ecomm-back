package org.authmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class LoginResponseDTO {
    private String email;
    private String accessToken;
    private String refreshToken;
    private List<String> roles= new ArrayList<>();
}
