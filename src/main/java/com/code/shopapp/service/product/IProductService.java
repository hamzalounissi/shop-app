package com.code.shopapp.service.product;

import com.code.shopapp.model.Product;
import com.code.shopapp.request.AddProductRequest;
import com.code.shopapp.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);

    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductByBrandAndName(String brand, String name);

}
