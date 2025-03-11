package com.kasyus.product_service.bulk;


import com.kasyus.product_service.model.Category;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.model.ProductImage;
import com.kasyus.product_service.repository.CategoryRepository;
import com.kasyus.product_service.repository.ProductRepository;
import com.kasyus.product_service.requests.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BulkProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public BulkProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void saveProducts(List<BulkProductCreateRequest> products) {
        for (BulkProductCreateRequest request : products) {
            Product product = convertToEntity(request);

            if (!request.imageUrls().isEmpty()) {
                List<ProductImage> productImages = request.imageUrls().stream()
                        .map(img -> new ProductImage(img, false, product))
                        .collect(Collectors.toList());


                if (!productImages.isEmpty()) {
                    productImages.get(0).setCoverImage(true);
                }

                product.setImages(productImages);
            }

            productRepository.save(product);
        }
    }

    private Product convertToEntity(BulkProductCreateRequest request) {
        // categoryId ile Category nesnesini bul
        Optional<Category> categoryOptional = categoryRepository.findById(request.categoryId());

        if (categoryOptional.isEmpty()) {
            throw new IllegalArgumentException("Kategori bulunamadı: ID = " + request.categoryId());
        }

        Category category = categoryOptional.get();

        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                category, // Category nesnesini set et
                request.productType(),
                request.sellerId(),
                request.sku()
        );


        return product;
    }

    @Transactional
    public void saveCategories(List<BulkCategoryCreateRequest> categories) {
        List<Category> categoryEntities = categories.stream()
                .map(request -> new Category(request.id(), request.name()))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categoryEntities);
    }
}
