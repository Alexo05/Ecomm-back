package org.cataloguemicroservice.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cataloguemicroservice.app.CategoryApp;
import org.cataloguemicroservice.dtos.CategoryPageDTO;
import org.cataloguemicroservice.dtos.PageRequestDTO;
import org.cataloguemicroservice.dtos.ThreeCategory;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import org.cataloguemicroservice.services.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryApp categoryApp;
    @Qualifier("ICategoryService")
    private final ICategoryService iCategoryService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private List<Category> getAllCategories() {
        log.info("allProducts");
        return iCategoryService.getAllCategories();
    }

    @GetMapping(value = "/{pageNumber}/{pageSize}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ThreeCategory handleCategories(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, String sort) {
        return categoryApp.getIndex(pageNumber, pageSize, sort);
    }

    @GetMapping(value = "/page/{pageNumber}/{pageSize}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private PageRequestDTO<Category> paginationCategories(@PathVariable Integer pageNumber,
                                                          @PathVariable Integer pageSize,
                                                          String sort) {
        return iCategoryService.getCategoryPagination(pageNumber, pageSize, sort);
    }

    @GetMapping(value = "/slug/{categorySlug}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CategoryPageDTO getCategoryBySlug(@PathVariable("categorySlug") String categorySlug) {
        return categoryApp.getCategorySlug(categorySlug);
    }

    @GetMapping(value = "/label/{categoryLabel}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CategoryPageDTO getCategoryByLabel(@PathVariable("categoryLabel") String categoryLabel) {
        return categoryApp.getCategoryLabel(categoryLabel);
    }


    @PostMapping(value = "/addCategory", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void addCategory(@RequestBody Category category) {
        log.info("Post Save Category");
        iCategoryService.add(category);
    }

    @PutMapping(value = "/updateCategory/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void updateCategory(@PathVariable Long id, @RequestBody Category category) {
        iCategoryService.update(id, category);
    }

    @DeleteMapping(value = "/deleteCategory/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private void deleteCategory(@PathVariable Long id) {
        iCategoryService.delete(id);
    }

    /**
     * String urlParams = request.pathVariable("categorySlug");
     * String[] segments = urlParams.split("/");
     * String categorySlugParam = segments[segments.length - 1];
     */

    /**
     @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
     private List<Page<Category>> getAllCategoriesPage() {
     log.info("allProductsPage");
     return iCategoryService.findAllCategoriesPageable(PageRequest.of(0, 2));
     }
     */


}
