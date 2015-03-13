package exercisetwo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.*;
import java.util.*;


public class JsonConverter {

    private static final List<String> primitiveNumericTypes = Arrays.asList(new String[]{"int", "short", "long", "byte", "double", "float", "boolean"});
    private static final List<String> wrapperNumericTypes = Arrays.asList(new String[]{Integer.class.getTypeName(), Short.class.getTypeName(), Long.class.getTypeName(), Byte.class.getTypeName(), Double.class.getTypeName(), Float.class.getTypeName(), Boolean.class.getTypeName()});

    public String convertToJson(Object object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");

        Field[] fields = object.getClass().getDeclaredFields();
        Iterator<Field> fieldIterator = Arrays.asList(fields).iterator();

        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);

            Class fieldType = field.getType();

            jsonString.append(quotateString(field.getName()) + ": ");


            if (fieldType.equals(char.class) || fieldType.equals(String.class) || fieldType.equals(Character.class)) {
                jsonString.append( field.get(object) != null ? quotateString(field.get(object).toString()) : quotateString(""));
            }
            else if (fieldType.isPrimitive() || ClassUtils.isPrimitiveWrapper(fieldType)) {
                jsonString.append(field.get(object));
            }
            else if (field.getType().isArray()) {
                jsonString.append(convertArrayToJsonString(field.get(object)));
            }
            else {
                String childObjectJson = convertToJson(field.get(object));
                jsonString.append(childObjectJson);
            }
            if (fieldIterator.hasNext()) {
                jsonString.append(", ");
            }
            field.setAccessible(false);
        }

        jsonString.append("}");
        return jsonString.toString();
    }

    public Object convertJsonStringToObject(String jsonString, Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InvalidJsonFormatException {

        if (jsonString.charAt(0) != '{' && jsonString.charAt(jsonString.length()-1) != '}') {
            throw new InvalidJsonFormatException("Invalid json format. Json should start with { and end with }.");
        }
        String[] fieldsArray = jsonString.substring(1, jsonString.length() - 1).replaceAll("\"", "").split("\"?(:|,)(?![^\\{]*\\})\"?");
        Map<String, String> fieldsMap = new HashMap<>();

        if (fieldsArray.length > 1) {
            for (int i = 0; i < fieldsArray.length; i += 2) {
                fieldsMap.put(fieldsArray[i].trim(), fieldsArray[i + 1].trim());
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        Set<String> fieldNamesFromString = fieldsMap.keySet();

        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object objectFromJson = constructor.newInstance();

            for (Field f: fields) {
                f.setAccessible(true);
                Class fieldType = f.getType();

                if (fieldType.isPrimitive())  {

                    Class wrapperFieldType = ClassUtils.primitiveToWrapper(fieldType);
                    try {
                        Method valueofMethod = wrapperFieldType.getMethod("valueOf", String.class);
                        f.set(objectFromJson, valueofMethod.invoke(objectFromJson, fieldsMap.get(f.getName())));
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Invalid data format.");
                        e.printStackTrace();
                    }

                }
                else if (fieldType.equals(String.class) || ClassUtils.isPrimitiveWrapper(fieldType)) {
                    BeanUtils.setProperty(objectFromJson, f.getName(), fieldsMap.get(f.getName()));
                }
                else {
                    String childObjectJson = fieldsMap.get(f.getName());
                    Object childObject = convertJsonStringToObject(childObjectJson, f.getType());
                    f.set(objectFromJson, childObject);
                }

                fieldNamesFromString.remove(f.getName());
                f.setAccessible(false);
            }

            if (!fieldNamesFromString.isEmpty()) {
                throw new InvalidJsonFormatException("Invalid field " + fieldNamesFromString.toArray()[0] + " in json string.");
            }

            return objectFromJson;
        }
        catch (NoSuchMethodException e) {
            System.err.println("Error: default constructor is required.");
            e.printStackTrace();
        }
        catch (InvalidJsonFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String quotateString(String s) {
        return "\"" + s + "\"";
    }

    private String convertArrayToJsonString(Object arr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder arrayJsonString = new StringBuilder();
        arrayJsonString.append("[");
        Class componentType = arr.getClass().getComponentType();

        for (int i = 0; i < Array.getLength(arr); i++) {
            arrayJsonString.append(quotateString(String.valueOf(i)) + ": ");
            Object element = Array.get(arr, i);

            if (componentType.equals(char.class) || componentType.equals(String.class)) {
                arrayJsonString.append(quotateString(element.toString()));
            }
            else if (ClassUtils.isPrimitiveOrWrapper(componentType)) {
                arrayJsonString.append(element);
            }
            else {
                arrayJsonString.append(convertToJson(element));
            }

            if (i < Array.getLength(arr) - 1) {
                arrayJsonString.append(", ");
            }
        }

        arrayJsonString.append("]");
        return arrayJsonString.toString();
    }
}
