package org.cataloguemicroservice.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cataloguemicroservice.app.ProductApp;
import org.cataloguemicroservice.dtos.PageRequestDTO;
import org.cataloguemicroservice.dtos.ProductDTO;
import org.cataloguemicroservice.dtos.ProductDetailsDTO;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import org.cataloguemicroservice.services.IProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductApp productApp;
    private final IProductService productService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private List<ProductDTO> getAllCategories() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/slug/{productSlug}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProductDetailsDTO getProductBySlug(@PathVariable("productSlug") String productSlug) {
        return productApp.getProductBySlug(productSlug);
    }

    @GetMapping(value = "/label/{productLabel}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProductDetailsDTO getProductByLabel(@PathVariable("productLabel") String productLabel) {
        return productApp.getProductByLabel(productLabel);
    }

    @GetMapping(value = "/page/{pageNumber}/{pageSize}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private PageRequestDTO<Product> pagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, String sort) {
        return productService.getCategoryPagination(pageNumber, pageSize, sort);
    }

    @PostMapping(value = "/addProduct", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void addCategory(@RequestBody Product product) {
        log.info("Add Product");

        productService.add(product);
    }

    @PutMapping(value = "/updateProduct/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void updateCategory(@PathVariable Long id, @RequestBody Product product) {
        productService.update(id, product);
    }

    @DeleteMapping(value = "/deleteProduct/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void deleteCategory(@PathVariable Long id) {
        productService.delete(id);
    }

}
