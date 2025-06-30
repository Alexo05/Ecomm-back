package org.cataloguemicroservice.mappers;

import org.cataloguemicroservice.base.BaseMapper;
import org.cataloguemicroservice.dtos.ProductDTO;
import org.cataloguemicroservice.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product,ProductDTO> {

}
