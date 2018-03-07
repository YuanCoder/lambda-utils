package com.extendus.lamdautils;

import com.extendus.lamdautils.function.ThrowFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.*;

public class Either<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(Either.class);

    private T value;

    private Predicate<T> predicate = Objects::nonNull;
    private Boolean predicateValue = Boolean.FALSE;
    private Boolean performAccepted = Boolean.FALSE;
    private Boolean elseAccepted = Boolean.FALSE;

    private Either(final T value) {
        this.value = value;
        this.predicateValue = predicate.test(value);
    }

    public static <T> Either<T> of(T value) {
        return new Either(value);
    }

    public Either<T> condition(Predicate<T> predicate) {
        this.predicateValue = this.predicate.and(predicate).test(value);
        return this;
    }

    /**
     * Accepts a {@code Consumer} with the specified present non-null and true value.
     *
     * @param consumer
     */
    public Either<T> perform(Consumer<T> consumer) {
        if (this.predicateValue && !elseAccepted) {
            performAccepted = Boolean.TRUE;
            consumer.accept(value);
        }
        return this;
    }

    /**
     * Accepts a {@code Function} with the specified present non-null and true value.
     *
     * @param function
     */
    public Either<T> perform(Function<T, T> function) {
        if (this.predicateValue && !elseAccepted) {
            performAccepted = Boolean.TRUE;
            value = function.apply(value);
        }
        return this;
    }

    /**
     * Accepts a {@code Supplier} with the specified present non-null and true value.
     *
     * @param supplier
     */
    public Either<T> perform(Supplier<T> supplier) {
        if (this.predicateValue && !elseAccepted) {
            performAccepted = Boolean.TRUE;
            value = supplier.get();
        }
        return this;
    }

    /**
     * Accepts a {@code Consumer} with the specified present null or false value.
     *
     * @param consumer
     */
    public Either<T> orElse(Consumer<T> consumer) {
        if (!this.predicateValue && !performAccepted) {
            elseAccepted = Boolean.TRUE;
            consumer.accept(value);
        }
        return this;
    }

    /**
     * Accepts a {@code Function} with the specified present null or false value.
     *
     * @param function
     */
    public Either<T> orElse(Function<T, T> function) {
        if (!this.predicateValue && !performAccepted) {
            elseAccepted = Boolean.TRUE;
            value = function.apply(value);
        }
        return this;
    }

    /**
     * Accepts a {@code Supplier} with the specified present null or false value.
     *
     * @param supplier
     */
    public Either<T> orElse(Supplier<T> supplier) {
        if (!this.predicateValue && !performAccepted) {
            elseAccepted = Boolean.TRUE;
            value = supplier.get();
        }
        return this;
    }

    /**
     * Throws a {@code Supplier<? extends RuntimeException>} with the specified present null or false value.
     *
     * @param exceptionSupplier
     */
    public Either<T> orElseThrow(Supplier<? extends RuntimeException> exceptionSupplier) throws RuntimeException {
        if (!this.predicateValue && !performAccepted) {
            elseAccepted = Boolean.TRUE;
            throw exceptionSupplier.get();
        }
        return this;
    }

    /**
     * Throws a {@code Supplier<? extends RuntimeException>} with the specified present null or false value.
     *
     * @param exceptionFunction
     */
    public Either<T> orElseThrow(Function<T, Supplier<RuntimeException>> exceptionFunction) throws RuntimeException {
        if (!this.predicateValue && !performAccepted) {
            elseAccepted = Boolean.TRUE;
            throw exceptionFunction.apply(value).get();
        }
        return this;
    }

    public T get() {
        return value;
    }

}
