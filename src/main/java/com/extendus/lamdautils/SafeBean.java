package com.extendus.lamdautils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class SafeBean {

    private static Logger LOGGER = LoggerFactory.getLogger(SafeBean.class);

    /**
     * <strong>NullPointer SafeBean get.</strong>
     * <p>
     * Receiver a supplier and encapsulate the exception and returns an empty Optional.
     * <p>usage example:<p>
     * <pre>
     * Optional<String> v = SafeBean.get(() -> obj.getNested().getInner().getFoo());
     * </pre>
     *
     * @param resolver Supplier type
     * @param <T>      Excepted type for the optional
     * @return Optional
     */
    public static <T> Optional<T> get(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (NullPointerException e) {
            LOGGER.debug("NullPointerException for the given Supplier, will return an empty Optional");
            return Optional.empty();
        }
    }
}
