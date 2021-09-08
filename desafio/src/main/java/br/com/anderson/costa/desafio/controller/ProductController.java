package br.com.anderson.costa.desafio.controller;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController implements ProductsDocs {

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
    public ResponseEntity<List<ProductDTO>> listProductsWithFilter(@RequestParam(value = "min_price", required = false) String minPrice,
                                                                   @RequestParam(value = "max_price", required = false) String maxPrice,
                                                                   @RequestParam(value = "q", required = false) String q) {

        return ResponseEntity.ok(productService.getResponseFilter(minPrice, maxPrice, q));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO, UriComponentsBuilder uriBuilder) {

        final ProductDTO product = productService.createOrUpdateProduct(productDTO, null);
        if (product != null) {
            URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
            return ResponseEntity.created(uri).body(product);
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
