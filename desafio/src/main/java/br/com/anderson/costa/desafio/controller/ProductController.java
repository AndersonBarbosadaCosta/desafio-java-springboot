package br.com.anderson.costa.desafio.controller;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> listProductById(@PathVariable String id) {
        Optional<Product> productDTO = productService.getProductById(id);
        return productDTO.map(product -> ResponseEntity.ok(new ProductDTO(product))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Product> listProductsWithFilter(@RequestParam double min_price, @RequestParam double max_price, @RequestParam String q) {
        return productService.findByPriceBetweenName(min_price, max_price, q);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO) {

        final ProductDTO product = productService.createOrUpdateProduct(productDTO, null);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO, @PathVariable String id) {

        final ProductDTO product = productService.createOrUpdateProduct(productDTO, id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable String id) {
        boolean removed = productService.removeProduct(id);
        if (removed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
