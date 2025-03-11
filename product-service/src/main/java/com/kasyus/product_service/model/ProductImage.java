package com.kasyus.product_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "is_cover_image", nullable = false)
    private Boolean coverImage;

    public ProductImage() {
    }

    public ProductImage(String imageUrl, Boolean coverImage, Product product) {
        this.imageUrl = imageUrl;
        this.coverImage = coverImage;
        this.product = product;
    }

    // Getter ve Setter’lar
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean isCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Boolean isCoverImage) {
        this.coverImage = isCoverImage;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", productId=" + (product != null ? product.getId() : null) +
                ", coverImage=" + coverImage +
                '}';
    }
}