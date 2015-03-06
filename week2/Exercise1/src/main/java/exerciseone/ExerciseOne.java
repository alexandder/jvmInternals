package exerciseone;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MyObject {
    public int x = 10;

    public MyObject(int number) {
        this.x = number;
    }

    public int addToX(int y) {
        return x + y;
    }
}

public class ExerciseOne {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int N = 10;
        int M = 1000000;
        int warmup = 10000000;
        long[] directAccessTimes = new long[N];
        long[] reflectionAccessTimes = new long[N];

        MyObject testObject = new MyObject(12);
        int y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < warmup; j++) {
                y = testObject.x;
                //testObject.addToX(3);
            }
            long startTime = System.nanoTime();

            for (int j = 0; j < M; j++) {
                y = testObject.x;
                //testObject.addToX(3);
            }
            long estimatedTime = System.nanoTime() - startTime;
            directAccessTimes[i] = estimatedTime;
        }

        for (int i = 0; i < N; i++) {
            Field field = testObject.getClass().getField("x");
            Method method = testObject.getClass().getMethod("addToX", int.class);
            for (int j = 0; j < warmup; j++) {

                field.get(testObject);
                //method.invoke(testObject, 3);
            }
            long startTime = System.nanoTime();
            for (int j = 0; j < M; j++) {

                field.get(testObject);
                //method.invoke(testObject, 3);
            }
            long estimatedTime = System.nanoTime() - startTime;
            reflectionAccessTimes[i] = estimatedTime;
        }


        double directAverage = 0;
        double reflectionAverage = 0;

        System.out.println("Direct access (in seconds):");
        for (int i = 0; i < N; i++) {
            directAverage += (directAccessTimes[i] / 1000000000.0);
            System.out.print(directAccessTimes[i] / 1000000000.0 + "\t");
        }
        System.out.println();

        System.out.println("Reflection access (in seconds):");
        for (int i = 0; i < N; i++) {
            reflectionAverage += (reflectionAccessTimes[i] / 1000000000.0);
            System.out.print(reflectionAccessTimes[i] / 1000000000.0 + "\t");
        }
        System.out.println();

        directAverage = directAverage / N;

        reflectionAverage = reflectionAverage / N;

        System.out.println("Average direct access (in seconds): " + directAverage);
        System.out.println("Average reflection access (in seconds): " + reflectionAverage);
        System.out.println("Ratio (average direct access to average reflection access): " + reflectionAverage/directAverage);
    }
}
