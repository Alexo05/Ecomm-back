package org.usermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequestDTO<T> {
    private List<T> elements;
    private int currentPage;
    private int totalPages;
    private int totalElements;
}
