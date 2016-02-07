package workshops.functional.fp1.exercises;


import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1FunctionsComposition {

    public static void main(String[] args){
        //exercise 1 - write custom function
//        CustomFunction<Integer,Integer> f1=(Integer i) -> i+1;
//        print(f1.apply(1));

        //exercise 2 - write andThen (try one line)
//        Function<String,Integer> parse=Integer::parseInt;
//        Function<Integer,Integer> twice=i->i*2;
//        print(andThen(parse,twice).apply("5"));

        //exercise 3 - write compose (try one line)
//        print(compose(twice,parse).apply("7"));


    }

    static <A,B,C> Function<A,C> andThen(Function<A,B> f1, Function<B,C> f2){
        throw new UnsupportedOperationException();
    }



    static <A,B,C> Function<A,C> compose(Function<B,C> f1,Function<A,B> f2){
        throw new UnsupportedOperationException();
    }


    static <A> void print(A line){
        System.out.println(line);
    }


}
