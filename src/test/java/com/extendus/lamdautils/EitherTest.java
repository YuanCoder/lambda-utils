package com.extendus.lamdautils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EitherTest {

    private static Logger LOGGER = LoggerFactory.getLogger(EitherTest.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void performConditionForImmutableString() {
        String value = "Rafael";
        Predicate<String> hasLastName = s -> s.endsWith("Mule");
        Function<String, String> addLastName = s -> s + " Mule";

        final String o = Either.eitherOf(value)
                .condition(hasLastName.negate())
                .perform(addLastName)
                .get();

        assertThat(value, is("Rafael"));
        assertThat(o, is("Rafael Mule"));
    }

    @Test
    public void performConditionForImmutableInteger() {
        Integer value = 16;
        Predicate<Integer> isMoreThanTen = s -> s > 10;
        Function<Integer, Integer> sumTen = s -> s + 10;

        final Integer o = Either.eitherOf(value)
                .condition(isMoreThanTen)
                .perform(sumTen)
                .get();

        assertThat(value, is(16));
        assertThat(o, is(26));
    }

    @Test
    public void performNoConditionNotNullValue() {
        Person person = new Person("Rafael");

        Function<Person, Person> addLastName = p -> {
            p.setName(p.getName() + " Mule");
            return p;
        };

        final Person o = Either.eitherOf(person)
                .perform(addLastName)
                .get();

        assertThat(o.getName(), is("Rafael Mule"));
        assertThat(person.getName(), is("Rafael Mule"));
    }

    @Test
    public void performNoConditionNullValue() {
        Person person = null;

        Function<Person, Person> addLastName = p -> {
            p.setName(p.getName() + " Mule");
            return p;
        };
        Supplier<Person> createNew = () -> {
            return new Person("Rafael");
        };

        final Person o = Either.eitherOf(person)
                .perform(addLastName)
                .orElse(createNew)
                .get();

        assertThat(o.getName(), is("Rafael"));
    }

    @Test
    public void performCondition() {
        Person person = new Person("Rafael");

        Predicate<Person> hasLastName = p -> p.getName().endsWith("Mule");
        Function<Person, Person> addLastName = p -> {
            p.setName(p.getName() + " Mule");
            return p;
        };

        final Person o = Either.eitherOf(person)
                .condition(hasLastName.negate())
                .perform(addLastName)
                .get();

        assertThat(o.getName(), is("Rafael Mule"));
        assertThat(person.getName(), is("Rafael Mule"));
    }

    @Test
    public void orElseCondition() {
        Person person = new Person("Rafael");

        Predicate<Person> hasLastName = p -> p.getName().endsWith("Mule");
        Function<Person, Person> addLastName = p -> {
            p.setName(p.getName() + " Mule");
            return p;
        };
        Function<Person, Person> addMiddleName = p -> {
            p.setName(p.getName() + " Broz");
            return p;
        };

        final Person o = Either.eitherOf(person)
                .condition(hasLastName)
                .perform(addLastName)
                .orElse(addMiddleName)
                .get();

        assertThat(o.getName(), is("Rafael Broz"));
        assertThat(person.getName(), is("Rafael Broz"));
    }

    @Test
    public void orElseThrowCondition() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Error");

        Person person = new Person("Rafael");
        Supplier<IllegalStateException> illegalStateException = () -> new IllegalStateException("Error");

        Predicate<Person> hasLastName = p -> p.getName().endsWith("Mule");

        final Person o = Either.eitherOf(person)
                .condition(hasLastName)
                .orElseThrow(illegalStateException)
                .get();
    }

    @Test
    public void orElseThrowFunctionCondition() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Error Rafael");

        Person person = new Person("Rafael");
        Function<Person, Supplier<? extends RuntimeException>> exceptionFunc = person1 ->
                () -> new IllegalStateException("Error " + person1.getName());

        Predicate<Person> hasLastName = p -> p.getName().endsWith("Mule");

        final Person o = Either.eitherOf(person)
                .condition(hasLastName)
                .orElseThrow(exceptionFunc)
                .get();
    }
}
