package exercisetwo;


import exercisetwo.testobjects.*;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class FromJsonConverterTest {

    JsonConverter jsonConverter =  new JsonConverter();

    @Test
    public void convertToObjectPrivateIntFieldTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidJsonFormatException {
        ObjectPrivateIntField objectPrivateIntField = new ObjectPrivateIntField(3);
        ObjectPrivateIntField fromJsonString = (ObjectPrivateIntField) jsonConverter.convertJsonStringToObject("{\"x\": 3}", ObjectPrivateIntField.class);
        Assert.assertEquals(objectPrivateIntField.getX(), fromJsonString.getX());
    }

    @Test
    public void convertToObjectPublicIntFieldTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidJsonFormatException {
        ObjectPublicIntField objectPrivateIntField = new ObjectPublicIntField(3);
        ObjectPublicIntField fromJsonString = (ObjectPublicIntField) jsonConverter.convertJsonStringToObject("{\"x\": 3}", ObjectPublicIntField.class);
        Assert.assertEquals(objectPrivateIntField.x, fromJsonString.x);
    }

    @Test
    public void convertToObjectStringFieldTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidJsonFormatException {
        ObjectStringField objectStringField = new ObjectStringField("asd");
        ObjectStringField fromJsonString = (ObjectStringField) jsonConverter.convertJsonStringToObject("{\"s\": \"asd\"}", ObjectStringField.class);

        Assert.assertEquals(objectStringField.getS(), fromJsonString.getS());
    }

    @Test
    public void convertToObjectWithAnotherObjectTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidJsonFormatException {
        ObjectPrivateIntField objectPrivateIntField = new ObjectPrivateIntField(4);
        ObjectWithAnotherObject objectWithAnotherObject = new ObjectWithAnotherObject(23.15, objectPrivateIntField);
        ObjectWithAnotherObject fromJsonString = (ObjectWithAnotherObject) jsonConverter.convertJsonStringToObject("{\"y\": 23.15, \"obj\": {\"x\": 4}}", ObjectWithAnotherObject.class);

        Assert.assertEquals(objectWithAnotherObject.getY(), fromJsonString.getY());
        Assert.assertEquals(objectWithAnotherObject.getObj().getX(), fromJsonString.getObj().getX());
    }

    @Test
    public void convertToObjectSeveralWrapperFields() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidJsonFormatException {
        ObjectSeveralWrapperFields objectSeveralWrapperFields = new ObjectSeveralWrapperFields(new Character('e'), new Float(77.02f), new Byte((byte) 10), Boolean.FALSE);
        ObjectSeveralWrapperFields fromJsonString = (ObjectSeveralWrapperFields) jsonConverter.convertJsonStringToObject("{\"c\": \"e\", \"f\": 77.02, \"b\": 10, \"t\": false}", ObjectSeveralWrapperFields.class);

        Assert.assertEquals(fromJsonString.getC(), objectSeveralWrapperFields.getC());
        Assert.assertEquals(fromJsonString.getB(), objectSeveralWrapperFields.getB());
        Assert.assertEquals(fromJsonString.getT(), objectSeveralWrapperFields.getT());
        Assert.assertEquals(fromJsonString.getF(), objectSeveralWrapperFields.getF());
    }

    @Test(expected = InvalidFieldException.class)
    public void invalidFieldExceptionTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidJsonFormatException, IllegalAccessException {
        String jsonWithInvalidField = "{\"x\": 3, \"y\": 4}";
        jsonConverter.convertJsonStringToObject(jsonWithInvalidField, ObjectPrivateIntField.class);
    }

    @Test(expected = InvalidJsonFormatException.class)
    public void invalidJsonFormatExceptionTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidJsonFormatException, IllegalAccessException {
        String invalidJson = "{\"x\": 3";
        jsonConverter.convertJsonStringToObject(invalidJson, ObjectPrivateIntField.class);
    }

    @Test(expected = InvocationTargetException.class)
    public void wrongFieldValueTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidJsonFormatException, IllegalAccessException {
        String invalidJson = "{\"x\": 3dss}";
        jsonConverter.convertJsonStringToObject(invalidJson, ObjectPrivateIntField.class);
    }
}
