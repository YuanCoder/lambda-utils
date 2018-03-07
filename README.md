## Lamda Utils

**SafeBean class**

mostly used to avoid nullPointExceptions on nested objects

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