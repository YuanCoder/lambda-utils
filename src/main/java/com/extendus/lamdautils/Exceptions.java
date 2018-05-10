package com.extendus.lamdautils;

import com.extendus.lamdautils.function.ThrowBiFunction;
import com.extendus.lamdautils.function.ThrowFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Exceptions {

    private static Logger LOGGER = LoggerFactory.getLogger(Exceptions.class);

    public static Function<String, RuntimeException> runtimeException = RuntimeException::new;

    public static Function<String, IllegalArgumentException> illegalArgumentException = IllegalArgumentException::new;

    public static Function<String, IllegalStateException> illegalStateException = IllegalStateException::new;

    public static BiFunction<Class, String, RuntimeException> enumConstantNotPresentException = EnumConstantNotPresentException::new;

    /**
     * RuntimeException and its subclasses are unchecked exceptions
     * Unchecked exceptions do not need to be declared in a method or constructor's throws
     */
    public static ThrowFunction<String> throwRuntimeException = s -> {
        throw runtimeException.apply(s);
    };

    /**
     * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
     */
    public static ThrowFunction<String> throwIllegalArgumentException = s -> {
        throw illegalArgumentException.apply(s);
    };

    /**
     * Signals that a method has been invoked at an illegal or inappropriate time. In other words,
     * the Java environment or Java application is not in an appropriate state for the requested operation.
     */
    public static ThrowFunction<String> throwIllegalStateException = s -> {
        throw illegalStateException.apply(s);
    };

    /**
     * Thrown when an application tries to access an enum constant by name
     * and the enum type contains no constant with the specified name
     */
    public static ThrowBiFunction<Class, String> throwEnumConstantNotPresentException = (c, s) -> {
        throw enumConstantNotPresentException.apply(c, s);
    };
}
