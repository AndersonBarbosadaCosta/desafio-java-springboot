package br.com.anderson.costa.desafio.model.entity;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private double price;

    public Product(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.price = productDTO.getPrice();
    }
}
