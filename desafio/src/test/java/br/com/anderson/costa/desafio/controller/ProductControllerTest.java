package br.com.anderson.costa.desafio.controller;

import br.com.anderson.costa.desafio.builder.ProductDTOBuilder;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.model.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.Optional;

import static br.com.anderson.costa.desafio.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private static final String PRODUCTS_API_URL_PATH = "/products";

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTisCalledThenAProductIsCreated() throws Exception {

        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        when(productService.createOrUpdateProduct(productDTO, null)).thenReturn(productDTO);

        mockMvc.perform(post(PRODUCTS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.price", is(productDTO.getPrice()))

                );
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenStatusOkIsReturned() throws Exception {

        //given
        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.removeProduct(productDTO.getId())).thenReturn(true);

        //then
        mockMvc.perform(delete(PRODUCTS_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithInValidIdThenNotFoundIsReturned() throws Exception {

        //given
        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        //when
        when(productService.removeProduct(productDTO.getId())).thenReturn(false);

        //then
        mockMvc.perform(delete(PRODUCTS_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithoutProductsIsCalledThenOkStatusIsReturned() throws Exception {

        when(productService.getAllProducts()).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get(PRODUCTS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
    }

    @Test
    void whenGETListWithProductsIsCalledThenOkStatusIsReturned() throws Exception {

        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        when(productService.getAllProducts()).thenReturn(Collections.singletonList(productDTO));

        mockMvc.perform(get(PRODUCTS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(productDTO.getName())))
                .andExpect(jsonPath("$[0].description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$[0].price", is(productDTO.getPrice()))

                );
    }

    @Test
    void whenGETIsCalledWithRegisteredIdThenStatusOkIsReturned() throws Exception {

        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        when(productService.getProductById(productDTO.getId())).thenReturn(Optional.of(new Product(productDTO)));

        mockMvc.perform(get(PRODUCTS_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {

        var productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        when(productService.getProductById(productDTO.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get(PRODUCTS_API_URL_PATH + "/" + productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
