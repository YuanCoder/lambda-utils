package com.extendus.lamdautils.function;

@FunctionalInterface
public interface ThrowFunction<T extends String> {

    void lob(T t) throws RuntimeException;

}
