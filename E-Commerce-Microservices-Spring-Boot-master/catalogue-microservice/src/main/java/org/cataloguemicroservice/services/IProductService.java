package org.cataloguemicroservice.services;

import org.cataloguemicroservice.base.BaseService;
import org.cataloguemicroservice.dtos.PageRequestDTO;
import org.cataloguemicroservice.dtos.PageResponseDTO;
import org.cataloguemicroservice.dtos.ProductDTO;
import org.cataloguemicroservice.entities.Product;
import org.springframework.data.domain.Page;
import java.util.List;


public interface IProductService extends BaseService<Product> {
    Product findById(Long id);
    List<ProductDTO> getAllProducts();
    List<Product> getProductById(Long id);
    Product getProductBySlug(String slug);
    Product getProductByLabel(String label);
    Page<Product> getProductPagination(Integer pageNumber, Integer pageSize, String sort);
    PageRequestDTO<Product> getCategoryPagination(Integer pageNumber, Integer pageSize, String sort);
    PageResponseDTO getProductInf(Integer pageNumber, Integer pageSize, String sort);

}
