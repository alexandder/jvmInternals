package exercisetwo;

import exercisetwo.testobjects.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;


public class JsonConverterTest {


    JsonConverter jsonConverter = new JsonConverter();

    @Test
    public void convertToJsonEmptyObjectTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        EmptyObject emptyObject = new EmptyObject();
        Assert.assertEquals("{}", jsonConverter.convertToJson(emptyObject));
    }

    @Test
    public void convertToJsonObjectPublicIntFieldTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectPublicIntField objectPublicIntegerField = new ObjectPublicIntField(3);
        Assert.assertEquals("{\"x\": 3}", jsonConverter.convertToJson(objectPublicIntegerField));
    }

    @Test
    public void convertToJsonObjectPrivateIntFieldTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectPrivateIntField objectPrivateIntegerField = new ObjectPrivateIntField();
        objectPrivateIntegerField.setX(12);
        Assert.assertEquals("{\"x\": 12}", jsonConverter.convertToJson(objectPrivateIntegerField));
    }

    @Test
    public void convertToJsonObjectSeveralPrimitiveFieldsTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectSeveralPrimitiveFields objectSeveralPrimitiveFields = new ObjectSeveralPrimitiveFields();
        objectSeveralPrimitiveFields.setMbyte((byte) 0b1101);
        objectSeveralPrimitiveFields.setMchar('a');
        objectSeveralPrimitiveFields.setMdouble(213.213);
        objectSeveralPrimitiveFields.setMfloat(21.32f);
        objectSeveralPrimitiveFields.setMlong(213L);
        objectSeveralPrimitiveFields.setMshort((short) 12);
        Assert.assertEquals("{\"mshort\": 12, \"mlong\": 213, \"mfloat\": 21.32, \"mdouble\": 213.213, \"mbyte\": 13, \"mchar\": \"a\"}", jsonConverter.convertToJson(objectSeveralPrimitiveFields));
    }

    @Test
    public void convertToJsonObjectStringFieldTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectStringField objectStringField = new ObjectStringField("aaa");
        Assert.assertEquals("{\"s\": \"aaa\"}", jsonConverter.convertToJson(objectStringField));
    }

    @Test
    public void convertToJsonObjectPrivateIntegerTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectPrivateIntegerField objectPrivateIntegerField = new ObjectPrivateIntegerField(new Integer(3));
        Assert.assertEquals("{\"x\": 3}", jsonConverter.convertToJson(objectPrivateIntegerField));
    }

    @Test
    public void convertToJsonObjectWithAnotherObjectTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectPrivateIntField obj = new ObjectPrivateIntField(4);
        ObjectWithAnotherObject objectWithAnotherObject = new ObjectWithAnotherObject(new Double(131.21), obj);
        Assert.assertEquals("{\"y\": 131.21, \"obj\": {\"x\": 4}}", jsonConverter.convertToJson(objectWithAnotherObject));
    }

    @Test
    public void convertToJsonObjectIntArrayTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ObjectIntArray objectIntArray = new ObjectIntArray(new int[]{1,2,3});
        Assert.assertEquals("{\"ints\": [\"0\": 1, \"1\": 2, \"2\": 3]}", jsonConverter.convertToJson(objectIntArray));
    }

}
