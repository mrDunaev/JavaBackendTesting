package minimarket;

import com.github.javafaker.Faker;
import minimarket.dto.ProductDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static minimarket.util.RetrofitUtil.getCategoryService;
import static minimarket.util.RetrofitUtil.getProductsService;

public class ProductsTest {
    private ProductDto productDto;
    private ProductDto wrongProductDto;
    private static String categoryTitle;
    private final String wrongCategoryTitle = "Book";
    private int productId;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        categoryTitle = Objects.requireNonNull(getCategoryService().getCategory(1).execute().body()).getTitle();
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {
        productId = -1;
        productDto = new ProductDto()
                .withCategoryTitle(categoryTitle)
                .withTitle(new Faker().food().ingredient())
                .withPrice(new Random().nextInt(500));
        wrongProductDto = new ProductDto()
                .withCategoryTitle(wrongCategoryTitle)
                .withTitle(new Faker().book().title())
                .withPrice(new Random().nextInt(1000));
    }

    @SneakyThrows
    @Test
    void createProductTest() {
        Response<ProductDto> productDtoResponse = getProductsService().createProduct(productDto)
                .execute();
        assertThat(productDtoResponse.isSuccessful()).isTrue();
        assertThat(productDtoResponse.body())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(productDto);
        productId = Objects.requireNonNull(productDtoResponse.body()).getId();
    }

    @SneakyThrows
    @Test
    void createProductWithWrongCategoryTest() {
        Response<ProductDto> productDtoResponse = getProductsService().createProduct(wrongProductDto)
                .execute();
        assertThat(productDtoResponse.isSuccessful()).isFalse();
    }

    @SneakyThrows
    @Test
    void changeProductTest() {
        Response<ProductDto> createdProductDtoResponse = getProductsService().createProduct(productDto)
                .execute();
        ProductDto createdProductDto = createdProductDtoResponse.body();
        createdProductDto.setPrice(new Random().nextInt(500) + 500);
        Response<ProductDto> changedProductDtoResponse = getProductsService().changeProduct(createdProductDto)
                .execute();
        assertThat(changedProductDtoResponse.isSuccessful()).isTrue();
        assertThat(changedProductDtoResponse.body())
                .usingRecursiveComparison()
                .isEqualTo(createdProductDto);
        productId = Objects.requireNonNull(changedProductDtoResponse.body()).getId();
    }

    @SneakyThrows
    @Test
    void wrongChangeOfProductCategoryTest() {
        Response<ProductDto> createdProductDtoResponse = getProductsService().createProduct(productDto)
                .execute();
        ProductDto createdProductDto = createdProductDtoResponse.body();
        productId = createdProductDto.getId();
        createdProductDto.setCategoryTitle(wrongCategoryTitle);
        Response<ProductDto> changedProductDtoResponse = getProductsService().changeProduct(createdProductDto)
                .execute();
        assertThat(changedProductDtoResponse.isSuccessful()).isFalse();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        if (productId != -1)
            assertThat(getProductsService().deleteProduct(productId).execute().isSuccessful()).isTrue();
        else
            assertThat(getProductsService().deleteProduct(productId).execute().isSuccessful()).isFalse();
    }
}
