package workshops.functional.fp1.exercises;

import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1FunctionalTypeAndAliases {



    public static void main(String[] args){
        //exercise 1
//        print(add1.apply(1));
        //exercise 2
//        print(add2.apply(x->x+1));
        //exercise 3
//        print(add3.apply(x->x+1).apply(2));
        //exercise 4
//        print(add3Aliases.apply(x->x+1).apply(2));
    }


    public static Function<Integer,Integer> add1 = null;

    public static Function<Function<Integer,Integer>,Integer> add2= null;

    public static Function<Function<Integer,Integer>,Function<Integer,Integer>> add3= null;

    public static Function<Int_to_Int,Int_to_Int> add3Aliases= null;

    static <A> void print(A line){
        System.out.println(line);
    }
}

@FunctionalInterface
interface Int_to_Int extends Function<Integer,Integer>{}
