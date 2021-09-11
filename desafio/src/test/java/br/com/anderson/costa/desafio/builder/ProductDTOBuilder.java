package br.com.anderson.costa.desafio.builder;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
public class ProductDTOBuilder {

    @Builder.Default
    private String id = "1";

    @Builder.Default
    private String name = "geladeira";

    @Builder.Default
    private String description = "geladeira duplex";

    @Builder.Default
    private double price = 2899.00;

    public ProductDTO toProductDTO () {
        return new ProductDTO( id, name, description, price);
    }
}
