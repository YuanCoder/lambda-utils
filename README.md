## Lamda Utils

**SafeBean class**

Mostly used to avoid nullPointExceptions on nested objects

Example:

```
String value = obj.getNested().getInner().getFoo();

```
This can be a potential nullPointExceptions candidate.

With SafeBean:

```
Supplier<String> supplier = () -> obj.getNested().getInner().getFoo();
Optional<String> value = SafeBean.get(supplier);
```

With SafeBean reduces the chances of nullPointExceptions on nested objects by 100%

**Either class**

Used to combine functions in order to create an if else statements 

Example:

```
String value = "Dog";
Predicate<String> isAnimal = s -> s.equals("Dog");
Function<String, String> applyAnimal = s -> s + " is an animal";
Function<String, String> applyNotAnimal = s -> s + " is not an animal";

if (isAnimal.test(value)) {
    value = applyAnimal.apply(value);
} else {
    value = applyNotAnimal.apply(value);
}
``` 

Either Util class can make this statement nicer :

```
value = Either.of(value)
                .condition(isAnimal)
                .perform(applyAnimal)
                .orElse(applyNotAnimal)
                .get();
```

Either Utils class helps you to remove the java keywords ( IF | ELSE) of 
your business logic classes

with possibility to throw Exception:

```
Supplier<IllegalStateException> illegalStateException = () -> new IllegalStateException("is not an annimal");
value = Either.of(value)
        .condition(isAnimal)
        .perform(applyAnimal)
        .orElseThrow(illegalStateException)
        .get();
```