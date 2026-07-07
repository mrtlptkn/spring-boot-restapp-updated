package com.mertalptekin.springbootrestapp.application.product;

// Port for handling different types of product requests
public interface IProductRequestHandler<TReq extends IProductRequest, TRes extends IProductResponse> {

    TRes handle(TReq request);
}
