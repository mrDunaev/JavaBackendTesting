package extensions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CommonApiTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));
    }
}