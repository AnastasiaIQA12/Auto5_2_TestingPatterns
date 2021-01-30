package ru.netology;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generateByLogin() {
            Faker faker = new Faker(new Locale("usa"));
            String[] status = {"active", "blocked"};
            return new RegistrationDto(faker.name().firstName(), faker.internet().password(), faker.options().nextElement(status));
        }

    }
}
