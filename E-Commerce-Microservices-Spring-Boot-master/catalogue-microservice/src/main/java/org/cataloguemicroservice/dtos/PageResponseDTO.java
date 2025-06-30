package org.cataloguemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponseDTO {
    private List<ProductInfDTO> productInfDTOS;
    private int currentPage;
    private int totalPages;
    private int totalElements;
}
