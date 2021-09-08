package br.com.anderson.costa.desafio.controller;

import br.com.anderson.costa.desafio.model.dto.ErrorMessageDTO;
import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Api("Products Management Compasso UOL")
public interface ProductsDocs {

    @ApiOperation(value = "Get products")
    @ApiResponse(code = 200, message = "")
    ResponseEntity<List<ProductDTO>> listAllProducts();

    @ApiOperation(value = "Get products by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<ProductDTO> listProductById(@PathVariable String id);

    @ApiOperation(value = "Get products using filter")
    @ApiResponse(code = 200, message = "OK", response = ProductDTO.class)
    ResponseEntity<List<ProductDTO>> listProductsWithFilter(@RequestParam(value = "min_price", required = false) String minPrice,
                                                            @RequestParam(value = "max_price", required = false) String maxPrice,
                                                            @RequestParam(value = "q", required = false) String q);

    @ApiOperation(value = "Create product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "missing required fields or field range value", response = ErrorMessageDTO.class)
    })
    ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO, UriComponentsBuilder uriBuilder);

    @ApiOperation(value = "Update product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "missing required fields or field range value", response = ErrorMessageDTO.class)
    })
    ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO, @PathVariable String id);

    @ApiOperation(value = "Remove product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "missing required fields or field range value", response = ErrorMessageDTO.class)
    })
    ResponseEntity<?> removeProduct(@PathVariable String id);
}
