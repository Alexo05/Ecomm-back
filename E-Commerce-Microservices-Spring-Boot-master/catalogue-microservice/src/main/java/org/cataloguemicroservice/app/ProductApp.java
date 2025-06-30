package org.cataloguemicroservice.app;

import lombok.AllArgsConstructor;
import org.cataloguemicroservice.dtos.BreadcrumbDTO;
import org.cataloguemicroservice.dtos.ProductDetailsDTO;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import org.cataloguemicroservice.repositories.ProductRepository;
import org.cataloguemicroservice.services.ICategoryService;
import org.cataloguemicroservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductApp {
    private final ProductRepository productRepository;
    private final IProductService iProductService;
    @Qualifier("ICategoryService")
    private final ICategoryService iCategoryService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public ProductDetailsDTO getProductBySlug(String slug) {
        Product product = iProductService.getProductBySlug(slug);
        Category category = iCategoryService.getCategoryByIdParent(product.getIdCategory());
        Category rootCategory = category.getIdParent() == 0 ? category :
                iCategoryService.getCategoryById(category.getIdParent());
        BreadcrumbDTO rootBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug(), rootCategory.getLabel());
        BreadcrumbDTO categoryBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug(), category.getSlug());
        BreadcrumbDTO productBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug() + "/" + product.getSlug(), product.getSlug());
        List<BreadcrumbDTO> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(rootBreadcrumb);
        breadcrumbs.add(categoryBreadcrumb);
        breadcrumbs.add(productBreadcrumb);
        return new ProductDetailsDTO(rootCategory, category, product, breadcrumbs);
    }

    public ProductDetailsDTO getProductByLabel(String slug) {
        Product product = iProductService.getProductByLabel(slug);
        Category category = iCategoryService.getCategoryByIdParent(product.getIdCategory());
        Category rootCategory = category.getIdParent() == 0 ? category :
                iCategoryService.getCategoryById(category.getIdParent());
        BreadcrumbDTO rootBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug(), rootCategory.getLabel());
        BreadcrumbDTO categoryBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug(), category.getSlug());
        BreadcrumbDTO productBreadcrumb = new BreadcrumbDTO("/" + rootCategory.getSlug() + "/" + category.getSlug() + "/" + product.getSlug(), product.getSlug());
        List<BreadcrumbDTO> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(rootBreadcrumb);
        breadcrumbs.add(categoryBreadcrumb);
        breadcrumbs.add(productBreadcrumb);
        return  new ProductDetailsDTO(rootCategory, category, product, breadcrumbs);
    }


}
