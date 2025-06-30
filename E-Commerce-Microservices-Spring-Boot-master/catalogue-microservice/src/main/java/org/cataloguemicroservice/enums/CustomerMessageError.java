package org.cataloguemicroservice.enums;

public enum CustomerMessageError {
    PRODUCT_NOT_FOUND("Product not found. "),
    CATEGORY_PARENT_NOT_FOUND("Category not found. "),
    CATEGORY_PARENT_NOT_FOUND_WITH_ID_EQUALS("Category  not found with id =  "),
    CATEGORY_NOT_FOUND("Category not found. "),
    PRODUCT_NOT_FOUND_LABEL_EQUALS("Product Not found with label =   "),
    PRODUCT_NOT_FOUND_WITH_SLUG_EQUALS("Product Not found with slug =   "),
    CATEGORY_NOT_FOUND_WITH_LABEL_EQUALS("Category Not found with label =   "),
    CATEGORY_NOT_FOUND_SLUG_EQUALS("Category Not found with slug =   "),
    PRODUCT_NOT_FOUND_WITH_ID_EQUALS("Product Not found with id =   "),
    CATEGORY_NOT_FOUND_WITH_ID_EQUALS("Category Not found with id =   "),
    CATEGORY_NOT_FOUND_WITH_ID_PARENT_EQUALS("Category Not found with idParent =   "),
    CATEGORY_ALREADY_EXISTS_WITH_ID_EQUALS("Category already exists with id = "),
    PRODUCT_ALREADY_EXISTS_WITH_ID_EQUALS("Product already exists with id = "),
    CATEGORY_INPUT_IS_EMPTY("Category is empty."),
    PRODUCT_INPUT_IS_EMPTY("Product is empty.");

    private final String msg;
    CustomerMessageError(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
