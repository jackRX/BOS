package com.czxy.bosparent1;

import java.sql.SQLOutput;

public class Demo {
    public static void main(String[] args) {
        System.out.println(new Integer(2) == 2);
        System.out.println(new Integer(2) == new Integer(2));
        Integer integer = Integer.valueOf(2);
        Integer integer1 = Integer.valueOf(2);
        System.out.println(integer==integer1);
        System.out.println(Integer.valueOf(2).intValue() == 2);


    }


}
