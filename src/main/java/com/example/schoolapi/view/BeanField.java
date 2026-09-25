package com.example.schoolapi.view;

import java.lang.reflect.Field;

/**
 * Helper that reads a field by name from a record or a class.
 * Records expose fields via their accessor methods (e.g. name()).
 * This helper prefers the record-component accessor if present, falls back to plain Field access.
 */
public final class BeanField {

    private BeanField() {}

    public static Object read(Object target, String key) {
        if (target == null || key == null || "tt".equals(key)) return null;
        try {
            // Records expose accessor methods named after the component (e.g. name()).
            String cap = Character.toUpperCase(key.charAt(0)) + key.substring(1);
            var m = target.getClass().getMethod(cap);
            return m.invoke(target);
        } catch (NoSuchMethodException e) {
            try {
                Field f = target.getClass().getDeclaredField(key);
                f.setAccessible(true);
                return f.get(target);
            } catch (Exception ex) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
