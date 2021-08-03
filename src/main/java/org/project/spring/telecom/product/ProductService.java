package org.project.spring.telecom.product;

import lombok.RequiredArgsConstructor;
import org.project.spring.telecom.infra.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.getProductById(id).orElseThrow(() -> new ProductException("product with id: "
                + id + " doesn't exist"));
    }

    @Timed
    public List<Product> getAllProduct() {
        return productRepository.getAllProducts();
    }
}
