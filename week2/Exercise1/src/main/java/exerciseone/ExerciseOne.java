package exerciseone;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class MyObject {
    public int x = 10;
    public Date date = new Date(432234234L);

    public MyObject(int number) {
        this.x = number;
    }

    public MyObject(long ms) {
        this.date = new Date(ms);
    }

    public int getX() {
        x++;
        return x;
    }

    public Date getDate() {
        return date;
    }

    public int addToX(int y) {
        return x + y;
    }
}

public class ExerciseOne {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int N = 10;
        int M = 100000;
        int warmup = 10000000;
        long[] directAccessTimes = new long[N];
        long[] reflectionAccessTimes = new long[N];

        MyObject testObject = new MyObject(12);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < warmup; j++) {
                //testObject.getX();
                testObject.addToX(3);
            }
            long startTime = System.nanoTime();
            for (int j = 0; j < M; j++) {
                //testObject.getX();
                testObject.addToX(3);
            }
            long estimatedTime = System.nanoTime() - startTime;
            directAccessTimes[i] = estimatedTime;
        }

        for (int i = 0; i < N; i++) {
            Field field = testObject.getClass().getField("x");
            Method method = testObject.getClass().getMethod("addToX", int.class);
            for (int j = 0; j < warmup; j++) {

                //field.get(testObject);
                method.invoke(testObject, 3);
            }
            long startTime = System.nanoTime();
            for (int j = 0; j < M; j++) {

                //field.get(testObject);
                method.invoke(testObject, 3);
            }
            long estimatedTime = System.nanoTime() - startTime;
            reflectionAccessTimes[i] = estimatedTime;
        }

        System.out.println("Direct access (in seconds):");
        for (int i = 0; i < N; i++) {
            System.out.print(directAccessTimes[i] / 1000000000.0 + "\t");
        }
        System.out.println();

        System.out.println("Reflection access (in seconds):");
        for (int i = 0; i < N; i++) {
            System.out.print(reflectionAccessTimes[i] / 1000000000.0 + "\t");
        }
        System.out.println();
    }
}