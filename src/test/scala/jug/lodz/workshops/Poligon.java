package jug.lodz.workshops;

import java.util.function.Function;

public class Poligon {

    public static void main(String[] args) {
        Function<Integer,Integer> inc = i -> i+1;

        Function<Function<Integer,Integer>,Function<Integer,Integer>> decorate = f -> i -> {
          System.out.println("decorated");
          return f.apply(i);
        };


        System.out.println(decorate.apply(inc).apply(2));
    }

    public static <A,B> Function <A,B> decorate(Function<A,B> f){
        return i -> {
            System.out.println("decorated");
            return f.apply(i);
        };
    }

}

class User{
        private String name;
        private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

