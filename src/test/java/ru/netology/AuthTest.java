package ru.netology;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import static io.restassured.RestAssured.given;

// спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
class AuthTest {
    private static RegistrationDto dataOrderCard=DataGenerator.Registration.generateByLogin();
    String login=dataOrderCard.getLogin();;
    String password=dataOrderCard.getPassword();

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    public static void setUpAll() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(dataOrderCard) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    void shouldLoginFunctionStatusUser() {
        open("http://localhost:9999/");
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $("[data-test-id=action-login]").click();
        String status=dataOrderCard.getStatus();
        if (status=="blocked"){
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
        }
        if (status=="active"){
            $(withText("Личный кабинет")).shouldBe(Condition.visible);
        }
    }

    @Test
    void shouldLoginFunctionInvalidLogin() {
        open("http://localhost:9999/");
        $("[name=login]").setValue("anastasia");
        $("[name=password]").setValue(password);
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginFunctionInvalidPassword() {
        open("http://localhost:9999/");
        $("[name=login]").setValue(login);
        $("[name=password]").setValue("12345");
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }


}
