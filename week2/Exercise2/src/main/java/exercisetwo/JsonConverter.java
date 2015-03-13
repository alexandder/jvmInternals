package exercisetwo;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            System.out.println(field.getType().getTypeName());

            String typeName = field.getType().getTypeName();

            jsonString.append(quotateString(field.getName()) + ": ");

            if (primitiveNumericTypes.contains(typeName) || wrapperNumericTypes.contains(typeName)) {
                jsonString.append(field.get(object));
            }
            else if (typeName.equals("char") || typeName.equals(String.class.getTypeName())) {
                jsonString.append(quotateString(field.get(object).toString()));
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

    public Object convertJsonStringToObject(String jsonString, Class cl) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String[] fieldsArray = jsonString.substring(1, jsonString.length()- 1).replaceAll("\"", "").split("\"?(:|,)(?![^\\{]*\\})\"?");
        Map<String, String> fieldsMap = new HashMap<>();
        for (int i = 0; i < fieldsArray.length; i+=2) {
            fieldsMap.put(fieldsArray[i].trim(), fieldsArray[i+1].trim());
        }
        Field[] fields = cl.getFields();

        try {
            Constructor<?> constructor = cl.getConstructor();
            Object objectFromJson = constructor.newInstance();
            for (Field f: fields) {
                f.setAccessible(true);
                if (isPrimitiveWrapperOrString(f.getType().getTypeName()))  {
                    f.set(objectFromJson, fieldsMap.get(f.getName()));
                }
                else {
                    String childObjectJson = fieldsMap.get(f.getName());
                    Object childObject = convertJsonStringToObject(childObjectJson, f.getType());
                    f.set(objectFromJson, childObject);
                }
                f.setAccessible(false);
            }
            return objectFromJson;
        }
        catch (NoSuchMethodException e) {
            System.err.println("Error: default constructor is required.");
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
        String componentTypeName = arr.getClass().getComponentType().getTypeName();

        for (int i = 0; i < Array.getLength(arr); i++) {
            arrayJsonString.append(quotateString(String.valueOf(i)) + ": ");
            Object element = Array.get(arr, i);
            if (primitiveNumericTypes.contains(componentTypeName) || wrapperNumericTypes.contains(componentTypeName)) {
                arrayJsonString.append(element);
            }
            else if (componentTypeName.equals("char") || componentTypeName.equals(String.class.getTypeName())) {
                arrayJsonString.append(quotateString(element.toString()));
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

    private boolean isPrimitiveWrapperOrString(String typeName) {
        return primitiveNumericTypes.contains(typeName) || wrapperNumericTypes.contains(typeName) || typeName.equals("char") || typeName.equals(String.class.getTypeName());
    }
}
