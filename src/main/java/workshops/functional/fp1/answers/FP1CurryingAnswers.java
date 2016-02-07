package workshops.functional.fp1.answers;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1CurryingAnswers {

    public static void main(String[] args){
        //exercise 1
        print(add1.apply(1));

        //exercise2
        print(add2.apply(1));
        print(add2.apply(1).apply(1));

        //exercise3
        print(add3.apply(1).apply(1).apply(1));

        //exercise4
        print(add4.apply(1).apply(1).apply(1).apply(1));


        //exercise 5
        print(add2params.apply(3,4));
        print(biFunction.apply(3,4));

    }

    public static Function<Integer,Integer> add1= x-> x+0;

    public static Function<Integer,Function<Integer,Integer>> add2= a->b->a+b;

    public static Function<Integer,Function<Integer,Function<Integer,Integer>>> add3= a->b->c->a+b+c;

    public static Function<Integer,Function<Integer,Function<Integer,Function<Integer,Integer>>>> add4= a->b->c->d->a+b+c+d;


    public static Function2<Integer,Integer,Integer> add2params=(a, b)->a+b;

    public static BiFunction<Integer,Integer,Integer> biFunction=(a, b)->a+b;

    static <A> void print(A line){
        System.out.println(line);
    }
}

@FunctionalInterface
interface Function2<A,B,C> {
    C apply(A a,B b);
}
