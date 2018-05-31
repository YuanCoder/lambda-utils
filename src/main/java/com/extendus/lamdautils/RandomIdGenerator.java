package com.extendus.lamdautils;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomIdGenerator {

    public static final String base62String = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final Predicate<Integer> notNullInteger = Objects::nonNull;

    public static final Predicate<Integer> isMaxSizeAcceptable = n -> n > base62String.length();

    public static final Supplier<Random> newRandom = Random::new;

    public static final Function<Integer, Integer> maxSize = n ->
            (notNullInteger.negate().or(isMaxSizeAcceptable).test(n) ? base62String.length() : n);

    public static final Function<Integer, Character> randomCharacter = l -> base62String.charAt(newRandom.get()
            .nextInt(maxSize.apply(l)));

    public static final BiFunction<Integer, Integer, String> generateId = (n, s) -> IntStream.range(0, n)
            .mapToObj(value -> randomCharacter.apply(s))
            .map(Object::toString)
            .collect(Collectors.joining());

    public static final Supplier<String> defaultId = () -> generateId.apply(8, 36);

    public static final Function<Integer, String> base36 = n -> generateId.apply(n, 36);

    public static final Function<Integer, String> base62 = n -> generateId.apply(n, 62);

    public static final Supplier<String> randomUuid = () -> UUID.randomUUID().toString();

}
