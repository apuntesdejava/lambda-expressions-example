package com.apuntesdejava.lambda.expressiones;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Person(String name, LocalDate birthday, Sex gender, String email) {

    public long getAge() {
        return ChronoUnit.YEARS.between(LocalDate.now(), birthday);
    }

    public void print() {
        System.out.printf("name:%s birthday:%s gender:%s email:%s\n",
                name, birthday, gender, email);
    }
}

enum Sex {
    MALE, FEMALE
}
