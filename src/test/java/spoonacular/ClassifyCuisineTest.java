package spoonacular;

import extensions.SpoonApiTest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ClassifyCuisineTest extends BaseSpoonTest {
    private static ResponseSpecification successfulResponse;
    private final static String BODY_TITLE = "title=";
    private final static String CUISINE_KEY = "cuisine";
    private final static String CONFIDENCE_KEY = "confidence";
    private final static float  CONFIDENCE_VALUE = 0;

    @BeforeAll
    static void beforeAll() {
        successfulResponse = new ResponseSpecBuilder()
                .expectStatusCode(SUCCESS_STATUS_CODE)
                .build();
    }

    @ParameterizedTest
    @CsvSource({
          "ramen, japanese",
          "fish and chips, european",
          "risotto, mediterranean",
          "kugel, jewish"
    })
    void classifyCuisineTest (String title, String cuisine) {
        given()
                .body(BODY_TITLE + title)
                .post(CLASSIFY_CUISINE)
                .then()
                .spec(successfulResponse)
                .body(CUISINE_KEY, Matchers.equalToIgnoringCase(cuisine))
                .body(CONFIDENCE_KEY, Matchers.greaterThan(CONFIDENCE_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"soup","toast"})
    void cuisineNotFoundTest (String title) {
        given()
                .body(BODY_TITLE + title)
                .post(CLASSIFY_CUISINE)
                .then()
                .spec(successfulResponse)
                .body(CONFIDENCE_KEY, Matchers.equalTo(CONFIDENCE_VALUE));
    }
}
