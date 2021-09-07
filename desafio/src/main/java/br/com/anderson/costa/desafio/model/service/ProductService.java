package br.com.anderson.costa.desafio.model.service;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDTO> getAllProducts() {
        final List<Product> products = repository.findAll();
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public Optional<Product> getProductById(String id) {
        return repository.findById(Integer.parseInt(id));
    }

    @Transactional
    public ProductDTO createOrUpdateProduct(ProductDTO productDTO, String id) {
        Product product = new Product(productDTO);
        Product productSave;
        if (id == null) {
            productSave = repository.save(product);
            productDTO.setId(String.valueOf(productSave.getId()));
        } else {
            Optional<Product> productUpdate = getProductById(id);

            if (productUpdate.isPresent()) {
                productUpdate.get().setName(productDTO.getName());
                productUpdate.get().setDescription(productDTO.getDescription());
                productUpdate.get().setPrice(productDTO.getPrice());

                productDTO.setId(id);
            } else {
                return null;
            }

        }
        return productDTO;
    }

    @Transactional
    public boolean removeProduct(String id) {
        Optional<Product> productDTO = getProductById(id);
        if (productDTO.isPresent()) {
            repository.delete(productDTO.get());
            return true;
        }
        return false;
    }

    public List<Product> findByPriceBetweenName(double minPrice, double maxPrice, String q) {
        return repository.findPriceBetweenNameEquals(minPrice, maxPrice, q);
    }
}
