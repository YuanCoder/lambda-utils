package com.extendus.lamdautils.function;

@FunctionalInterface
public interface ThrowBiFunction<T extends Class, R extends String> {

    void lob(T t, R r) throws RuntimeException;

}
