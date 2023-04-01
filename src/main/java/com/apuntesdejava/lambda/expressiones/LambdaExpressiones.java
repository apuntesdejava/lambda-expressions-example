package com.apuntesdejava.lambda.expressiones;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaExpressiones {

    public static void main(String[] args) {
        List<Person> persons = List.of(
                new Person("Ann", LocalDate.of(2001, 3, 17), Sex.FEMALE, "ann@mail.com"),
                new Person("Bob", LocalDate.of(2004, 4, 4), Sex.MALE, "bob@mail.com"),
                new Person("Carl", LocalDate.of(1976, 3, 27), Sex.MALE, "carl@mail.com"),
                new Person("Dana", LocalDate.of(1977, 10, 10), Sex.FEMALE, "dana@mail.com"),
                new Person("Ernie", LocalDate.of(2006, 2, 2), Sex.MALE, "ernie@mail.com")
        ); //

        printPersons(persons, new SelectPersonCheckPerson());

        printPersons(persons, new CheckPerson() {
            @Override
            public boolean evaluate(Person person) {
                return person.gender() == Sex.MALE
                        && person.getAge() >= 18
                        && person.getAge() <= 15;
            }
        });
        printPersons(persons, (Person person) -> person.gender() == Sex.MALE
                && person.getAge() >= 18
                && person.getAge() <= 15);

        printPersonsWithPredicate(persons, person -> person.gender() == Sex.MALE
                && person.getAge() >= 18
                && person.getAge() <= 15);

        processPersons(
                persons,
                p -> p.gender() == Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 15,
                p -> p.print());

        processPersonsWithFunction(
                persons,
                p -> p.gender() == Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 15,
                p -> p.email(),
                email -> System.out.println(email));

        processElements(
                persons,
                p -> p.gender() == Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 15,
                p -> p.email(),
                email -> System.out.println(email));

        //Paso 9:
        persons
                .stream() //obtiene la fuente de los objetos
                .filter( //filtra los objetos. Debe devolver un boolean
                        p -> p.gender() == Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 15
                )
                .map( //obtiene un nuevo valor a partir del parámetro
                        p -> p.email()
                ).forEach( //realiza un acción por cada elemento recibido
                        email -> System.out.println(email)
                );
    }
    // Paso 1
    static void printPersonsOlderThan(List<Person> persons, int age) {
        for (var person : persons) {
            if (person.getAge() >= age) {
                person.print();
            }
        }
    }

    // Paso 2
    static void printPersonsWithinAgeRange(List<Person> persons, int low, int high) {
        for (var person : persons) {
            if (person.getAge() >= low && person.getAge() <= high) {
                person.print();
            }
        }
    }

    // Paso 3
    static void printPersons(List<Person> persons, CheckPerson checkPerson) {
        for (var person : persons) {
            if (checkPerson.evaluate(person)) {
                person.print();
            }
        }
    }
    // Paso 6
    static void printPersonsWithPredicate(List<Person> persons, Predicate<Person> checkperson) {
        for (var person : persons) {
            if (checkperson.test(person)) {
                person.print();
            }
        }
    }

    //Paso 7
    static void processPersons(List<Person> persons,
            Predicate<Person> checkPerson,
            Consumer<Person> consumer) {

        for (Person person : persons) {
            if (checkPerson.test(person)) {
                consumer.accept(person);
            }
        }
    }

    static void processPersonsWithFunction(
            List<Person> persons,
            Predicate<Person> checkPerson,
            Function<Person, String> getField,
            Consumer<String> consumer
    ) {
        for (Person person : persons) {
            if (checkPerson.test(person)) {
                String data = getField.apply(person);
                consumer.accept(data);
            }
        }
    }

    //Paso 8
    static <X, Y> void processElements(
            Iterable<X> iterable,
            Predicate<X> tester,
            Function<X, Y> mapper,
            Consumer<Y> consumer
    ) {
        for (X element : iterable) {
            if (tester.test(element)) {
                Y data = mapper.apply(element);
                consumer.accept(data);
            }
        }
    }
}

@FunctionalInterface
interface CheckPerson {

    boolean evaluate(Person p);
}

class SelectPersonCheckPerson implements CheckPerson {

    @Override
    public boolean evaluate(Person person) {
        return person.gender() == Sex.MALE
                && person.getAge() >= 18
                && person.getAge() <= 15;
    }

}
