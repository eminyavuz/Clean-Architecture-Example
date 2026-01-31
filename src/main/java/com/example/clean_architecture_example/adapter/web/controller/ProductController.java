package com.example.clean_architecture_example.adapter.web.controller;

import com.example.clean_architecture_example.adapter.web.dto.request.CreateProductRequest;
import com.example.clean_architecture_example.adapter.web.dto.request.UpdateProductPriceRequest;
import com.example.clean_architecture_example.adapter.web.dto.request.UpdateProductStockRequest;
import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.application.usecase.product.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
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

    @PostMapping("/create")
    public ProductResponse create(@RequestBody CreateProductRequest request )
    {
        int id= createProductUseCase.execute(

                request.getProductName(),
                request.getPrice(),
                request.getDescription(),
                request.getStock(),
                request.getIsActive()
        );

        return getProductUseCase.execute(id);
    }
    @GetMapping("/{productId}")
    public ProductResponse getProduct( @PathVariable int productId)
    {
        return getProductUseCase.execute(productId);
    }

    @PutMapping("/{productId}/price")
    public void updateProductPrice(@PathVariable int productId,
                                   @RequestBody UpdateProductPriceRequest request)
    {
       updateProductPriceUseCase.execute(productId,request.getNewPrice());
    }

    @PutMapping("/{productId}/stock")
    public void setUpdateProductStockUseCase(@PathVariable  int productId,
                                             @RequestBody UpdateProductStockRequest request)
    {
        updateProductStockUseCase.execute(productId,request.getNewStock());
    }

    @PutMapping("/{productId}/activate")
    public  void activateProduct(@PathVariable int productId)
    {
        activateProductUseCase.execute(productId);
    }

    @PutMapping("/{productId}/deactivate")
    public  void  deactivateProduct(@PathVariable int productId)
    {
        deactivateProductUseCase.execute(productId);
    }

}
