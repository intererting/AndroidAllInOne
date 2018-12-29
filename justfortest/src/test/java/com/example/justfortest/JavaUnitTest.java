package com.example.justfortest;

import org.junit.Test;

public class JavaUnitTest {

    @Test
    public void test() {
//        Son.say();
//        Father.say();
        Father.say("hahaha");
    }
}

class Father<M> {

    static <T> void say(T t) {
        if (t instanceof String) {
            System.out.println(t);
        }
    }

    static void say() {
        System.out.println("father static say");
    }
}

class Son extends Father {


}
