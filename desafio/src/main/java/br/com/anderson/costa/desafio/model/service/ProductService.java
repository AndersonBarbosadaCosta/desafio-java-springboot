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

    public List<ProductDTO> converterToProductDTO(List<Product> products) {
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts() {
        final List<Product> products = repository.findAll();
        return converterToProductDTO(products);
    }

    public List<ProductDTO> getProductsPriceLessThan(String price) {
        final List<Product> products = repository.findByPriceLessThanEqual(Double.parseDouble(price));
        return converterToProductDTO(products);
    }

    public List<ProductDTO> getProductsPriceGreaterThan(String price) {
        final List<Product> products = repository.findByPriceGreaterThanEqual(Double.parseDouble(price));
        return converterToProductDTO(products);
    }

    public List<ProductDTO> getProductsByMinAndGreaterPrice(String minPrice, String maxPrice) {
        final List<Product> products = repository.findByPriceBetween(Double.parseDouble(minPrice), Double.parseDouble(maxPrice));
        return converterToProductDTO(products);
    }

    public List<ProductDTO> getProductsByNameOrDescription(String q) {
        final List<Product> products = repository.findByNameContains(q);
        return converterToProductDTO(products);
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

    public List<ProductDTO> findByPriceBetweenName(String minPrice, String maxPrice, String q) {
        List<Product> list = repository.findPriceBetweenNameEquals(Double.parseDouble(minPrice)
                , Double.parseDouble(maxPrice), q);
        return converterToProductDTO(list);
    }

    public List<ProductDTO> getResponseFilter(String minPrice, String maxPrice, String q) {
        if (minPrice == null && maxPrice == null && q == null) {
            return getAllProducts();
        } else if (minPrice != null && maxPrice == null && q == null) {
            return getProductsPriceLessThan(minPrice);
        } else if (maxPrice != null && minPrice == null && q == null) {
            return getProductsPriceGreaterThan(maxPrice);
        } else if (minPrice != null && maxPrice != null && q == null) {
            return getProductsByMinAndGreaterPrice(minPrice, maxPrice);
        } else if (minPrice == null && maxPrice == null) {
            return getProductsByNameOrDescription(q);
        } else {
            return findByPriceBetweenName(minPrice, maxPrice, q);
        }
    }
}
