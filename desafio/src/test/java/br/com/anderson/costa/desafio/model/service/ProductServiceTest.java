package br.com.anderson.costa.desafio.model.service;

import br.com.anderson.costa.desafio.model.dto.ProductDTO;
import br.com.anderson.costa.desafio.model.entity.Product;
import br.com.anderson.costa.desafio.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {

    private final ProductDTO productDtoExpected = new ProductDTO("1", "cadeira", "cadeira gamer pro", 899.56);
    private final Product productExpected = new Product(1, "cadeira", "cadeira gamer pro", 899.56);

    @Mock
    private ProductRepository mockRepository;
    private ProductService productService;

    @BeforeEach
    private void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.productService = new ProductService(mockRepository);
    }

    @Test
    public void mustReturnListProducts() {
        Mockito.when(mockRepository.findAll()).thenReturn(products());
        var productList = productService.getAllProducts();
        Assertions.assertEquals(productList.get(0).getId(), productDtoExpected.getId());
    }

    @Test
    public void mustConverterToProductDTO() {
        var listProductsDTO = productService.converterToProductDTO(products());
        Assertions.assertEquals(listProductsDTO.get(0), productDtoExpected);
    }

    @Test
    public void mustReturnListProductsWithMinorPrice() {
        Mockito.when(mockRepository.findByPriceLessThanEqual(Double.parseDouble("899.56")))
                .thenReturn(Collections.singletonList(products().get(0)));
        var listProductsDTO = productService.getProductsPriceLessThan("899.56");
        Assertions.assertEquals(899.56, listProductsDTO.get(0).getPrice());
    }

    @Test
    public void mustReturnListProductsWithPriceGreaterOrEqualsThan() {
        Mockito.when(mockRepository.findByPriceGreaterThanEqual(Double.parseDouble("899.56")))
                .thenReturn(products());
        var listProductsDTO = productService.getProductsPriceGreaterThan("899.56");
        Assertions.assertEquals(productsDto(), listProductsDTO);
    }

    @Test
    public void mustReturnListProductsWithPriceMinorOrEqualsThanAndGreaterOrEqualsThan() {
        Mockito.when(mockRepository.findByPriceBetween(Double.parseDouble("1"), Double.parseDouble("999999.99")))
                .thenReturn(products());
        var listProductsDTO = productService.getProductsByMinAndGreaterPrice("1", "999999.99");
        Assertions.assertEquals(productsDto(), listProductsDTO);
    }

    @Test
    public void mustNotReturnListProductsWithContainNameOrDescriptionEqual() {
        Mockito.when(mockRepository.findByNameContains("cadeira"))
                .thenReturn(Collections.singletonList(products().get(0)));
        var listProductsDTO = productService.getProductsByNameOrDescription("cadeira");
        Assertions.assertNotEquals(productsDto().get(0).getDescription(), listProductsDTO.get(0).getName());
    }

    @Test
    public void mustReturnProductWhenIdValidIsPassed() {
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(productExpected));
        final Optional<Product> productById = productService.getProductById("1");
        Assertions.assertEquals(productExpected, productById.get());
    }

    @Test
    public void mustNotReturnProductWhenIdInValidIsPassed() {
        Mockito.when(mockRepository.findById(2)).thenReturn(Optional.empty());
        final Optional<Product> productById = productService.getProductById("2");
        Assertions.assertFalse(productById.isPresent());
    }

    @Test
    public void mustReturnListProductsWithFilterMinPrice() {
        Mockito.when(mockRepository.findByPriceLessThanEqual(10000.0)).thenReturn(products());
        final List<ProductDTO> responseFilter = productService.getResponseFilter("10000.0", null, null);
        Assertions.assertEquals(2, responseFilter.size());
    }

    @Test
    public void mustReturnListProductsWithFilterMaxPrice() {
        Mockito.when(mockRepository.findByPriceGreaterThanEqual(3899.56)).thenReturn(products());
        final List<ProductDTO> responseFilter = productService.getResponseFilter(null, "3899.56", null);
        Assertions.assertEquals(2, responseFilter.size());
    }

    @Test
    public void mustReturnListProductsWithNameOrDescription() {
        Mockito.when(mockRepository.findByNameContains("cadeira")).thenReturn(Collections.singletonList(products().get(0)));
        final List<ProductDTO> responseFilter = productService.getResponseFilter(null, null, "cadeira");
        Assertions.assertEquals(1, responseFilter.size());
    }

    @Test
    public void mustReturnListProductsWithMinorAndGreaterAndNameOrDescription() {
        Mockito.when(mockRepository.findPriceBetweenNameEquals(1.0, 3899.56, "cadeira")).thenReturn(products());
        final List<ProductDTO> responseFilter = productService.getResponseFilter("1.0", "3899.56", "cadeira");
        Assertions.assertEquals(2, responseFilter.size());
    }

    @Test
    public void mustReturnListWithAllProductsIfValuesNull() {
        Mockito.when(mockRepository.findAll()).thenReturn(products());
        final List<ProductDTO> responseFilter = productService.getResponseFilter(null, null, null);
        Assertions.assertEquals(2, responseFilter.size());
    }

    @Test
    public void mustReturnListProductsWithFilterBetweenMinAndMaxPrice() {
        Mockito.when(mockRepository.findByPriceBetween(1, 10000)).thenReturn(products());
        final List<ProductDTO> responseFilter = productService.getResponseFilter("1", "10000", null);
        Assertions.assertEquals(2, responseFilter.size());
    }

    @Test
    public void mustReturnProductWhenValuesAreValid() {
        Product product = new Product();
        product.setName("cadeira");
        product.setDescription("cadeira gamer pro");
        product.setPrice(899.56);

        Mockito.when(mockRepository.save(Mockito.any(Product.class))).thenReturn(product);

        final ProductDTO productDTO = productService.createOrUpdateProduct(productDtoExpected, null);
        Mockito.verify(mockRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void mustReturnProductWhenValuesAndIdAreValid() {

        Mockito.when(mockRepository.save(Mockito.any(Product.class))).thenReturn(productExpected);
        Mockito.when(mockRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(productExpected));
        final ProductDTO productDTO = productService.createOrUpdateProduct(productDtoExpected, "1");
        Assertions.assertEquals(productDtoExpected, productDTO);
        Mockito.verify(mockRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void mustReturnFalseWhenValuesAndIdNotAreValid() {
        Mockito.when(mockRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        final ProductDTO productDTO = productService.createOrUpdateProduct(productDtoExpected, "3");
        Assertions.assertNull(productDTO);
        Mockito.verify(mockRepository, Mockito.times(1)).findById(3);
    }

    @Test
    public void mustDeleteProductWhenIdIsValid() {

        Mockito.when(mockRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(productExpected));
        final boolean isRemoved = productService.removeProduct(String.valueOf(productExpected.getId()));
        Assertions.assertTrue(isRemoved);
        Mockito.verify(mockRepository, Mockito.times(1)).findById(1);
        Mockito.verify(mockRepository, Mockito.times(1)).delete(productExpected);
    }



    @Test
    public void mustNotDeleteProductWhenIdInValid() {

        Mockito.when(mockRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        final boolean isRemoved = productService.removeProduct("3");
        Assertions.assertFalse(isRemoved);
        Mockito.verify(mockRepository, Mockito.times(1)).findById(3);
    }

    private List<Product> products() {
        var list = new ArrayList<Product>();
        var cadeira = new Product(1, "cadeira", "cadeira gamer pro", 899.56);
        var tv = new Product(2, "TV 50", "Smart TV Phillips", 3899.56);
        list.add(cadeira);
        list.add(tv);
        return list;
    }

    private List<ProductDTO> productsDto() {
        var list = new ArrayList<ProductDTO>();
        var cadeira = new ProductDTO("1", "cadeira", "cadeira gamer pro", 899.56);
        var tv = new ProductDTO("2", "TV 50", "Smart TV Phillips", 3899.56);
        list.add(cadeira);
        list.add(tv);
        return list;
    }
}
