package spoonacular.extensions;

import common.CommonApiTestExtension;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class, CommonApiTestExtension.class})
public @interface SpoonApiTest {

}