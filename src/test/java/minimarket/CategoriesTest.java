package minimarket;

import lombok.SneakyThrows;
import minimarket.dto.CategoryDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;

import static minimarket.util.RetrofitUtil.getCategoryService;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTest {

    @SneakyThrows
    @ParameterizedTest
    @CsvSource({
            "1, Food",
            "2, Electronic"
    })
    void getCategoryTest(int id, String title) {
        Response<CategoryDto> categoryDtoResponse = getCategoryService().getCategory(id)
                .execute();
        CategoryDto categoryDto = categoryDtoResponse.body();
        assertThat(categoryDtoResponse.isSuccessful()).isTrue();
        assertThat(categoryDto.getId().equals(id)).isTrue();
        assertThat(categoryDto.getTitle().equals(title)).isTrue();
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {0,3,4})
    void getWrongCategoryTest(int id) {
        Response<CategoryDto> categoryDtoResponse = getCategoryService().getCategory(id)
                .execute();
        assertThat(categoryDtoResponse.isSuccessful()).isFalse();
    }
}
