package extensions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static config.SpoonConfig.spoonConfig;

public class SpoonApiTestExtension implements BeforeAllCallback {
    private final static String API_KEY= "apiKey";

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.baseURI = spoonConfig.baseURI();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam(API_KEY, spoonConfig.apiKey())
                .build();
    }
}