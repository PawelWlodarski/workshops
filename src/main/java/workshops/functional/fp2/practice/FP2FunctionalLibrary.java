package workshops.functional.fp2.practice;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by pawel on 13.03.16.
 */
public class FP2FunctionalLibrary {
    static <A,B> Function<Collection<A>,Collection<B>> liftToCollection(Function<A,B> f){
        return input -> input.stream().map(f).collect(Collectors.toList());
    }

    static <A,B> Function<Optional<A>,Optional<B>> liftToOptional(Function<A,B> f){
        return input-> input.map(f);
    }

    static <A> Function<Collection<A>,Collection<A>> filterFunction(Predicate<A> p){
        return input -> {
            List<A> result=new LinkedList<>();
            for (A a : input) {
                if(p.test(a)) result.add(a);
            }
            return result;
        };
    }

    static <A> Function<Collection<A>,Map<A,Integer>> countFunction(){
        return input->{
            Map<A,Integer> counts=new HashMap<>();
            for (A field : input) {
                counts.compute(field, (k,v)-> v==null? 1 : v+1);
            }
            return counts;
        };
    }

    static <A> Function<Collection<A>,Collection<A>> sortFunction(Comparator<A> comparator){
        return input->input.stream().sorted(comparator).collect(Collectors.toList());
    }
}
