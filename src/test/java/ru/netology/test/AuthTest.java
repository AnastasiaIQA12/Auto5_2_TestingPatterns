package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import static io.restassured.RestAssured.given;

// спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
class AuthTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLoginActiveUser() {
        RegistrationDto dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        RegistrationDto dataOrderCard= DataGenerator.Registration.generateBlockedUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginFunctionInvalidLogin() {
        RegistrationDto dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue("anastasia");
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginFunctionInvalidPassword() {
        RegistrationDto dataOrderCard= DataGenerator.Registration.generateActiveUser();
        DataGenerator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue("12345");
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }


}
