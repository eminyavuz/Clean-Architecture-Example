package com.example.clean_architecture_example.adapter.web.controller;

import com.example.clean_architecture_example.adapter.web.dto.request.UpdateProductPriceRequest;
import com.example.clean_architecture_example.application.usecase.product.*;
import com.example.clean_architecture_example.domain.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ActivateProductUseCase activateProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductPriceUseCase updateProductPriceUseCase;

    public ProductController
            (
            ActivateProductUseCase activateProductUseCase,
            CreateProductUseCase createProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase,
            GetProductUseCase getProductUseCase,
            UpdateProductStockUseCase updateProductStockUseCase,
            UpdateProductPriceUseCase updateProductPriceUseCase
            )
    {
        this.activateProductUseCase= activateProductUseCase;
        this.deactivateProductUseCase= deactivateProductUseCase;
        this.createProductUseCase=createProductUseCase;
        this.getProductUseCase=getProductUseCase;
        this.updateProductPriceUseCase=updateProductPriceUseCase;
        this.updateProductStockUseCase=updateProductStockUseCase;
    }




}
