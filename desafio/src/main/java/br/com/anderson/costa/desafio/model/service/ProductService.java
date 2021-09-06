package br.com.anderson.costa.desafio.model.service;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductDTO> getAll() {
        final List<Product> products = repository.findAll();
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public ProductDTO getProductById(String id) {
        try {
            final Product product = repository.getById(Integer.parseInt(id));
            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDTO createOrUpdateProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        Product productSave;
        if(productDTO.getId() == null) {
            productSave = repository.save(product);
             productDTO.setId(String.valueOf(productSave.getId()));
        } else if(!productDTO.getId().isEmpty()) {
            Product productUpdate = repository.getById(Integer.valueOf(productDTO.getId()));
            productUpdate.setName(productDTO.getName());
            productUpdate.setDescription(productDTO.getDescription());
            productUpdate.setPrice(productDTO.getPrice());

            repository.save(productUpdate);
        }
        return productDTO;
    }
}
