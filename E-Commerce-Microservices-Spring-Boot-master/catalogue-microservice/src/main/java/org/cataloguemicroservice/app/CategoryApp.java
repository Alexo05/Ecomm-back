package org.cataloguemicroservice.app;

import lombok.AllArgsConstructor;
import org.cataloguemicroservice.dtos.BreadcrumbDTO;
import org.cataloguemicroservice.dtos.CategoryPageDTO;
import org.cataloguemicroservice.dtos.ThreeCategory;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import org.cataloguemicroservice.mappers.CategoryMapper;
import org.cataloguemicroservice.repositories.CategoryRepository;
import org.cataloguemicroservice.repositories.ProductRepository;
import org.cataloguemicroservice.services.ICategoryService;
import org.cataloguemicroservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryApp {
    @Qualifier("ICategoryService")
    private final ICategoryService iCategoryService;
    private final IProductService iProductService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ThreeCategory getIndex(Integer pageNumber, Integer pageSize, String sort) {
        ThreeCategory threeCategory = new ThreeCategory();
        threeCategory.setCategoriesTrees(iCategoryService.getHierarchyCategories().getCategoriesTrees());
        threeCategory.setAllProductsPage(iProductService.getProductInf(pageNumber,pageSize,sort));
        return threeCategory;
    }

    public CategoryPageDTO getCategorySlug(String slug) {
        Category category = iCategoryService.getCategoryBySlug(slug);
        Category rootCategory = category.getIdParent() == 0 ?
                category : iCategoryService.getCategoryById(category.getIdParent());
        List<Category> subCategories = category.getIdParent() == 0 ?
                categoryRepository.findCategoriesByIdParent(category.getCategoryId()) : List.of(category);
        List<Product> productList = category.getIdParent() != 0 ?
                iProductService.getProductById(category.getCategoryId()) : Collections.emptyList();
        BreadcrumbDTO rootBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug(), rootCategory.getLabel());
        BreadcrumbDTO categoryBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug(), category.getLabel());
        List<BreadcrumbDTO> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(rootBreadcrumb);
        breadcrumbs.add(categoryBreadcrumb);
        return new CategoryPageDTO(rootCategory, subCategories, productList, breadcrumbs);
    }

    public CategoryPageDTO getCategoryLabel(String label) {
        Category category = iCategoryService.getCategoryByLabel(label);
        Category rootCategory = category.getIdParent() == 0 ?
                category : iCategoryService.getCategoryById(category.getIdParent());
        List<Category> subCategories = category.getIdParent() == 0 ?
                categoryRepository.findCategoriesByIdParent(category.getCategoryId()) : List.of(category);
        List<Product> productList = category.getIdParent() != 0 ?
                iProductService.getProductById(category.getCategoryId()) : Collections.emptyList();
        BreadcrumbDTO rootBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug(), rootCategory.getLabel());
        BreadcrumbDTO categoryBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug(), category.getLabel());
        List<BreadcrumbDTO> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(rootBreadcrumb);
        breadcrumbs.add(categoryBreadcrumb);
        return new CategoryPageDTO(rootCategory, subCategories, productList, breadcrumbs);
    }

}
