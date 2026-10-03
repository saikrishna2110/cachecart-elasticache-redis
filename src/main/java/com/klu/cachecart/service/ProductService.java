package com.klu.cachecart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klu.cachecart.Entity.Product;
import com.klu.cachecart.repository.ProductRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    public ProductService(
            ProductRepository productRepository,
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {

        String key = "product:" + id;

        try {
            String cachedProduct = redisTemplate.opsForValue().get(key);

            if (cachedProduct != null) {
                System.out.println("REDIS CACHE HIT: " + key);
                return objectMapper.readValue(cachedProduct, Product.class);
            }

            System.out.println("REDIS CACHE MISS: " + key);

        } catch (Exception e) {
            System.out.println("Redis read error: " + e.getMessage());
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            String json = objectMapper.writeValueAsString(product);
            redisTemplate.opsForValue().set(key, json, CACHE_TTL);
            System.out.println("PRODUCT STORED IN REDIS: " + key);

        } catch (Exception e) {
            System.out.println("Redis write error: " + e.getMessage());
        }

        return product;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {

        Product existing = getProductById(id);

        existing.setName(product.getName());
        existing.setBrand(product.getBrand());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setOriginalPrice(product.getOriginalPrice());
        existing.setRating(product.getRating());
        existing.setReviewCount(product.getReviewCount());
        existing.setStock(product.getStock());
        existing.setDescription(product.getDescription());
        existing.setSpecifications(product.getSpecifications());
        existing.setImage(product.getImage());
        existing.setFeatured(product.getFeatured());

        Product updated = productRepository.save(existing);

        try {
            redisTemplate.delete("product:" + id);
            System.out.println("REDIS CACHE INVALIDATED: product:" + id);
        } catch (Exception e) {
            System.out.println("Redis invalidation error: " + e.getMessage());
        }

        return updated;
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);

        try {
            redisTemplate.delete("product:" + id);
            System.out.println("REDIS CACHE INVALIDATED: product:" + id);
        } catch (Exception e) {
            System.out.println("Redis invalidation error: " + e.getMessage());
        }
    }
}
