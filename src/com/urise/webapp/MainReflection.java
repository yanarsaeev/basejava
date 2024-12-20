package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("ali");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new uuid");
        // TODO : invoke r.toString via reflection
        Class<?> clazz = Class.forName("com.urise.webapp.model.Resume");
        Method method = clazz.getMethod("toString");
        System.out.println("toString(): " + method.invoke(r));
        System.out.println(r);
    }
}
