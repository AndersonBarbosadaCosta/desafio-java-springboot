package br.com.anderson.costa.desafio.model.dto;

import br.com.anderson.costa.desafio.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;
    @NotNull(message = "The value should not be null")
    @NotEmpty(message = "The value should not be empty")
    private String name;
    @NotNull(message = "The value should not be null")
    @NotEmpty(message = "The value should not be empty")
    private String description;
    @Min(value = 0L, message = "The value must be positive")
    private double price;

    public ProductDTO(Product product) {
        this.id = String.valueOf(product.getId());
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
