package com.mertalptekin.springbootrestapp.application.product.getProductById;

import com.mertalptekin.springbootrestapp.application.product.IProductRequestHandler;
import com.mertalptekin.springbootrestapp.domain.entity.Product;
import com.mertalptekin.springbootrestapp.domain.service.product.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class GetProductByIdHandler implements IProductRequestHandler<GetProductByIdRequest,GetProductByIdResponse> {

    private final IProductService productService;

    public GetProductByIdHandler(IProductService productService) {
        this.productService = productService;
    }


    @Override
    public GetProductByIdResponse handle(GetProductByIdRequest request) {

        Product entity = this.productService.getProductById(request.productId());

        GetProductByIdResponse response = new GetProductByIdResponse();
        BeanUtils.copyProperties(entity,response);

        return response;
    }
}
