package workshops.functional.fp1.answers;

import java.util.function.Function;

/**
 * Created by pawel on 07.02.16.
 */
public class FP1FunctionsCompositionAnswer {
    public static void main(String[] args){
        //exercise 1
        CustomFunction<Integer,Integer> f1=(Integer i) -> i+1;
        print(f1.apply(1));

        //exercise 2
        Function<String,Integer> parse=Integer::parseInt;
        Function<Integer,Integer> twice=i->i*2;
        print(andThen(parse,twice).apply("5"));

        //exercise 3
        print(compose(twice,parse).apply("7"));


    }

    static <A,B,C> Function<A,C> andThen(Function<A,B> f1, Function<B,C> f2){
        return a->{
            B b=f1.apply(a);
            return f2.apply(b);
        };
    }

    static <A,B,C> Function<A,C> andThenOneLine(Function<A,B> f1, Function<B,C> f2){
        return a->f2.apply(f1.apply(a));
    }

    static <A,B,C> Function<A,C> compose(Function<B,C> f1,Function<A,B> f2){
        return a->{
            B b=f2.apply(a);
            return f1.apply(b);
        };
    }

    static <A,B,C> Function<A,C> composeOneLine(Function<B,C> f1,Function<A,B> f2){
        return a->f1.apply(f2.apply(a));
    }

    static <A> void print(A line){
        System.out.println(line);
    }
}

@FunctionalInterface
interface CustomFunction<A,B>{
    B apply(A a);
}