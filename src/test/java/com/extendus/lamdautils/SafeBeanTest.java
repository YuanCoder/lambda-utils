package com.extendus.lamdautils;


import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SafeBeanTest {

    @Test
    public void withNullParameter() {
        final Optional<String> value = retrieveFoo(null);
        assertFalse(value.isPresent());
    }

    public Optional<String> retrieveFoo(final Outer outer) {
        return SafeBean.get(() -> outer.getNested()
                .getInner()
                .getFoo());
    }

    @Test
    public void withNullInnerObject() {
        Outer obj = new Outer();

        Supplier<String> supplier = () -> obj.getNested().getInner().getFoo();
        Optional<String> value = SafeBean.get(supplier);

        assertFalse(value.isPresent());
    }

    @Test
    public void withAllInstantiatedObjects() {
        Outer obj = new Outer();
        Nested nested = new Nested();
        nested.setInner(new Inner());
        obj.setNested(nested);

        Supplier<String> supplier = () -> obj.getNested().getInner().getFoo();
        Optional<String> v = SafeBean.get(supplier);

        assertTrue(v.isPresent());
        assertThat("foo", is(v.get()));
    }

    class Outer {
        private Nested nested;

        Nested getNested() {
            return nested;
        }

        public void setNested(Nested nested) {
            this.nested = nested;
        }
    }

    class Nested {
        private Inner inner;

        Inner getInner() {
            return inner;
        }

        public void setInner(Inner inner) {
            this.inner = inner;
        }
    }

    class Inner {
        String getFoo() {
            return "foo";
        }
    }

}
