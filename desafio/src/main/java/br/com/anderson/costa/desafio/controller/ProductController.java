package br.com.anderson.costa.desafio.controller;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        List<ProductDTO> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> listProductById(@PathVariable String id) {
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public String listProductsWithFilter() {
        return null;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {

        final ProductDTO product = productService.createOrUpdateProduct(productDTO);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping
    public String updateProduct() {
        return null;
    }

    @DeleteMapping
    public String removeProduct() {
        return null;
    }

}
