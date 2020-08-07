package dev.donghyeon.annotation;


import java.lang.reflect.Field;

/**
 * Annotation processor
 * 5
 */
public class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz) {

        String strFruitName = " Fruit Name:";
        String strFruitColor = " Fruit color:";
        String strFruitProvicer = "Supplier Information:";

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                strFruitName = strFruitName + fruitName.value();
                System.out.println(strFruitName);
            } else if (field.isAnnotationPresent(FruitColor.class)) {
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                strFruitColor = strFruitColor + fruitColor.fruitColor().toString();
                System.out.println(strFruitColor);
            } else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                strFruitProvicer = " Supplier ID: " + fruitProvider.id() + " Vendor Name: " + fruitProvider.name() + " Vendor Address: " + fruitProvider.address();
                System.out.println(strFruitProvicer);
            }
        }
    }
}

