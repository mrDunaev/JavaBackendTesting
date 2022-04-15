package spoonacular;

import extensions.SpoonApiTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ClassifyCuisineTest {

    @ParameterizedTest
    @CsvSource({
          "ramen, japanese",
          "fish and chips, british",
          "risotto, italian",
          "kugel, jewish"
    })
    void classifyCuisineTest (String title, String cuisine) {
        given()
                .body("title=" + title)
                .post("/recipes/cuisine")
                .then()
                .statusCode(200)
                .body("cuisine", Matchers.equalToIgnoringCase(cuisine));
    }

    @ParameterizedTest
    @ValueSource(strings = {"soup","toast"})
    void cuisineNotFoundTest (String title) {
        given()
                .body("title=" + title)
                .post("/recipes/cuisine")
                .then()
                .statusCode(200)
                .body("confidence", Matchers.equalTo(0.0f));
    }
}
