package edu.school21.app;


import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;


public class Reflection {
    Class<?> currentClass;
    Scanner scanner;

    public Reflection(String className) {
        this.currentClass = getClassByName(className);
        this.scanner = new Scanner(System.in);
    }

    public Class<?> getClassByName(String className) {
        Class<?> currentClass = null;
        try {
            currentClass = Class.forName("edu.school21.classes." + className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class \"" + e.getMessage() + "\" not found");
        }
        return currentClass;
    }

    public Class<?> getClassInstance() {
        return currentClass;
    }

    public Object createObject(Class<?> currentClass) {
        Object currentObject = null;
        try {
            currentObject = currentClass.getConstructor().newInstance();
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName() + ":");
                field.setAccessible(true);
                String input = scanner.nextLine();
                String typeName = field.getType().getSimpleName();
                if (typeName.equals("String")) {
                    field.set(currentObject, input);
                } else if (typeName.equals("int")) {
                    field.setInt(currentObject, Integer.parseInt(input));
                } else if (typeName.equals("boolean")) {
                    field.setBoolean(currentObject, Boolean.parseBoolean(input));
                }
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return currentObject;
    }

    public void showObject(Object currentObject) {
        System.out.println(currentObject);
    }

    public Object fieldChang(Object currentObject) {
        String input = scanner.nextLine();
        try {
            Field field = currentObject.getClass().getDeclaredField(input);
            field.setAccessible(true);
            System.out.println("Enter " + field.getType() + " value: ");
            String typeName = field.getType().getSimpleName();
            input = scanner.nextLine();
            if (typeName.equals("String")) {
                field.set(currentObject, input);
            } else if (typeName.equals("int")) {
                field.setInt(currentObject, Integer.parseInt(input));
            } else if (typeName.equals("boolean")) {
                field.setBoolean(currentObject, Boolean.parseBoolean(input));
            }

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return currentObject;
    }

    public void runMethod(Object currentObject) {
        String input = scanner.nextLine();
        input = input.replace(" ", "");
        String methodNameIn = (input.split("\\(")[0]);
        String[] methodParameterIn = input.split("\\(")[1].split("[,()]");
        Method[] methods = currentObject.getClass().getDeclaredMethods();
        Type[] types = null;
        for (Method method : methods) {
            if (!method.getName().equals(methodNameIn)) {
                continue;
            }
            Class[] parameterTypes = method.getParameterTypes();
            types = method.getGenericParameterTypes();
            String[] typesStringArray = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                String fullClassName = types[i].getTypeName();
                typesStringArray[i] = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            }
            if (Arrays.equals(typesStringArray, methodParameterIn)) {
                try {
                    Method method1 = currentObject.getClass().getDeclaredMethod(methodNameIn, parameterTypes);
                    Object[] params = getParamsValue(parameterTypes);
                    Object returnValue = method1.invoke(currentObject, params);
                    if (returnValue != null) {
                        System.out.println("Method returned:");
                        System.out.println(returnValue);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Object[] getParamsValue(Class[] parameterTypes) {
        int length = parameterTypes.length;
        Object[] params = new Object[length];
        for (int i = 0; i < length; i++) {
            String parameterName = parameterTypes[i].getSimpleName();
            System.out.println("Enter " + parameterName + " value:");
            if (parameterTypes[i].getSimpleName().equals("String")) {
                params[i] = scanner.nextLine();
            } else if (parameterTypes[i].getSimpleName().equals("int")) {
                params[i] = scanner.nextInt();
            } else if (parameterTypes[i].getSimpleName().equals("boolean")) {
                params[i] = scanner.nextBoolean();
            }
        }

        return params;
    }

    public void showMethodList(Class<?> currentClass) {
        Method[] methods = currentClass.getDeclaredMethods();
        String returnType;
        System.out.println("methods:");
        for (Method method : methods) {
            returnType = method.getReturnType().getSimpleName();
            if (returnType.equals("void")) {
                returnType = "\r   ";
            }
            System.out.print("    " + returnType + " " + method.getName() + "(");
            Type[] types = method.getGenericParameterTypes();
            for (int i = 0; i < method.getParameterCount(); i++) {
                String fullClassName = types[i].getTypeName();
                if (i < method.getParameterCount() - 1) {
                    System.out.print(fullClassName.substring(fullClassName.lastIndexOf('.') + 1) + ", ");
                } else {
                    System.out.print(fullClassName.substring(fullClassName.lastIndexOf('.') + 1));
                }
            }
            System.out.println(")");
        }
    }

    public void showFieldList(Class<?> currentClass) {
        Field[] fields = currentClass.getDeclaredFields();
        System.out.println("fields:");
        for (Field field : fields) {
            System.out.print("    " + field.getType().getSimpleName() + " " + field.getName() + "\n");
        }
    }

}
