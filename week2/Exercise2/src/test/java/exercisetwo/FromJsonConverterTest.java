package exercisetwo;


import exercisetwo.testobjects.EmptyObject;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class FromJsonConverterTest {

    JsonConverter jsonConverter =  new JsonConverter();

    @Test
    public void convertToEmptyObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EmptyObject emptyObject = new EmptyObject();
        Assert.assertEquals(emptyObject, jsonConverter.convertJsonStringToObject("{}", EmptyObject.class));
    }
}
