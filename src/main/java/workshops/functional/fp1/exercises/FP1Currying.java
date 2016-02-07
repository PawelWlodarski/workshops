package workshops.functional.fp1.exercises;

import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1Currying {


    public static void main(String[] args){
        //exercise 1
//        print(add1.apply(1));

        //exercise2
//        print(add2.apply(1));
//        print(add2.apply(1).apply(1));

        //exercise3
//        print(add3.apply(1).apply(1).apply(1));

        //exercise4
//        print(add4.apply(1).apply(1).apply(1).apply(1));


    }

    public static Function<Integer,Integer> add1= null;

    public static Function<Integer,Function<Integer,Integer>> add2= null;

    public static Function<Integer,Function<Integer,Function<Integer,Integer>>> add3= null;

    public static Function<Integer,Function<Integer,Function<Integer,Function<Integer,Integer>>>> add4= null;


    static <A> void print(A line){
        System.out.println(line);
    }

}
