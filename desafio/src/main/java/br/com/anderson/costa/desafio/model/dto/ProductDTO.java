package br.com.anderson.costa.desafio.model.dto;

import br.com.anderson.costa.desafio.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private double price;

    public ProductDTO(Product product) {
        this.id = String.valueOf(product.getId());
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
