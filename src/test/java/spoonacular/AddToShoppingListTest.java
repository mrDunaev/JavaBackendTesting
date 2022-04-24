package spoonacular;

import com.github.javafaker.Faker;
import spoonacular.dto.AddToShoppingListRequest;
import spoonacular.dto.CreateUserRequest;
import spoonacular.dto.CreateUserResponse;
import spoonacular.extensions.SpoonApiTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class AddToShoppingListTest extends BaseSpoonTest {
    private static CreateUserResponse createUserResponse;
    private static ResponseSpecification successfulResponse;
    private static RequestSpecification hashParam;

    private final static String HASH_KEY = "hash";
    private final static String AISLES_KEY = "aisles";

    private int id;

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();
        successfulResponse = new ResponseSpecBuilder()
                .expectStatusCode(SUCCESS_STATUS_CODE)
                .build();
        createUserResponse = given()
                .body(CreateUserRequest.builder()
                        .username(faker.dune().character())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .build())
                .post(CONNECT_USER)
                .then()
                .spec(successfulResponse)
                .extract()
                .as(CreateUserResponse.class);
        hashParam = new RequestSpecBuilder()
                .addQueryParam(HASH_KEY, createUserResponse.getHash())
                .build();
    }

    @BeforeEach
    void setUp() {
        given()
                .spec(hashParam)
                .get(GET_SHOPPING_LIST, createUserResponse.getUsername())
                .then()
                .spec(successfulResponse)
                .body(AISLES_KEY, Matchers.hasSize(0));
    }

    @ParameterizedTest
    @CsvSource(value = {"300 g cheese,Cheese", "500 g pasta,Pasta"})
    void addToShoppingListTest(String item, String aisle) {
        given()
                .spec(hashParam)
                .body(AddToShoppingListRequest.builder()
                        .item(item)
                        .aisle(aisle)
                        .parse(true)
                        .build())
                .post(ADD_TO_SHOPPING_LIST, createUserResponse.getUsername())
                .then()
                .spec(successfulResponse);

        id = given()
                .spec(hashParam)
                .get(GET_SHOPPING_LIST, createUserResponse.getUsername())
                .then()
                .spec(successfulResponse)
                .body(AISLES_KEY, Matchers.hasSize(1))
                .body("aisles.aisle[0]", Matchers.equalTo(aisle))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void tearDown() {
        given()
                .spec(hashParam)
                .delete(DELETE_FROM_SHOPPING_LIST, createUserResponse.getUsername(), id)
                .then()
                .spec(successfulResponse);
    }
}
